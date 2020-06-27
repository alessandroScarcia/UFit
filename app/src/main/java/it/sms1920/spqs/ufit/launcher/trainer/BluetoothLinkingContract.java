package it.sms1920.spqs.ufit.launcher.trainer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.widget.ArrayAdapter;

public interface BluetoothLinkingContract {
    interface View {

        void showBluetoothDialog(BluetoothAdapter bluetoothAdapter,
                                 ArrayAdapter<String> discoveredDevicesAdapter,
                                 BroadcastReceiver discoveryFinishReceiver);

        void showNoBluetoothAdapterAvailable();

        String getNoFoundStringError();

        void disableButton();

        void showToast(String message);

        String getConnectedWithString();

        String getLostConnectionString();

        void notifyConnectionEstablished();

        void closeDialog();

        void setStatus(String text);

        String getConnectingString();

        String getSameRoleString();

        void alreadyConnected();

        String getDisconnectionString();
    }

    interface Presenter {

        void onConnectButtonClicked();

        void onConnectToDeviceRequested(String address);

        void setChatController();

        void onResume();

        void onDestroy();

        void onStart();
    }
}
