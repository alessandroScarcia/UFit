package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

    public ExerciseSetListPresenter(final ExerciseSetListContract.View view, String exerciseListId, final String exerciseId) {
        this.view = view;
        this.list = new ArrayList<>();

        if (exerciseListId != null && exerciseId != null) {
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
                            Log.d("TAG", "onDataChanging: " + myChild);
                            if (myChild.getExerciseId().equals(exerciseId)) {
                                list = myChild.getExerciseSetItems();
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


    @Override
    public void onBindItemViewAtPosition(ExerciseSetListContract.View.Item holder, int position) {
        holder.setReps(String.valueOf(list.get(position).getReps()));
        holder.setLoad(String.valueOf(list.get(position).getLoad()));
    }

    @Override
    public int getSeriesCount() {
        return list.size();
    }


    @Override
    public void onSerieAdded(int reps, float loads) {
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
        for (ExerciseSetItem serie : list) {
            reps.add(serie.getReps());
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
}
