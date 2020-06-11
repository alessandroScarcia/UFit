package it.sms1920.spqs.ufit.contract;


public interface iLauncher {

    interface view {

        enum FragType {HOME, PLANS, TRAINER, STATS, PROFILE}

        void insertHomeFragment();

        void insertPlansFragment();

        void insertTrainerFragment();

        void insertStatsFragment();

        void insertProfileFragment();

        void startSearchActivity();

        void startLoginActivity();

        void endActivity();

        void resetActivity();
    }

    interface presenter {
        void onHomeIconClicked();

        void onPlansIconClicked();

        void onTrainerIconClicked();

        void onStatsIconClicked();

        void onProfileIconClicked();

        void onSearchIconClicked();

        void onBackPressed();

        void onLogOutIconClicked();
    }

}
