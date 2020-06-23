package it.sms1920.spqs.ufit.launcher.userprofile.login;

public interface LoginContract {
    interface View {


        void setInputError(Presenter.InputErrorType inputErrorType);

        void setLoginError(Presenter.AuthResultType authResultType);

        void endActivity();

        void setEnabledUi(boolean enabled);
    }

    interface Presenter {

        enum InputErrorType {
            EMAIL_FIELD_EMPTY,
            PASSWORD_FIELD_EMPTY,
            EMAIL_FORMAT_NOT_VALID,
        }

        void onLoginButtonClicked(String email, String password);

        enum AuthResultType {
            FAILURE,
            SUCCESS
        }

        void onBackPressed();
    }
}

