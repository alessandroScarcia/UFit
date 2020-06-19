package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public interface iSearchForWorkout {
    interface View {
        void back();
        void notifyQueryTextChangedToAdapter(final String keyword);
        void sendResultBack(ArrayList<String> exercisesId);
    }

    interface Presenter {
        void onBackPressed();
        void onQueryTextChanged(final String keyword);
        void onExerciseSelectionEnded( ArrayList<String> exercisesId);
    }
}
