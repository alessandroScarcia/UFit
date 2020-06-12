package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.iSearchForWorkout;

public class SearchExerciseForWorkoutPresenter implements iSearchForWorkout.Presenter {

    private iSearchForWorkout.View view;

    public SearchExerciseForWorkoutPresenter(iSearchForWorkout.View view) {
        this.view = view;
    }

    @Override
    public void onBackPressed() {
        view.back();
    }
}
