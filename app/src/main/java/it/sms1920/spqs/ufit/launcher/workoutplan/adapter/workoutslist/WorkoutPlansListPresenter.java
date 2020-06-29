package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.workoutslist;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;
import it.sms1920.spqs.ufit.model.firebase.database.WorkoutPlan;


/**
 *
 */
public class WorkoutPlansListPresenter implements WorkoutPlansListContract.Presenter {
    private static final String TAG = WorkoutPlansListPresenter.class.getCanonicalName();
    private final WorkoutPlansListContract.View view;

    private List<WorkoutPlan> workoutPlans = new ArrayList<>();
    private List<WorkoutPlan> personalWorkoutPlans = new ArrayList<>();
    private List<WorkoutPlan> trainerWorkoutPlans = new ArrayList<>();

    private boolean requiredTrainerTab = false;
    private User me;

    public WorkoutPlansListPresenter(WorkoutPlansListContract.View view) {
        this.view = view;

        if (FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser().isAnonymous()) {
            loadWorkoutPlans(true);
        } else {
            FirebaseDbSingleton.getInstance().getReference().child("User").orderByKey().equalTo(FirebaseAuthSingleton.getFirebaseAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        me = item.getValue(User.class);

                        loadWorkoutPlans(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        view.callNotifyDataSetChanged(workoutPlans.isEmpty());
    }

    @Override
    public void onItemClicked(int position) {
        view.showWorkoutPlanDetail(workoutPlans.get(position).getWorkoutPlanId());
    }

    @Override
    public void onBindWorkoutPlanItemListViewAtPosition(WorkoutPlansListContract.View.Item holder, int position) {
        WorkoutPlan itemData = workoutPlans.get(position);
        holder.setPosition(position);
        holder.setName(itemData.getName());
    }

    @Override
    public boolean isTrainer() {
        return me.getRole();
    }

    @Override
    public int getWorkoutPlansCount() {
        return workoutPlans.size();
    }

    @Override
    public boolean isTrainerTabRequested() {
        return requiredTrainerTab;
    }

    @Override
    public void removeItemAt(int position) {
        if (requiredTrainerTab) {
            removeWorkout(trainerWorkoutPlans.get(position).getWorkoutPlanId(), trainerWorkoutPlans.get(position).getExerciseListId());
            trainerWorkoutPlans.remove(position);
        } else {
            removeWorkout(personalWorkoutPlans.get(position).getWorkoutPlanId(), personalWorkoutPlans.get(position).getExerciseListId());
            personalWorkoutPlans.remove(position);
        }

        view.callNotifyDataSetChanged(workoutPlans.isEmpty());
    }

    private void removeWorkout(String id, String exerciseListId) {
        FirebaseDbSingleton.getInstance().getReference().child("WorkoutPlan").child(id).setValue(null);
        FirebaseDbSingleton.getInstance().getReference().child("WorkoutPlanExerciseSets").child(exerciseListId).setValue(null);
    }

    @Override
    public void onPersonalWorkoutPlansRequired() {
        workoutPlans = personalWorkoutPlans;
        requiredTrainerTab = false;
        view.callNotifyDataSetChanged(workoutPlans.isEmpty());
    }

    @Override
    public void onTrainerWorkoutPlansRequired() {
        workoutPlans = trainerWorkoutPlans;
        requiredTrainerTab = true;
        view.callNotifyDataSetChanged(workoutPlans.isEmpty());
    }

    private void loadWorkoutPlans(boolean isAnonymous) {
        FirebaseDbSingleton.getInstance().getReference().child("WorkoutPlan").keepSynced(true);
        Query mPersonalWorkoutPlansQuery = null;
        Query mTrainerWorkoutPlansQuery = null;

        mPersonalWorkoutPlansQuery = FirebaseDbSingleton.getInstance().getReference()
                .child("WorkoutPlan")
                .orderByChild("userOwnerId")
                .equalTo(FirebaseAuthSingleton.getFirebaseAuth().getUid());
        if (!isAnonymous) {
            mTrainerWorkoutPlansQuery = FirebaseDbSingleton.getInstance().getReference()
                    .child("WorkoutPlan")
                    .orderByChild("userOwnerId")
                    .equalTo(me.getLinkedUserId());
        }


        if (isAnonymous) {
            // Loading my workouts
            mPersonalWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        WorkoutPlan temp = child.getValue(WorkoutPlan.class);
                        if (temp != null) {
                            personalWorkoutPlans.add(temp);
                            Log.d(TAG, "onDataChange: " + temp);
                        }
                    }
                    workoutPlans = personalWorkoutPlans;
                    view.callNotifyDataSetChanged(personalWorkoutPlans.isEmpty());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else if (me.getRole()) {
            mPersonalWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        WorkoutPlan temp = child.getValue(WorkoutPlan.class);
                        if (temp != null) {
                            Log.d(TAG, "onDataChange: " + temp);
                            if (temp.getForMyAthlete()) {
                                trainerWorkoutPlans.add(temp);
                            } else {
                                personalWorkoutPlans.add(temp);
                            }
                        }
                    }

                    workoutPlans = personalWorkoutPlans;
                    view.callNotifyDataSetChanged(workoutPlans.isEmpty());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {

            // Loading my workouts
            mPersonalWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        WorkoutPlan temp = child.getValue(WorkoutPlan.class);
                        if (temp != null) {

                            personalWorkoutPlans.add(temp);
                            Log.d(TAG, "onDataChange: " + temp);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


            // Loading trainer's workout
            mTrainerWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        WorkoutPlan temp = child.getValue(WorkoutPlan.class);

                        if (temp != null && temp.getForMyAthlete()) {
                            trainerWorkoutPlans.add(temp);
                        }
                    }
                    workoutPlans = personalWorkoutPlans;
                    view.callNotifyDataSetChanged(workoutPlans.isEmpty());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    }

}
