package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public interface SelectExercisesContract {
    interface View {
        void back();
        void notifyQueryTextChangedToAdapter(final String keyword);
        void sendResultBack(ArrayList<String> exercisesId);
    }

    interface Presenter {
        int CODE_SUCCESSFUL = 0;
        int CODE_NOT_SUCCESSFUL = 1;

        void onBackPressed();
        void onQueryTextChanged(final String keyword);
        void onExerciseSelectionEnded(ArrayList<String> exercisesId);
    }
}
