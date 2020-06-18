package it.sms1920.spqs.ufit.launcher.splashscreen;

public interface SplashScreenContract {
    int SPLASH_TIME_OUT = 1000;

    interface View {
        void startApp();
    }

    interface Presenter {
        void onStartApp();
    }
}
