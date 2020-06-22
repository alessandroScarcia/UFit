package it.sms1920.spqs.ufit.launcher.userprofile.choose;

public interface ChooseContract {
    interface View {
        void startRegisterActivity();

        void startLoginActivity();

        void insertProfileFragment();
    }

    interface Presenter {
        void onButtonRegisterClicked();

        void onButtonLoginClicked();

        void onChooseFragmentResume();
    }
}
