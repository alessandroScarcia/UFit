package it.sms1920.spqs.ufit.launcher.userprofile.resetpassword;

public interface ResetPasswordContract {
    interface View {

        void showCheckMailBox();

        void closeActivity();
    }

    interface Presenter {

        void onResetPassword(String email);

        void onBackPressed();
    }
}
