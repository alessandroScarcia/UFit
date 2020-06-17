package it.sms1920.spqs.ufit.contract;

import android.content.Intent;

import java.util.List;

public interface iExercise {
    interface View {
        void setName(String name);

        void setTvExerciseDescription(String tvExerciseDescription);

        void setImage(String imageUrl);

        void closeActivity();

        void setMuscleList(List<String> muscleList);
    }

    interface Presenter {
        void onCreateCompleted(Intent intent);
        void onBackPressed();
    }

}
