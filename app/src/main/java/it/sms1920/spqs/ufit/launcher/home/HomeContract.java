package it.sms1920.spqs.ufit.launcher.home;

import android.view.View;

public interface HomeContract {
    interface View {

        void startAssembliesReportActivity();
    }

    interface Presenter {

        void onAssembliesReportClicked();


        void callGetRandomAdvice(HomeFragment homeFragment);
    }
}
