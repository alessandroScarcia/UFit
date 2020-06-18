package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public class CreatingWorkoutPresenter implements iCreatingWorkout.Presenter {


    private final static int PICK_EXERCISE = 1;
    private iCreatingWorkout.View view;

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

    @Override
    public void onAddExerciseSuccessfulDone(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads) {
        view.communicateNewExerciseToAdapter(exerciseId, exerciseName, reps, loads);
    }
}
