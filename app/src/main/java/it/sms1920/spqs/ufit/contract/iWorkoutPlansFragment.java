package it.sms1920.spqs.ufit.contract;

public interface iWorkoutPlansFragment {
    interface View {
        void showPersonalWorkoutPlans();

        void showTrainerWorkoutPlans();

        void addNewPlan();
    }

    interface Presenter {
        void onTabSelectedAtPosition(int position);

        void onAddClicked();
    }
}
