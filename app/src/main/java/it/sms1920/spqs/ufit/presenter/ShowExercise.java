package it.sms1920.spqs.ufit.presenter;

import android.content.Intent;

import it.sms1920.spqs.ufit.contract.ShowExerciseContract;
import it.sms1920.spqs.ufit.model.Exercise;

public class ShowExercise implements ShowExerciseContract.presenter {

    ShowExerciseContract.view view;
    Exercise exercise;

    public ShowExercise(ShowExerciseContract.view view) {
        this.view = view;
    }


    @Override
    public void onCreateComplete(Intent intent) {
        this.exercise = (Exercise) intent.getSerializableExtra("Exercise");
        view.load(this.exercise);
    }
}
