package it.sms1920.spqs.ufit.presenter;

import java.util.ArrayList;

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

    @Override
    public void onAddExerciseSuccessfulDone(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads) {
        view.communicateNewExerciseToAdapter(exerciseId,reps,loads);
    }
}
