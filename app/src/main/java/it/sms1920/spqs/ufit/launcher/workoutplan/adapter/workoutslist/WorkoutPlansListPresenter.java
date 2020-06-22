package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.workoutslist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.WorkoutPlan;

public class WorkoutPlansListPresenter implements WorkoutPlansListContract.Presenter {
    private static final String TAG = WorkoutPlansListPresenter.class.getCanonicalName();
    private final WorkoutPlansListContract.View view;

    private List<WorkoutPlan> workoutPlans = new ArrayList<>();
    private List<WorkoutPlan> personalWorkoutPlans = new ArrayList<>();
    private List<WorkoutPlan> trainerWorkoutPlans = new ArrayList<>();

    public WorkoutPlansListPresenter(WorkoutPlansListContract.View view) {
        this.view = view;

        loadWorkoutPlans();
    }

    @Override
    public void onItemClicked(int position) {
        view.showWorkoutPlanDetail(workoutPlans.get(position).getWorkoutPlanId());
    }

    @Override
    public void onBindWorkoutPlanItemListViewAtPosition(WorkoutPlansListContract.View.Item holder, int position) {
        // TODO add data creation or data last workout execution bind
        WorkoutPlan itemData = workoutPlans.get(position);
        Log.d(TAG, itemData.getName());
        holder.setPosition(position);
        holder.setName(itemData.getName());
    }

    @Override
    public int getWorkoutPlansCount() {
        return workoutPlans.size();
    }

    @Override
    public void onPersonalWorkoutPlansRequired() {
        workoutPlans = personalWorkoutPlans;
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onTrainerWorkoutPlansRequired() {
        workoutPlans = trainerWorkoutPlans;
        view.callNotifyDataSetChanged();
    }

    private void loadWorkoutPlans() {

        Query mPersonalWorkoutPlansQuery = FirebaseDbSingleton.getInstance().getReference()
                .child("WorkoutPlan")
                .orderByChild("userOwnerId")
                .equalTo(FirebaseAuthSingleton.getFirebaseAuth().getUid());

        mPersonalWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    WorkoutPlan temp = child.getValue(WorkoutPlan.class);
                    if (temp != null) {
                        if (temp.getTrainerId() == null) {
                            personalWorkoutPlans.add(temp);
                        } else {
                            trainerWorkoutPlans.add(temp);
                        }
                    }
                }

                workoutPlans = personalWorkoutPlans;
                view.callNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO add action when listener is cancelled
            }
        });
    }

}
