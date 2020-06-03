package it.sms1920.spqs.ufit.contract;

public interface LauncherManagerContract {

    interface view {
        void insertHomeFragment();
        void insertPlansFragment();
        void insertTrainerFragment();
        void insertStatsFragment();
        void insertProfileFragment();
        void startSearchActivity();
    }

    interface presenter {
        void onHomeIconClick();
        void onPlansIconClick();
        void onTrainerIconClick();
        void onStatsIconClick();
        void onProfileIconClick();
        void onSearchIconClick();
    }

}
