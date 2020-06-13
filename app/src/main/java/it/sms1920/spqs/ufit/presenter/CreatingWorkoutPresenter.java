package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.iCreatingWorkout;

public class CreatingWorkoutPresenter implements iCreatingWorkout.Presenter {


    private final static int PICK_EXERCISE = 1;
    private  iCreatingWorkout.View view;

    public CreatingWorkoutPresenter(iCreatingWorkout.View view) {
        this.view = view;
    }

    @Override
    public void onBackPressed() {
        view.back();
    }

    @Override
    public void onAddIconClicked() {
        view.startSearchExerciseForWorkout(PICK_EXERCISE);
    }
}
