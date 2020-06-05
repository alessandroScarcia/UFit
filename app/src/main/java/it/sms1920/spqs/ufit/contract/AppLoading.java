package it.sms1920.spqs.ufit.contract;

import android.content.Context;

public interface AppLoading {
    interface View {
        void showLauncher();

        Context retriveApplicationContext();
    }

    interface Presenter {
        void appInitialized();
    }
}
