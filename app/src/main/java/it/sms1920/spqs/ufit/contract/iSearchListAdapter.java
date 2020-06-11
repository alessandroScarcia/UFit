package it.sms1920.spqs.ufit.contract;

import android.media.Image;

public interface iSearchListAdapter {

    interface View {

        void callNotifyDataSetChanged();
//        void showExercise(int exerciseId, String exerciseName);

        interface Item {
            void setName(String name);

            void setImage(Image image);

            void setPosition(int position);

            void setId(int Id);
        }
    }

    interface Presenter {
        void changeQueryText(String query);

        void onBindExerciseItemViewAtPosition(iSearchListAdapter.View.Item holder, int position);

        int getExerciseCount();
//        void onClickedExerciseHolder(int position);
    }

}
