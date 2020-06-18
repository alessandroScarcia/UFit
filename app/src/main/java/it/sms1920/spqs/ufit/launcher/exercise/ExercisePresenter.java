package it.sms1920.spqs.ufit.launcher.exercise;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.model.search.iSearchClient;
import it.sms1920.spqs.ufit.model.search.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.search.SearchExercise;

public class ExercisePresenter implements ExerciseContract.Presenter, iSearchClient {
    private static final String TAG = ExercisePresenter.class.getCanonicalName();
    private ExerciseContract.View view;

    private SearchExercise searchExercise;

    public ExercisePresenter(ExerciseContract.View view) {
        this.view = view;
        searchExercise = new SearchExercise(this);
    }


    @Override
    public void onCreateCompleted(Intent intent) {
        String exerciseId = intent.getStringExtra("exerciseId");

        searchExercise.getExerciseById(exerciseId);
    }

    @Override
    public void onBackPressed() {
        view.closeActivity();
    }

    @Override
    public void notifyResultListReady() {
        List<ExerciseDetailed> exerciseDetailedList = new ArrayList<>(searchExercise.getExerciseResultList());

        if (exerciseDetailedList.size() != 1) {
            Log.d(TAG, "Error extracting exercise information");
        } else {
            ExerciseDetailed exercise = exerciseDetailedList.get(0);

            view.setName(exercise.getName());
            view.setTvExerciseDescription(exercise.getDescription());
            view.setMuscleList(exercise.getMuscleList());
            view.setImage(exercise.getImageUrl());
        }
    }
}
