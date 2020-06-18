package it.sms1920.spqs.ufit.launcher.userprofile.login;

public interface LoginContract {
    interface View {


        void setInputError(Presenter.InputErrorType inputErrorType);

        void setSignInError(Presenter.AuthResultType authResultType);

        void startLauncherActivity();

        void setEnabledUI(boolean enabled);
    }

    interface Presenter {

        enum InputErrorType {
            EMAIL_FIELD_EMPTY,
            PASSWORD_FIELD_EMPTY,
            EMAIL_FORMAT_NOT_VALID,
        }

        enum AuthResultType {
            EMAILS_NOT_MATCH,
            PASSWORDS_NOT_MATCH,
            SUCCESS
        }

        void onSignIn(String email, String password);

        void returnSignInResult(AuthResultType check);

        void signInSuccessful();

        void onBackPressed();
    }
}

