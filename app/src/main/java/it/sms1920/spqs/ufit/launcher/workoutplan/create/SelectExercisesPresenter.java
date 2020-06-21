package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import java.util.ArrayList;

public class SelectExercisesPresenter implements SelectExercisesContract.Presenter {

    private SelectExercisesContract.View view;

    public SelectExercisesPresenter(SelectExercisesContract.View view) {
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
