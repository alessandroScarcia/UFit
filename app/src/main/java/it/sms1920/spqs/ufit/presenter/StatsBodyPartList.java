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

import it.sms1920.spqs.ufit.contract.iStats;
import it.sms1920.spqs.ufit.contract.iStats;
import it.sms1920.spqs.ufit.contract.iWorkoutPlans;
import it.sms1920.spqs.ufit.model.WorkoutPlan;

public class StatsBodyPartList implements iStats.Presenter {
    private static final String TAG = StatsBodyPartList.class.getCanonicalName();
    private final iStats.View view;

    private List<WorkoutPlan> Stats = new ArrayList<>();
    private List<WorkoutPlan> personalStats = new ArrayList<>();
    private List<WorkoutPlan> trainerStats = new ArrayList<>();

    public StatsBodyPartList(iStats.View view) {
        this.view = view;

        loadStats();
    }

    @Override
    public void onClickAddMeasure() {

    }

    @Override
    public void onGeneralStatsRequired() {

    }

    @Override
    public void onBodyPartsStatsRequired() {

    }

    @Override
    public void onClickedItem(int position) {
        //TODO decide what to do when item is clicked
    }

    @Override
    public void onBindWorkoutPlanItemListViewAtPosition(iStats.View.Item holder, int position) {

    }

    private void loadStats() {
        Log.d(TAG, "loadStats");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // TODO add reference to local user
        Query mPersonalStatsQuery = mDatabase.child("WorkoutPlan").orderByChild("userOwnerId").equalTo("0");

        mPersonalStatsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPersonalStats->onDataChange:" + child.getKey());

                    WorkoutPlan temp = child.getValue(WorkoutPlan.class);
                    if (temp != null) {
                        Log.d(TAG, temp.toString());
                        if (temp.getTrainerId() == null) {
                            personalStats.add(temp);
                        } else {
                            trainerStats.add(temp);
                        }
                    }
                }

                Stats = personalStats;
                view.callNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO add action when listener is cancelled
            }
        });
    }

}
