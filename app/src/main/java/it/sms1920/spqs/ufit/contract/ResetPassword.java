package it.sms1920.spqs.ufit.contract;

public interface ResetPassword {
    interface View {

        void showCheckMailBox();
        void closeActivity();
    }

    interface Presenter {

        void onResetPassword(String email);
        void onBackPressed();
    }
}
