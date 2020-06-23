package it.sms1920.spqs.ufit.launcher.userprofile.resetpassword;

public interface ResetPasswordContract {
    interface View {
        void showEmailSentMessage();

        void closeActivity();

        void setEnabledUi(boolean enabled);

        void setError();
    }

    interface Presenter {
        void onSendEmailButtonClicked(String email);

        void onBackPressed();
    }
}
