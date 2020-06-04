package it.sms1920.spqs.ufit.contract;

public interface Login {
    interface View {
        void showSignInSuccessFully(String message);

        void showSignInFail(String message);
    }

    interface Presenter {
        void onSignIn(String email, String password);

        void onResultSignIn(int check);

    }
}
