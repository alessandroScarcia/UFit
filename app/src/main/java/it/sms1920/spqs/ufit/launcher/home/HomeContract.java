package it.sms1920.spqs.ufit.launcher.home;

public interface HomeContract {
    interface View {

        void startAssembliesReportActivity();
    }

    interface Presenter {

        void onAssembliesReportClicked();
    }
}
