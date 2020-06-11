package it.sms1920.spqs.ufit.contract;

public interface iWorkoutPlansFragment {
    interface View {
        void showPersonalWorkoutPlans();

        void showTrainerWorkoutPlans();

        void insertShowWorkoutPlanFragment(int workoutPlanId);
    }

    interface Presenter {
        void onTabSelectedAtPostition(int position);
    }
}
