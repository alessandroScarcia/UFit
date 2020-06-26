package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.password;

public interface EditPasswordContract {
    interface View {
        void endActivity();

        void setError(Presenter.PasswordError error);

        void reauthenticate();
    }

    interface Presenter {
        void onBackPressed();

        void confirmEdit(String password, String confirmPassword);

        enum PasswordError {
            PASSWORD_EMPTY,
            PASSWORD_TOO_WEAK,
            PASSWORDS_NOT_MATHING
        }
    }
}
