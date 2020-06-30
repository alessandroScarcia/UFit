package it.sms1920.spqs.ufit.launcher.trainer;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import it.sms1920.spqs.ufit.launcher.LauncherActivity;
import it.sms1920.spqs.ufit.launcher.R;

import static android.view.View.GONE;

public class BluetoothLinkingFragment extends Fragment implements BluetoothLinkingContract.View {

    private static final int VISIBILITY_REQUEST = 0;
    private static final int ACTIVATE_BLUETOOTH = 1;
    LauncherActivity launcher;

    private TextView status;
    private MaterialButton btnConnect;
    private MaterialButton btnDisconnect;
    private Dialog dialog;

    private BluetoothLinkingContract.Presenter presenter;


    public BluetoothLinkingFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bluetooth_linking, container, false);
        status = view.findViewById(R.id.txtStatus);
        btnConnect = view.findViewById(R.id.btnConnetti);
        btnDisconnect = view.findViewById(R.id.btnDisconnetti);
        presenter = new BluetoothLinkingPresenter(this, new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1));


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onConnectButtonClicked();
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDisconnectButtonClicked();
            }
        });


        launcher = (LauncherActivity) getActivity();
        return view;
    }

    @Override
    public void showNoBluetoothAdapterAvailable() {
        Toast.makeText(getContext(), getString(R.string.senzaBluetooth), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getNoFoundStringError() {
        return getString(R.string.noFound);
    }

    @Override
    public void disableButton() {
        btnConnect.setEnabled(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(launcher, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getConnectedWithString() {
        return getString(R.string.connessoCon);
    }

    @Override
    public String getLostConnectionString() {
        return getString(R.string.lostConnection);
    }

    @Override
    public void notifyConnectionStateChanged() {
        launcher.insertBluetoothLinkingFragment();
    }

    @Override
    public void closeDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    public void setStatus(String text) {
        status.setText(text);
    }

    @Override
    public void setButtonText(String text) {
        btnConnect.setText(text);
    }

    @Override
    public String getConnectingString() {
        return getString(R.string.connecting);
    }

    @Override
    public String getSameRoleString() {
        return getString(R.string.sameRole);
    }

    @Override
    public void alreadyConnected(boolean isTrainer) {

        btnConnect.setVisibility(GONE);
        btnDisconnect.setVisibility(View.VISIBLE);

        if (isTrainer) setStatus(getString(R.string.userLinked));
        else setStatus(getString(R.string.trainerLinked));

    }

    @Override
    public String getDisconnectionString() {
        return getString(R.string.disconnection);
    }

    @Override
    public String getConnectHint(Boolean role) {
        String string;

        if (role) {
            string = getString(R.string.connectionHintForTrainer);
        } else {
            string = getString(R.string.connectionHintForUser);
        }
        return string;
    }

    @Override
    public void showLinkedUserInfo(String name, String surname, Integer gender, String imageUrl, String birthDate) {
        View view = getView();

        AppCompatImageView img = view.findViewById(R.id.imageTrainer);
        img.setVisibility(GONE);

        ConstraintLayout lyt = view.findViewById(R.id.lytLinkedInfo);
        lyt.setVisibility(View.VISIBLE);

        TextView tvNameSurname = view.findViewById(R.id.tvNameSurname);
        TextView tvGender = view.findViewById(R.id.tvGender);
        TextView tvBirthDate = view.findViewById(R.id.tvBirthDate);

        String nameSurnameText = getString(R.string.name) + ": " + name + " " + surname;
        tvNameSurname.setText(nameSurnameText);

        String genderText = getText(R.string.gender) + ": " + getResources().getStringArray(R.array.genders)[gender];
        tvGender.setText(genderText);

        String birthDateText = getString(R.string.birth_date) + ": " + birthDate;
        tvBirthDate.setText(birthDateText);


        ImageView imgProfile = view.findViewById(R.id.imgProfile);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.img_profile_placeholder)
                .into(imgProfile);

    }

    @Override
    public void activeVisibility(BluetoothAdapter bluetoothAdapter) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        startActivityForResult(intent, VISIBILITY_REQUEST);
    }

    @Override
    public String getButtonText(Boolean isTrainer) {
        String text;
        if (isTrainer) {
            text = getString(R.string.connetti);
        } else {
            text = getString(R.string.active_bluetooth);
        }
        return text;
    }

    @Override
    public void showBluetoothDialog(BluetoothAdapter bluetoothAdapter, ArrayAdapter<String> discoveredDevicesAdapter, BroadcastReceiver discoveryFinishReceiver) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_bluetooth_dialog);
        dialog.setTitle(getString(R.string.dispositiviBT));

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ACTIVATE_BLUETOOTH);
        } else {

            continueShowingBluetoothDialog(bluetoothAdapter, discoveredDevicesAdapter, discoveryFinishReceiver);
        }
    }


    public void continueShowingBluetoothDialog(BluetoothAdapter bluetoothAdapter, ArrayAdapter<String> discoveredDevicesAdapter, BroadcastReceiver discoveryFinishReceiver) {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        ActivityCompat.requestPermissions(launcher,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        bluetoothAdapter.startDiscovery();

        //Initializing bluetooth adapters
        discoveredDevicesAdapter.clear();
        ArrayAdapter<String> pairedDevicesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        //locate listviews and attach the adapters
        ListView lstAssociati = dialog.findViewById(R.id.pairedDeviceList);
        ListView lstTrovati = dialog.findViewById(R.id.discoveredDeviceList);
        lstAssociati.setAdapter(pairedDevicesAdapter);
        lstTrovati.setAdapter(discoveredDevicesAdapter);


        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getContext().registerReceiver(discoveryFinishReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getContext().registerReceiver(discoveryFinishReceiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            pairedDevicesAdapter.add(getString(R.string.noPaired));
        }

        presenter.setChatController();

        //Handling listview item click event
        final BluetoothAdapter finalBluetoothAdapter = bluetoothAdapter;

        lstAssociati.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalBluetoothAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                presenter.onConnectToDeviceRequested(address);
                dialog.dismiss();
            }

        });

        lstTrovati.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finalBluetoothAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                presenter.onConnectToDeviceRequested(address);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVATE_BLUETOOTH:
                if (resultCode == Activity.RESULT_OK) {
                    presenter.setChatController();
                    presenter.onConnectButtonClicked();
                } else {
                    Toast.makeText(launcher, getString(R.string.bluetoothDisabled), Toast.LENGTH_SHORT).show();
                }
                break;
            case VISIBILITY_REQUEST:
                if (resultCode == 30) {
                    Toast.makeText(launcher, getString(R.string.waitingtrainer), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(launcher, getString(R.string.bluetoothDisabled), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();

    }


}

