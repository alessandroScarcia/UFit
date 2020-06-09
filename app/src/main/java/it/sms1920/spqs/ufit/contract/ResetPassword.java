package it.sms1920.spqs.ufit.contract;

public interface ResetPassword {
    interface View {

        void showCheckMailBox();
    }

    interface Presenter {

        void onResetPassword(String email);

    }
}
