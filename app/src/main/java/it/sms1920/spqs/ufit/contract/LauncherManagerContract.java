package it.sms1920.spqs.ufit.contract;


public interface LauncherManagerContract {

    interface view {

        enum FragType { HOME, PLANS, TRAINER, STATS, PROFILE }

        void insertHomeFragment();
        void insertPlansFragment();
        void insertTrainerFragment();
        void insertStatsFragment();
        void insertProfileFragment();
        void startSearchActivity();
        void endActivity();
        void resetMenuIcons();
    }

    interface presenter {
        void onHomeIconClick();
        void onPlansIconClick();
        void onTrainerIconClick();
        void onStatsIconClick();
        void onProfileIconClick();
        void onSearchIconClick();
        void onBackPressed();
    }

}
