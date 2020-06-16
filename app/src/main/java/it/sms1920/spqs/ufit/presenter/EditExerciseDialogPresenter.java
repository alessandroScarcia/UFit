package it.sms1920.spqs.ufit.presenter;

import android.util.Log;

import it.sms1920.spqs.ufit.contract.iExerciseDialog;
import it.sms1920.spqs.ufit.contract.iSearchClient;
import it.sms1920.spqs.ufit.model.SearchExercise;

public class EditExerciseDialogPresenter implements iSearchClient, iExerciseDialog.Presenter {

    private iExerciseDialog.View view;
    private SearchExercise mSearch;
    private String exerciseName;

    public EditExerciseDialogPresenter(iExerciseDialog.View view) {
        this.view = view;
        mSearch = new SearchExercise(this);
        exerciseName = "null";
    }

    @Override
    public void notifyResultListReady() {
        exerciseName = mSearch.getExerciseResultList().size() > 0 ? mSearch.getExerciseResultList().get(0).getName() : "not found";
        view.setExerciseName(exerciseName.substring(0, 1).toUpperCase() + exerciseName.substring(1).toLowerCase());
    }

    @Override
    public String getExerciseName() {
        return exerciseName;
    }

    @Override
    public void onExerciseNameRequested(String exerciseId) {
        mSearch.getExerciseById(exerciseId);
        Log.d("ddddddd", "onExerciseNameRequested: " + exerciseId);
    }
}
