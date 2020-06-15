package it.sms1920.spqs.ufit.contract;

import android.media.Image;

import java.util.ArrayList;

import it.sms1920.spqs.ufit.model.ExerciseSetItem;

public interface iExerciseSeriesRepsListAdapter {
    interface View{

        void callNotifyItemRemoved(int position);
        void callNotifyItemRangeChanged(int position, int range);
        void callNotifyDatasetChanged();
        void onItemRemoved(int position);

        interface Item {
            void setSerie(String serie);
            void setReps(String reps);
            void setLoad(String load);
        }
    }

    interface Presenter{
        void onBindItemViewAtPosition(View.Item holder, int position);
        int getSeriesCount();
        void onSerieAdded(int reps, float loads);
        void setSeriesList(ArrayList<ExerciseSetItem> list);
        void removeItemAt(int position);
        ArrayList<Integer> getReps();
        ArrayList<Float> getLoads();
    }
}