package it.sms1920.spqs.ufit.launcher.workoutplan.showlist;

public class WorkoutPlansPresenter implements WorkoutPlansContract.Presenter {
    private final WorkoutPlansContract.View view;

    public WorkoutPlansPresenter(WorkoutPlansContract.View view) {
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
