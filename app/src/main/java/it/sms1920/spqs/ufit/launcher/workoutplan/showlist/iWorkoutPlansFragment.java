package it.sms1920.spqs.ufit.launcher.workoutplan.showlist;

public interface iWorkoutPlansFragment {
    interface View {
        void showPersonalWorkoutPlans();

        void showTrainerWorkoutPlans();

        void addNewPlan();

        void insertShowWorkoutPlanFragment(String workoutPlanId);
    }

    interface Presenter {
        void onTabSelectedAtPosition(int position);

        void onAddClicked();
    }
}
