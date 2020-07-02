package it.sms1920.spqs.ufit.launcher.traineradvice;

public interface AdviceContract {
    interface View {
        void openDialog();

        void addNewAdvice(String title, String description);

        void endActivity();
    }

    interface Presenter {
        void onFabButtonClicked();

        void onBackPressed();
    }
}
