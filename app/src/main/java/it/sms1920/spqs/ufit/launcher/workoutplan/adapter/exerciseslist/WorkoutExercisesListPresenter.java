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

    WorkoutExercisesListContract.View view;

    List<ExerciseSetDetails> myExercises_copy;
    List<ExerciseSetDetails> myExercises;
    SearchExercise mSearch;

    private DatabaseReference mDatabase;
    private String exerciseListId;


    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();
        view.callNotifyDataSetChanged();
    }

    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view, String workoutId) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();

        Query mExerciseListIdQuery = mDatabase
                .child("WorkoutPlan")
                .orderByKey()
                .equalTo(workoutId);

        mExerciseListIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                WorkoutPlan workoutPlan = null;

                // Fetching exercises from query result, adding them to a List<>
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    workoutPlan = child.getValue(WorkoutPlan.class);
                }
                exerciseListId = workoutPlan.getExerciseListId();

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

    // TODO QUALCOSA QUA
    @Override
    public void onSaveCurrentWorkoutPlanRequested(String name) {

        if (exerciseListId == null) { // if is a new workout

            String workoutKey = mDatabase.child("WorkoutPlan").push().getKey();
            String workoutSetsKey = mDatabase.child("WorkoutPlanExerciseSets").push().getKey();

            WorkoutPlan workoutPlan = new WorkoutPlan(
                    workoutKey,
                    name,
                    FirebaseAuthSingleton.getFirebaseAuth().getUid(),
                    workoutSetsKey,
                    null);

            mDatabase.child("WorkoutPlan").child(Objects.requireNonNull(workoutKey)).setValue(workoutPlan);
            mDatabase.child("WorkoutPlanExerciseSets").child(Objects.requireNonNull(workoutSetsKey)).setValue(myExercises);

            Log.d(TAG, "onSaveCurrentWorkoutPlanRequested: " + myExercises);

        } else { // if is an edit

            DatabaseReference workoutRef = mDatabase.child("WorkoutPlanExerciseSets").child(exerciseListId);
            Query mQuery = workoutRef.orderByKey().equalTo(exerciseListId);
            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mDatabase.child("WorkoutPlanExerciseSets").child(exerciseListId).setValue(myExercises);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBindExerciseItemViewAtPosition(WorkoutExercisesListContract.View.Item holder, int position) {
        ExerciseSetDetails exercise = myExercises.get(position);
        holder.setName(exercise.getExerciseName());
        holder.setId(exercise.getExerciseId());
        holder.setExerciseSetsAdapter(position, exerciseListId, myExercises.get(position).getExerciseId());
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


}
