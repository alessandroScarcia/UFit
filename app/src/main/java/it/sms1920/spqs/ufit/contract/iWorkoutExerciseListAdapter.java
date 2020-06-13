package it.sms1920.spqs.ufit.contract;

import android.media.Image;

import java.util.ArrayList;

public interface iWorkoutExerciseListAdapter {

    interface View {
        void callNotifyDataSetChanged();

        interface Item {
            void setName(String name);
            void setImage(Image image);
            void setId(int Id);
            void setDetails(ArrayList<Integer> reps, ArrayList<Float> loads);
        }
    }

    interface Presenter {
        void onBindExerciseItemViewAtPosition(View.Item holder, int position);
        int getExerciseCount();
        void onNewExerciseAdded(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads);
    }

}
