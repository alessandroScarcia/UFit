package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public class CreatingWorkoutPresenter implements CreatingWorkoutContract.Presenter {


    private final static int PICK_EXERCISE = 1;
    private CreatingWorkoutContract.View view;

    public CreatingWorkoutPresenter(CreatingWorkoutContract.View view) {
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

    @Override
    public void onAddExercisesSuccessfulDone(ArrayList<String> exercisesId) {
        view.communicateNewExercisesToAdapter(exercisesId);
    }
}
