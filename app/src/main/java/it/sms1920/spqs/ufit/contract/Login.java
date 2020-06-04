package it.sms1920.spqs.ufit.contract;

public interface Login {
    interface View {
        int EMAIL_FIELD_EMPTY = -1;
        int PASSWORD_FIELD_EMPTY = -2;
        int EMAIL_NOT_VALID = -3;
        int SIGNIN_SUCCESSFULL = 0;
        int EMAIL_NOT_MATCH = 3;
        int PASSWORD_NOT_MATCH = 4;
        int FIELDS_CORRECT = 5;


        void showSignInFail(int signInError);

        void showInputFail(int inputError);
    }

    interface Presenter {

        void checkFields(String emailField, String passwordField);

        void onSignIn(String email, String password);

        void onResultSignIn(int check);

    }
}
