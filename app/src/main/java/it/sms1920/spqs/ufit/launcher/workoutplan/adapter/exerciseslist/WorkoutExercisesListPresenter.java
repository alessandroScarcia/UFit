package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private String workoutPlanId;


    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();
        view.callNotifyDataSetChanged();
    }

    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view, String workoutPlanId) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);
        this.workoutPlanId = workoutPlanId;

        mDatabase = FirebaseDbSingleton.getInstance().getReference();
        loadExerciseSetList();
    }


    private void loadExerciseSetList() {

        final Query mPersonalWorkoutPlansQuery = mDatabase
                .child("WorkoutPlan")
                .orderByChild("userOwnerId")
                .equalTo(FirebaseAuthSingleton.getFirebaseAuth().getUid());



        mPersonalWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String exerciseListId = Objects.requireNonNull(child.getValue(WorkoutPlan.class)).getExerciseListId();

                    Query mExerciseSetListQuery = mDatabase
                            .child("WorkoutPlanExerciseSets")
                            .orderByKey()
                            .equalTo(exerciseListId);

                    mExerciseSetListQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                for (DataSnapshot exerciseItem : child.getChildren()) {
                                    myExercises.add(exerciseItem.getValue(ExerciseSetDetails.class));
                                }
                            }
                            loadExercisesNames();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void loadExercisesNames() {
        ArrayList<String> idList = new ArrayList<>();
        for (ExerciseSetDetails item : myExercises) {
            idList.add(item.getExerciseId());
        }
        mSearch.getExerciseByIdList(idList);
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
    public void onSaveCurrentWorkoutPlanRequested(String name) {

        if (workoutPlanId == null) {
            Log.d(this.getClass().getCanonicalName(), "onSaveDataRequested: save " + workoutPlanId);
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


        } else {
            Log.d(this.getClass().getCanonicalName(), "onSaveDataRequested: save " + workoutPlanId);
            DatabaseReference workoutRef = mDatabase.child("WorkoutPlanExerciseSets").child(workoutPlanId);
            Query mQuery = workoutRef.orderByKey().equalTo(workoutPlanId);
            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mDatabase.child("WorkoutPlanExerciseSets").child(workoutPlanId).setValue(myExercises);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //DatabaseReference ref = mDatabase.child("WorkoutPlanExerciseSets");
        //Query mQuery = ref.orderByKey().equalTo(workoutPlanId);
        //Log.d(this.getClass().getCanonicalName(), "onSaveDataRequested: save " + mQuery);
//        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getChildrenCount() > 0) { // If already exists
//                    mDatabase.child("WorkoutPlanExerciseSets").child(workoutPlanId).setValue(myExercises);
//                } else { // if not exists yet
//                    mDatabase.push().setValue(myExercises);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onBindExerciseItemViewAtPosition(WorkoutExercisesListContract.View.Item holder, int position) {
        ExerciseSetDetails exercise = myExercises.get(position);
        holder.setName(exercise.getExerciseName());
        holder.setId(exercise.getExerciseId());
        holder.setExerciseSetsAdapter(position, myExercises.get(position).getExerciseSetItems());
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
