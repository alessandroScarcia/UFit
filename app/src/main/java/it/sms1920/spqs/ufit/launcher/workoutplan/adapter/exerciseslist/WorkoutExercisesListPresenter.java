package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetDetails;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.WorkoutPlan;
import it.sms1920.spqs.ufit.model.search.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.search.SearchExercise;
import it.sms1920.spqs.ufit.model.search.iSearchClient;

public class WorkoutExercisesListPresenter implements WorkoutExercisesListContract.Presenter, iSearchClient {
    private static final String TAG = WorkoutExercisesListPresenter.class.getCanonicalName();

    private WorkoutExercisesListContract.View view;

    private List<ExerciseSetDetails> myExercises_copy;
    private List<ExerciseSetDetails> myExercises;
    private SearchExercise mSearch;

    private DatabaseReference mDatabase;
    private String exerciseListId = null;
    private WorkoutPlan workoutPlan = null;


    private boolean isForAthlete;

    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view, boolean isForAthlete) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();

        this.isForAthlete = isForAthlete;
        view.callNotifyDataSetChanged();

    }

    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view, String workoutId, boolean isForAthlete) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();
        this.isForAthlete = isForAthlete;
        fetchWorkout(workoutId);

    }

    void fetchWorkout(String workoutId) {
        Query mExerciseListIdQuery = mDatabase
                .child("WorkoutPlan")
                .orderByKey()
                .equalTo(workoutId);

        mExerciseListIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Fetching exercises from query result, adding them to a List<>
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    workoutPlan = child.getValue(WorkoutPlan.class);
                }
                if (workoutPlan != null) {
                    exerciseListId = workoutPlan.getExerciseListId();
                }

                loadExerciseList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void loadExerciseList() {
        Query mExerciseSetListQuery = mDatabase
                .child("WorkoutPlanExerciseSets")
                .orderByKey()
                .equalTo(exerciseListId);

        mExerciseSetListQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Fetching exercises from query result, adding them to a List<>
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot item : child.getChildren()) {
                        myExercises.add(item.getValue(ExerciseSetDetails.class));
                        if (myExercises.get(myExercises.size() - 1).getExerciseSetItems() == null) {
                            myExercises.get(myExercises.size() - 1).setExerciseSetItems(new ArrayList<ExerciseSetItem>());
                        }
                    }
                }

                // Fetching exercises name using .getExerciseByIdList() method
                ArrayList<String> idList = new ArrayList<>();
                for (ExerciseSetDetails item : myExercises) {
                    idList.add(item.getExerciseId());
                }
                mSearch.getExerciseByIdList(idList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void notifyResultListReady() {

        myExercises_copy = new ArrayList<>(myExercises);
        myExercises.clear();


        for (ExerciseDetailed exercise : mSearch.getExerciseResultList()) {
            boolean exists = false;

            for (int i = 0; i < myExercises_copy.size(); i++) {
                if (exercise.getExerciseId().equals(myExercises_copy.get(i).getExerciseId())) {
                    myExercises.add(new ExerciseSetDetails(exercise.getExerciseId(), exercise.getName(), myExercises_copy.get(i).getExerciseSetItems()));
                    exists = true;
                    i = myExercises_copy.size(); // Stopping iteration
                }
            }
            if (!exists) {
                myExercises.add(new ExerciseSetDetails(exercise.getExerciseId(), exercise.getName(), new ArrayList<ExerciseSetItem>()));
            }
        }
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onSaveNewWorkoutPlanRequested(String name) {
        String workoutKey = mDatabase.child("WorkoutPlan").push().getKey();
        String workoutSetsKey = mDatabase.child("WorkoutPlanExerciseSets").push().getKey();

        WorkoutPlan workoutPlan = new WorkoutPlan(
                workoutKey,
                name,
                FirebaseAuthSingleton.getFirebaseAuth().getUid(),
                workoutSetsKey,
                null,
                isForAthlete);

        if (workoutKey != null && workoutSetsKey != null) {
            mDatabase.child("WorkoutPlan").child(workoutKey).setValue(workoutPlan);
            mDatabase.child("WorkoutPlanExerciseSets").child(workoutSetsKey).setValue(myExercises);
        }
    }

    @Override
    public void onSaveWorkoutPlanChangesRequested(String name) {
        if (!name.isEmpty()) {
            workoutPlan.setName(name);
        }
        mDatabase.child("WorkoutPlan").child(workoutPlan.getWorkoutPlanId()).setValue(workoutPlan);
        mDatabase.child("WorkoutPlanExerciseSets").child(exerciseListId).setValue(myExercises);
    }

    @Override
    public void onBindExerciseItemViewAtPosition(WorkoutExercisesListContract.View.Item holder, int position) {
        ExerciseSetDetails exercise = myExercises.get(position);
        holder.setName(exercise.getExerciseName());
        holder.setId(exercise.getExerciseId());
        holder.setExerciseSetsAdapter(position, exerciseListId, myExercises.get(position).getExerciseId(), myExercises.get(position).getExerciseSetItems());
    }

    @Override
    public int getExerciseCount() {
        return myExercises.size();
    }

    @Override
    public void removeItemAt(int position) {
        myExercises.remove(position);
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onNewExercisesAdded(ArrayList<String> exercisesId) {
        mSearch.getExerciseByIdList(exercisesId);
    }

    @Override
    public ArrayList<String> onExercisesIdRequested() {
        ArrayList<String> list = new ArrayList<>();

        for (ExerciseSetDetails exercise : myExercises) {
            list.add(exercise.getExerciseId());
        }
        return list;
    }

    @Override
    public List<ExerciseSetItem> onSetsListRequested(int position) {
        return myExercises.get(position).getExerciseSetItems();
    }

    @Override
    public void onUpdateRequested() {
        if (workoutPlan != null) {
            fetchWorkout(workoutPlan.getWorkoutPlanId());
        }
        view.callNotifyDataSetChanged();
    }


}
