package it.sms1920.spqs.ufit.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.contract.iSearchListAdapter;
import it.sms1920.spqs.ufit.model.Exercise;

public class SearchListAdapterPresenter implements iSearchListAdapter.Presenter {
    private static final String TAG = SearchListAdapterPresenter.class.getCanonicalName();

//    Link to associated view
    private iSearchListAdapter.View view;
    private DatabaseReference mDatabase;

    private List<Exercise> lstExercise = new ArrayList<>();
    private List<Exercise> lstExerciseFull = new ArrayList<>();

    public SearchListAdapterPresenter(iSearchListAdapter.View view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadExerciseList();
    }

    public void loadExerciseList() {
        Query myExerciseQuery = mDatabase.child("Exercise").orderByKey();

        myExerciseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Exercise exercise = data.getValue(Exercise.class);
                    lstExerciseFull.add(exercise);
                    lstExercise.add(exercise);
                    view.callNotifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void changeQueryText(String query) {
        lstExercise.clear();

        for (Exercise exercise : lstExerciseFull) {
            if (exercise.getName().contains(query)) {
                lstExercise.add(exercise);
            }
        }

        lstExercise.sort(new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Log.d(TAG, "changeQueryText: " + lstExercise);
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onBindExerciseItemViewAtPosition(iSearchListAdapter.View.Item holder, int position) {
        Log.d(TAG, "onBindExerciseItemViewAtPosition: " + lstExercise.get(position).getName());
        holder.setName(lstExercise.get(position).getName());
        // TODO setImage
        holder.setPosition(position);
    }

    @Override
    public int getExerciseCount() {
        return lstExercise.size();
    }

//    @Override
//    public void onClickedExerciseHolder(int position) {
//        view.showExercise(lstExercise.get(position).getExerciseId(), lstExercise.get(position).getName());
//    }


}