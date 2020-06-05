package it.sms1920.spqs.ufit.contract;

public interface RegistrationContract {
    interface View {

        enum InputErrorType {
            EMAIL_FIELD_EMPTY,
            EMAIL_FORMAT_NOT_VALID,
            PASSWORD_FIELD_EMPTY,
            PASSWORD_FORMAT_NOT_VALID,
            PASSWORDS_NOT_MATCHING
        }

        enum AuthResultType {
            SINGUP_SUCCESSFULL,
            USER_ALREADY_EXISTS
        }

         int SINGUP_SUCCESSFULL = 0;
         int EMAIL_NOT_VALID = 1;
         int USER_ALREADY_EXISTS = 2;

        void showSignUpSuccessFully(String message);

        void showSignUpFail(String message);

        void showValidationError(String message);
    }

    interface Presenter {
        void onSignUp(String email, String password, String confirmPassword);

        void onResultSignUp(int check);
    }
}
