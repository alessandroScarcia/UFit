package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public interface CreatingWorkoutContract {

    interface View {
        void back();
        void startSearchExerciseForWorkout(int requestCode);
        void communicateNewExercisesToAdapter(ArrayList<String> exercisesId);
        boolean checkIfSavable();
        void showError();
        void saveData();
    }

    interface Presenter {
        int PICK_EXERCISE = 1;
        int RESULT_SUCCESSFUL = 0;

        void onBackPressed();

        void onAddIconClicked();

        void onAddExercisesSuccessfulDone(ArrayList<String> exercisesId);

        void onSaveDataRequested();
    }

}
