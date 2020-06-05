package it.sms1920.spqs.ufit.contract;

public interface Login {
    interface View {

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

        void setInputError(InputErrorType inputErrorType);
        void setSignInError(AuthResultType authResultType);
        void startLauncherActivity();
        void setEnabledUI(boolean enabled);
    }

    interface Presenter {
        void onSignIn(String email, String password);
        void returnSignInResult(View.AuthResultType check);
        void signInSuccessful();
    }
}

