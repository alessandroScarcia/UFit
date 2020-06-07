package it.sms1920.spqs.ufit.contract;

import android.content.Intent;

import it.sms1920.spqs.ufit.model.Exercise;

public interface ShowExerciseContract {

    interface View {
        void setName(String name);

        void setDescription();

        void setDescription(String description);

        void setImage();

        void closeActivity();
    }

    interface Presenter {
        void onCreateComplete(Intent intent);

        void onBackPressed();
    }

}
