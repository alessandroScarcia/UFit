package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.email;

public interface EditEmailContract {
    interface View {

        void endActivity();

        void setError(Presenter.EmailError error);

        void reauthenticate();

        void showEmail(String email);

        void showAskReauthenticateDialog();
    }

    interface Presenter {

        void onBackPressed();

        void confirmEdit(String email);

        void onReautenticate();

        enum EmailError {
            EMAIL_EMPTY,
            EMAIL_NOT_VALID,
            ALREADY_USED
        }
    }
}
