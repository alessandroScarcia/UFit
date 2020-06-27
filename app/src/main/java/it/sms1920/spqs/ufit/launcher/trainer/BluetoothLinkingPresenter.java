package it.sms1920.spqs.ufit.launcher.trainer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.model.bluetooth.BluetoothHelper;
import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;

class BluetoothLinkingPresenter implements BluetoothLinkingContract.Presenter {

    private BluetoothLinkingContract.View view;

    private User him;
    private User me;

    private BluetoothHelper chatController;
    private BluetoothDevice connectingDevice;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> discoveredDevicesAdapter;

    private boolean connected = false;

    BluetoothLinkingPresenter(final BluetoothLinkingContract.View view, ArrayAdapter<String> discoveredDevicesAdapter) {
        this.view = view;
        this.discoveredDevicesAdapter = discoveredDevicesAdapter;
        //check device supports bluetooth or not
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            view.showNoBluetoothAdapterAvailable();
            view.disableButton();
        }

        FirebaseDbSingleton.getInstance().getReference().child("User").child(FirebaseAuthSingleton.getFirebaseAuth().getUid()).keepSynced(true);

        final String myUid = FirebaseAuthSingleton.getFirebaseAuth().getUid();
        FirebaseDbSingleton.getInstance().getReference()
                .child("User")
                .orderByKey()
                .equalTo(myUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            me = item.getValue(User.class);
                            view.setStatus(me.toString());
                            if (!me.getLinkedUserId().isEmpty()) {
                                view.alreadyConnected();
                                connected = true;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    public void onConnectButtonClicked() {
        if (me.getLinkedUserId().isEmpty()) {
            view.showBluetoothDialog(bluetoothAdapter, discoveredDevicesAdapter, discoveryFinishReceiver);
        } else {
            view.showToast(view.getDisconnectionString());

            FirebaseDbSingleton.getInstance().getReference().child("User").child(me.getLinkedUserId()).child("linkedUserId").setValue("");

            me.setLinkedUserId("");
            FirebaseDbSingleton.getInstance().getReference().child("User").child(FirebaseAuthSingleton.getFirebaseAuth().getUid()).setValue(me);
            view.notifyConnectionEstablished();
        }
    }

    @Override
    public void onConnectToDeviceRequested(String address) {
        bluetoothAdapter.cancelDiscovery();
        try {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
            chatController.connect(device);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setChatController() {
        this.chatController = new BluetoothHelper(handler);
    }

    @Override
    public void onResume() {
        if (chatController != null) {
            if (chatController.getState() == BluetoothHelper.STATE_NONE) {
                chatController.start();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (chatController != null)
            chatController.stop();
    }

    @Override
    public void onStart() {

        if (bluetoothAdapter.isEnabled()) {
            chatController = new BluetoothHelper(handler);
        }
    }


    private final BroadcastReceiver discoveryFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    discoveredDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (discoveredDevicesAdapter.getCount() == 0) {
                    discoveredDevicesAdapter.add(view.getNoFoundStringError());
                }
            }
        }
    };


    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case BluetoothHelper.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothHelper.STATE_CONNECTED:
                            view.closeDialog();
                            if (!connected) {
                                view.setStatus("Connessione stabilita");
                                sendMessage(FirebaseAuthSingleton.getFirebaseAuth().getUid());
                                connected = true;
                            }
                            break;
                        case BluetoothHelper.STATE_CONNECTING:
                            view.showToast(view.getConnectingString());

                            break;
                        case BluetoothHelper.STATE_LISTEN:
                        case BluetoothHelper.STATE_NONE:
                            break;
                    }
                    break;
                case BluetoothHelper.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);

                    break;
                case BluetoothHelper.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    if (readMessage.isEmpty()) {
                        view.notifyConnectionEstablished();
                    } else {
                        view.showToast("Invio del ID: " + FirebaseAuthSingleton.getFirebaseAuth().getUid());
                        view.setStatus(view.getConnectedWithString() + " " + readMessage);

                        tryConnectWith(readMessage);

                    }

                    break;
                case BluetoothHelper.MESSAGE_DEVICE_OBJECT:
                    connectingDevice = msg.getData().getParcelable(BluetoothHelper.DEVICE_OBJECT);
                    break;
                case BluetoothHelper.MESSAGE_TOAST:
                    break;
            }

            return false;
        }
    });


    private void tryConnectWith(final String readMessage) {

        FirebaseDbSingleton.getInstance().getReference().child("User").child(readMessage).keepSynced(true);

        Query query = FirebaseDbSingleton.getInstance().getReference()
                .child("User")
                .orderByKey()
                .equalTo(readMessage);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()) {
                    him = user.getValue(User.class);
                    if (him.getRole() == me.getRole()) {
                        view.setStatus(view.getSameRoleString());
                    } else {
                        connectUser(readMessage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void connectUser(final String hisUid) {
        final String myUid = FirebaseAuthSingleton.getFirebaseAuth().getUid();
        final DatabaseReference usersReference = FirebaseDbSingleton.getInstance().getReference().child("User");

        me.setLinkedUserId(hisUid);
        him.setLinkedUserId(myUid);

        usersReference.child(myUid).setValue(me);
        usersReference.child(hisUid).setValue(him);

        sendMessage("");

        view.notifyConnectionEstablished();

    }

    private void sendMessage(String message) {
        if (chatController.getState() != BluetoothHelper.STATE_CONNECTED) {
            view.setStatus(view.getLostConnectionString());
            return;
        }

        if (message.length() > 0) {
            byte[] send = message.getBytes();
            chatController.write(send);
        }
    }

}
