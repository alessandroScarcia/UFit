package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;

public interface ExerciseSetListContract {
    interface View {
        void callNotifyItemRemoved(int position);
        void callNotifyItemRangeChanged(int position, int range);
        void callNotifyDatasetChanged();
        void onItemRemoved(int position);

        interface Item {
            void setReps(String reps);
            void setLoad(String load);
        }
    }

    interface Presenter {
        void onBindItemViewAtPosition(View.Item holder, int position);
        int getSeriesCount();
        void onSerieAdded(int reps, float loads);
        void setSeriesList(List<ExerciseSetItem> list);
        void removeItemAt(int position);
        void onUpdateRepsRequested(int newRep, int position);
        void onUpdateLoadsRequested(float newLoad, int position);
        ArrayList<Integer> getReps();
        ArrayList<Float> getLoads();
    }
}
