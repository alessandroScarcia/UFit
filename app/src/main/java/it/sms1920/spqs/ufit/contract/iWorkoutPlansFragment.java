package it.sms1920.spqs.ufit.contract;

public interface iWorkoutPlansFragment {
    interface View {
        void showPersonalWorkoutPlans();

        void showTrainerWorkoutPlans();
    }

    interface Presenter {
        void onTabSelectedAtPostition(int position);
    }
}
