package it.sms1920.spqs.ufit.contract;

public interface iChangeProfileInfo {

    interface View {
        void showAllProfileInfo();

    }

    interface Presenter {
        void onShowAllProfileInfo();
    }
}
