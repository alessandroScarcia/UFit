package it.sms1920.spqs.ufit.launcher;


public interface LauncherContract {

    interface View {
        void insertHomeFragment();

        void insertPlansFragment();

        void insertTrainerFragment(boolean isAnonymous, boolean isTrainer, boolean isLinked);

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

        void updateStatus();

        enum UserLinkingState {USER_NO_LINKED, USER_LINKED, TRAINER_NO_LINKED, TRAINER_LINKED}

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

        //UserLinkingState onCheckUserLinkingRequested();
    }

}
