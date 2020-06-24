package it.sms1920.spqs.ufit.launcher.workoutplan.showsingle;

public interface ShowWorkoutPlanContract {
    interface View {
        void setToolbarTextEqualToName(String name);
        void showToolbarNavigationButton();
        void hideToolbarNavigationButton();
    }

    interface Presenter {
    }
}
