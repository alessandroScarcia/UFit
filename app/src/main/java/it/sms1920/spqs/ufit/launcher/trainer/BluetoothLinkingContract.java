package it.sms1920.spqs.ufit.launcher.trainer;

import android.bluetooth.BluetoothAdapter;
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

        void notifyConnectionStateChanged();

        void closeDialog();

        void setStatus(String text);

        void setButtonText(String text);

        String getConnectingString();

        String getSameRoleString();

        void alreadyConnected(boolean isTrainer);

        String getDisconnectionString();

        String getConnectHint(Boolean role);

        void showLinkedUserInfo(String name, String surname, Integer gender, String imageUrl, String birthDate);

        void activeVisibility(BluetoothAdapter bluetoothAdapter);

        String getButtonText(Boolean isTrainer);

        void setAdviceIcon(boolean visible);
    }

    interface Presenter {

        void onConnectButtonClicked();

        void onConnectToDeviceRequested(String address);

        void setChatController();

        void onResume();

        void onDestroy();

        void onStart();

        void onDisconnectButtonClicked();
    }
}
