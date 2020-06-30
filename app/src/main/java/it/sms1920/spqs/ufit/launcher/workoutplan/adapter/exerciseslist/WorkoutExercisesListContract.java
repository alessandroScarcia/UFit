package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist.ExerciseSetListAdapter;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;

public interface WorkoutExercisesListContract {

    interface View {
        void callNotifyDataSetChanged();
        void onItemRemoved(int position);


        interface Item {
            void setName(String name);
            void setImage(String imageURL);
            void setId(String id);
            void setExerciseSets(List<ExerciseSetItem> exerciseSets);

            ExerciseSetListAdapter setExerciseSetsAdapter(int position, String workoutPlanId, String exerciseId, List<ExerciseSetItem> /*Object*/ setsListReference);

            void addExerciseSet(int reps, float loads);

            void setNoItemView();
        }
    }

    interface Presenter {

        void onSaveWorkoutPlanChangesRequested(String name);
        void onSaveNewWorkoutPlanRequested(String name);
        void onBindExerciseItemViewAtPosition(View.Item holder, int position);
        int getExerciseCount();
        void removeItemAt(int position);
        void onNewExercisesAdded(ArrayList<String> exerciseId);
        ArrayList<String> onExercisesIdRequested();
        List<ExerciseSetItem> onSetsListRequested(int position);


        void onUpdateRequested();
    }

}
