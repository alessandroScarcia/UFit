package it.sms1920.spqs.ufit.presenter;

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

import it.sms1920.spqs.ufit.contract.iWorkoutPlans;
import it.sms1920.spqs.ufit.model.WorkoutPlan;

public class WorkoutPlansList implements iWorkoutPlans.Presenter {
    private static final String TAG = WorkoutPlansList.class.getCanonicalName();
    private final iWorkoutPlans.View view;

    private List<WorkoutPlan> workoutPlans = new ArrayList<>();
    private List<WorkoutPlan> personalWorkoutPlans = new ArrayList<>();
    private List<WorkoutPlan> trainerWorkoutPlans = new ArrayList<>();

    public WorkoutPlansList(iWorkoutPlans.View view) {
        this.view = view;

        loadWorkoutPlans();
    }

    @Override
    public void onClickedItem(int position) {
        view.showWorkoutPlanDetail(workoutPlans.get(position).getWorkoutPlanId());
    }

    @Override
    public void onBindWorkoutPlanItemListViewAtPosition(iWorkoutPlans.View.Item holder, int position) {
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
        Log.d(TAG, workoutPlans.toString());
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onTrainerWorkoutPlansRequired() {
        workoutPlans = trainerWorkoutPlans;
        Log.d(TAG, workoutPlans.toString());
        view.callNotifyDataSetChanged();
    }

    private void loadWorkoutPlans() {
        Log.d(TAG, "loadWorkoutPlans");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // TODO add reference to local user
        Query mPersonalWorkoutPlansQuery = mDatabase.child("WorkoutPlan").orderByChild("userOwnerId").equalTo("0");

        mPersonalWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPersonalWorkoutPlans->onDataChange:" + child.getKey());

                    WorkoutPlan temp = child.getValue(WorkoutPlan.class);
                    if (temp != null) {
                        Log.d(TAG, temp.toString());
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
