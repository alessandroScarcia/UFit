package it.sms1920.spqs.ufit.contract;

public interface RegistrationContract {
    interface View {
        void showSignUpSuccessFully(String message);

        void showSignUpFail(String message);

        void showValidationError(String message);
    }

    interface Presenter {
        void onSignUp(String email, String password, String confirmPassword);

        void onResultSignUp(int check);
    }
}
