package it.sms1920.spqs.ufit.contract;

import android.content.Intent;

public interface iExercise {

    interface View {
        void setName(String name);

        void setDescription();

        void setDescription(String description);

        void setImage();

        void closeActivity();
    }

    interface Presenter {
        void onCreateCompleted(Intent intent);

        void onBackPressed();
    }

}
