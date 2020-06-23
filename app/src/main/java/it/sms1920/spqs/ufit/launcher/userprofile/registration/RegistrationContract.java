package it.sms1920.spqs.ufit.launcher.userprofile.registration;

public interface RegistrationContract {
    interface View {

        void setInputError(Presenter.InputErrorType inputErrorType);

        void setRegistrationError(Presenter.AuthResultType authResultType);

        void endActivity();

        void setEnabledUI(boolean enabled);

        void startLoginActivity();

    }

    interface Presenter {

        enum InputErrorType {
            EMAIL_FIELD_EMPTY,
            EMAIL_FORMAT_NOT_VALID,
            PASSWORD_FIELD_EMPTY,
            PASSWORD_FORMAT_NOT_VALID,
            PASSWORDS_NOT_MATCHING
        }

        enum AuthResultType {
            SIGNUP_SUCCESSFUL,
            USER_ALREADY_EXISTS
        }

        void onRegisterButtonClicked(String email, String password, String confirmPassword);

        void onLoginButtonClicked();

        void onBackPressed();
    }
}
