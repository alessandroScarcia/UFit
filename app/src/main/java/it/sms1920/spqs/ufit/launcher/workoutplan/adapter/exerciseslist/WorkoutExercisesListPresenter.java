package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist.ExerciseSetListAdapter;
import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetDetails;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.WorkoutPlan;
import it.sms1920.spqs.ufit.model.search.ExerciseDetailed;
import it.sms1920.spqs.ufit.model.search.SearchExercise;
import it.sms1920.spqs.ufit.model.search.SearchClient;

public class WorkoutExercisesListPresenter implements WorkoutExercisesListContract.Presenter, SearchClient {

    private WorkoutExercisesListContract.View view;

    private List<ExerciseSetDetails> myExercises;
    private SearchExercise mSearch;

    private DatabaseReference mDatabase;
    private String exerciseListId = null;
    private WorkoutPlan workoutPlan = null;
    private ArrayList<ExerciseSetListAdapter> adapters = new ArrayList<>();

    private boolean isForAthlete;
    private boolean isCreation;

    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view, boolean isForAthlete, boolean isCreation) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();

        this.isForAthlete = isForAthlete;
        this.isCreation = isCreation;
        view.callNotifyDataSetChanged();

    }

    public WorkoutExercisesListPresenter(WorkoutExercisesListContract.View view, String workoutId, boolean isForAthlete, boolean isCreation) {
        this.view = view;

        myExercises = new ArrayList<>();
        mSearch = new SearchExercise(this);

        mDatabase = FirebaseDbSingleton.getInstance().getReference();
        this.isForAthlete = isForAthlete;
        this.isCreation = isCreation;
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

        List<ExerciseSetDetails> myExercises_copy = new ArrayList<>(myExercises);
        myExercises.clear();


        for (ExerciseDetailed exercise : mSearch.getExerciseResultList()) {
            boolean exists = false;

            for (int i = 0; i < myExercises_copy.size(); i++) {
                if (exercise.getExerciseId().equals(myExercises_copy.get(i).getExerciseId())) {
                    myExercises.add(new ExerciseSetDetails(exercise.getExerciseId(), exercise.getName(), myExercises_copy.get(i).getExerciseSetItems(), exercise.getImageUrl()));
                    exists = true;
                    i = myExercises_copy.size(); // Stopping iteration
                }
            }
            if (!exists) {
                myExercises.add(new ExerciseSetDetails(exercise.getExerciseId(), exercise.getName(), new ArrayList<ExerciseSetItem>(), exercise.getImageUrl()));
            }
        }
        adapters.clear();
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
            for (int i = 0; i < myExercises.size(); i++) {
                mDatabase.child("WorkoutPlanExerciseSets").child(workoutSetsKey).child(String.valueOf(i)).child("exerciseId").setValue(myExercises.get(i).getExerciseId());
                adapters.get(i).onSaveRequested(workoutSetsKey, i);
            }


            //mDatabase.child("WorkoutPlanExerciseSets").child(workoutSetsKey).setValue(myExercises);
        }
    }

    @Override
    public void onSaveWorkoutPlanChangesRequested(String name) {
        if (!name.isEmpty()) {
            workoutPlan.setName(name);
        }
        for (int i = 0; i < myExercises.size(); i++) {
            mDatabase.child("WorkoutPlanExerciseSets").child(exerciseListId).child(i + "").child("exerciseId").setValue(myExercises.get(i).getExerciseId());

            adapters.get(i).onSaveRequested(exerciseListId, i);
        }
        mDatabase.child("WorkoutPlan").child(workoutPlan.getWorkoutPlanId()).setValue(workoutPlan);

    }

    @Override
    public void onBindExerciseItemViewAtPosition(WorkoutExercisesListContract.View.Item holder, int position) {
        ExerciseSetDetails exercise = myExercises.get(position);
        holder.setName(exercise.getExerciseName());
        holder.setImage(exercise.getImageUrl());
        holder.setId(exercise.getExerciseId());

        if (myExercises.get(position).getExerciseSetItems().size() == 0 && !isCreation){
            holder.setNoItemView();
        }

        ExerciseSetListAdapter adapter = holder.setExerciseSetsAdapter(position, exerciseListId, myExercises.get(position).getExerciseId(), myExercises.get(position).getExerciseSetItems());

        adapters.add(adapter);


    }


    @Override
    public int getExerciseCount() {
        return myExercises.size();
    }

    @Override
    public void removeItemAt(int position) {
        myExercises.remove(position);
        adapters.clear();
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
        adapters.clear();
        view.callNotifyDataSetChanged();
    }


}
