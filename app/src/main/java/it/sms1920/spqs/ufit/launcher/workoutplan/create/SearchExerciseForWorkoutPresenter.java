package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public class SearchExerciseForWorkoutPresenter implements iSearchForWorkout.Presenter {

    private iSearchForWorkout.View view;

    public SearchExerciseForWorkoutPresenter(iSearchForWorkout.View view) {
        this.view = view;
    }


    @Override
    public void onQueryTextChanged(final String keyword) {
        view.notifyQueryTextChangedToAdapter(keyword.trim());
    }

    @Override
    public void onExerciseSelectionEnded(ArrayList<String> exercisesId) {
        view.sendResultBack(exercisesId);
    }

    @Override
    public void onBackPressed() {
        view.back();
    }
}
