package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import android.media.Image;

import java.util.ArrayList;

public interface iWorkoutExerciseListAdapter {

    interface View {
        void callNotifyItemRemoved(int position);
        void callNotifyItemRangeChanged(int position, int range);
        void callNotifyDataSetChanged();
        void onItemRemoved(int position);

        interface Item {
            void setName(String name);
            void setImage(Image image);
            void setId(String id);
            //void setDetails(ArrayList<Integer> reps, ArrayList<Float> loads);
            void addSerie(int reps, float loads);
        }
    }

    interface Presenter {
        void onBindExerciseItemViewAtPosition(View.Item holder, int position);
        int getExerciseCount();
        void removeItemAt(int position);
        void onNewExercisesAdded(ArrayList<String> exerciseId);
        ArrayList<String> onExercisesIdRequested();
    }

}
