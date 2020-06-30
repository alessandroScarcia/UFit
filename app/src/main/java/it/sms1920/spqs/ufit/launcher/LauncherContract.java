package it.sms1920.spqs.ufit.launcher;


public interface LauncherContract {

    interface View {
        void insertHomeFragment();

        void insertPlansFragment();

        void insertTrainerFragment();

        void insertStatsFragment();

        void insertProfileFragment();

        void insertProfileSettingsFragment();

        void startSearchActivity();

        void insertChooseFragment();

        void endActivity();

        void resetActivity();

        String getWorkoutId();

        void startEditWorkoutActivity(String id);

        void startTimer();

        void setToolbarTitle(String text);

        void insertBluetoothLinkingFragment();

        String getLoginRequiredString();

        enum FragType {HOME, PLANS, SHOW_PLAN, TRAINER, STATS, PROFILE, PROFILE_SETTINGS, CHOOSE}
    }

    interface Presenter {


        void onHomeIconClicked();

        void onPlansIconClicked();

        void onTrainerIconClicked();

        void onStatsIconClicked();

        void onProfileIconClicked();

        void onSearchIconClicked();

        void onShowPlanClicked();

        void onBackPressed();

        void onLogOutIconClicked();

        void onProfileSettingsClicked();

        void onEditIconClicked();

        void onTimerIconClicked();
    }

}
