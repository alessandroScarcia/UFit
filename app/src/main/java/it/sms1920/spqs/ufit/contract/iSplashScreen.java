package it.sms1920.spqs.ufit.contract;

public interface iSplashScreen {
    int SPLASH_TIME_OUT = 1000;

    interface View{
        void startApp();
    }

    interface Presenter{
        void onStartApp();
    }
}
