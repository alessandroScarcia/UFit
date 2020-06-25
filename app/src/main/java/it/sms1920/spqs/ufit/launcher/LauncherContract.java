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

        enum FragType {HOME, PLANS, SHOW_PLAN, TRAINER, STATS, PROFILE, PROFILE_SETTINGS}
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

        void onShowPlanClosed();

        void onProfileSettingsClicked();

        void onEditIconClicked();
    }

}
