package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.iWorkoutPlansFragment;

public class WorkoutPlansPresenter implements iWorkoutPlansFragment.Presenter {
    private final iWorkoutPlansFragment.View view;

    public WorkoutPlansPresenter(iWorkoutPlansFragment.View view) {
        this.view = view;
    }

    @Override
    public void onTabSelectedAtPosition(int position) {
        if (position == 0) {
            view.showPersonalWorkoutPlans();
        } else if (position == 1) {
            view.showTrainerWorkoutPlans();
        } else {
            throw new IllegalArgumentException("Invalid value for argument position.");
        }
    }

    @Override
    public void onAddClicked() {
        view.addNewPlan();
    }
}
