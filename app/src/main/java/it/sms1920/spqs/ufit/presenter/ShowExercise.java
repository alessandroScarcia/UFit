package it.sms1920.spqs.ufit.presenter;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.contract.iExercise;
import it.sms1920.spqs.ufit.contract.iSearchClient;
import it.sms1920.spqs.ufit.model.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.SearchExercise;

public class ShowExercise implements iExercise.Presenter, iSearchClient {
    private static final String TAG = ShowExercise.class.getCanonicalName();
    private iExercise.View view;

    private SearchExercise searchExercise;

    public ShowExercise(iExercise.View view) {
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
            view.setDescription(exercise.getDescription());
            view.setMuscleList(exercise.getMuscleList());
            view.setImage(exercise.getImageUrl());
        }
    }
}
