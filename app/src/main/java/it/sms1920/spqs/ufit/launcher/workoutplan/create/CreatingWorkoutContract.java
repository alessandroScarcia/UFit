package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public interface CreatingWorkoutContract {

    interface View {
        void back();

        void startSearchExerciseForWorkout(int requestCode);

        void communicateNewExercisesToAdapter(ArrayList<String> exercisesId);
    }

    interface Presenter {
        void onBackPressed();

        void onAddIconClicked();

        void onAddExercisesSuccessfulDone(ArrayList<String> exercisesId);
    }

}
