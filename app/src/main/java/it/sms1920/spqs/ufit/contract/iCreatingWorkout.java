package it.sms1920.spqs.ufit.contract;

import java.util.ArrayList;

public interface iCreatingWorkout {

    interface View {
        void back();

        void startSearchExerciseForWorkout(int requestCode);

        void communicateNewExerciseToAdapter(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads);
    }

    interface Presenter {
        void onBackPressed();

        void onAddIconClicked();

        void onAddExerciseSuccessfulDone(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads);
    }

}
