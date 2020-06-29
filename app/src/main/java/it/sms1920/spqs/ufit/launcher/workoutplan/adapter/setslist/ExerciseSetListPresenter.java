package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetDetails;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;

public class ExerciseSetListPresenter implements ExerciseSetListContract.Presenter {

    private ExerciseSetListContract.View view;
    private List<ExerciseSetItem> list;
    private String exerciseListId;
    private String exerciseId;

    /**
     * Associated presenter to ExerciseSetListAdapter.
     *
     * @param view              a ref to correspondent adapter
     * @param exerciseListId    exercise list id of WorkoutPlanExerciseSets child in firebase database
     * @param exerciseId        exercise id
     * @param setsListReference a reference to the sets list stored in WorkoutExerciseListPresenter in ExerciseSetDetails.exerciseSetItems ( myExercises.get(int).getExerciseSetItems variable ). Needs a cast to
     */
    public ExerciseSetListPresenter(final ExerciseSetListContract.View view, String exerciseListId, final String exerciseId, List<ExerciseSetItem> /*Object*/ setsListReference) {
        this.view = view;
        // this.list = /*(List<ExerciseSetItem>)*/ setsListReference;
        this.exerciseListId = exerciseListId;
        this.exerciseId = exerciseId;

        Log.d("TAG", "UFFAExerciseSetListPresenter:2 " + list);
        if (exerciseListId != null && exerciseId != null) {
            FirebaseDbSingleton.getInstance().getReference("WorkoutPlanExerciseSets").child(exerciseListId).keepSynced(true);
            Query mExerciseSetListQuery = FirebaseDbSingleton.getInstance().getReference()
                    .child("WorkoutPlanExerciseSets")
                    .orderByKey()
                    .equalTo(exerciseListId);

            mExerciseSetListQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ExerciseSetDetails myChild;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        for (DataSnapshot item : child.getChildren()) {
                            myChild = item.getValue(ExerciseSetDetails.class);
                            if (myChild != null) {
                                if (myChild.getExerciseId().equals(exerciseId)) {
                                    list = myChild.getExerciseSetItems();
                                }
                            }
                        }
                    }
                    view.callNotifyDatasetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    /**
     * Definition how holder bind should go on
     *
     * @param holder   ViewHolder to manipulate
     * @param position position in the list
     */
    @Override
    public void onBindItemViewAtPosition(ExerciseSetListContract.View.Item holder, int position) {
        holder.setReps(String.valueOf(list.get(position).getReps()));
        holder.setLoad(String.valueOf(list.get(position).getLoad()));
        holder.setSets(String.valueOf(position + 1));
    }

    @Override
    public int getSeriesCount() {
        int size = 0;
        if (list != null) {
            size = list.size();
        }

        return size;
    }


    @Override
    public void onSerieAdded(int reps, float loads) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(new ExerciseSetItem(reps, loads));
        view.callNotifyDatasetChanged();
    }

    @Override
    public void setSeriesList(List<ExerciseSetItem> seriesList) {
        list.clear();
        list.addAll(seriesList);
        view.callNotifyDatasetChanged();
    }

    @Override
    public void removeItemAt(int position) {
        list.remove(position);
        view.callNotifyItemRemoved(position);
        view.callNotifyItemRangeChanged(position, list.size());
        view.callNotifyDatasetChanged();
    }

    @Override
    public void onUpdateRepsRequested(int newRep, int position) {
        list.get(position).setReps(newRep);
    }

    @Override
    public void onUpdateLoadsRequested(float newLoad, int position) {
        list.get(position).setLoad(newLoad);
    }

    @Override
    public ArrayList<Integer> getReps() {
        ArrayList<Integer> reps = new ArrayList<>();
        for (ExerciseSetItem exerciseSetItem : list) {
            reps.add(exerciseSetItem.getReps());
        }
        return reps;
    }

    @Override
    public ArrayList<Float> getLoads() {
        ArrayList<Float> loads = new ArrayList<>();
        for (ExerciseSetItem serie : list) {
            loads.add(serie.getLoad());
        }
        return loads;
    }

    @Override
    public void onSaveRequested(String exerciseListId, int pos) {
        FirebaseDbSingleton.getInstance().getReference("WorkoutPlanExerciseSets").child(exerciseListId).child(pos + "").child("exerciseSetItems").setValue(list);
    }

}
