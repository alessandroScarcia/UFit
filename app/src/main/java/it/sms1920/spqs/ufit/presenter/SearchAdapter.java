//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.presenter;

import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import it.sms1920.spqs.ufit.contract.SearchContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.view.SearchActivity.myViewHolder;

/**
 * Presenter for the View 'SearchActivity'.
 * Contains business logic for 'Search Exercise' functionality.
 */
public class SearchAdapter extends RecyclerView.Adapter<myViewHolder> implements SearchContract.Presenter {
    private static final String TAG = SearchAdapter.class.getCanonicalName();

    private final SearchContract.View view;

    private DatabaseReference mDatabase;

    private List<Exercise> lstExercise = new ArrayList<>();
    private List<Exercise> lstExerciseFull = new ArrayList<>();

    public SearchAdapter(SearchContract.View view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadExerciseList();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return view.createSearchViewItem(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.bind(lstExercise.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lstExercise.size();
    }

    @Override
    public void onClickExercise(int position) {
        view.showExercise(lstExercise.get(position));
    }

    @Override
    public void onBackPressed() {
        view.back();
    }

    public void loadExerciseList() {
        Query myExerciseQuery = mDatabase.child("Exercise").orderByKey();

        myExerciseQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "exerciseEventListener::onChildAdded:" + dataSnapshot.getKey());

                Exercise exercise = dataSnapshot.getValue(Exercise.class);
                lstExerciseFull.add(exercise);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void search(String keyString) {
        lstExercise.clear();
        notifyDataSetChanged();

        for (Exercise exercise : lstExerciseFull) {
            if (exercise.getName().contains(keyString)) {
                lstExercise.add(exercise);
            }
        }

        lstExercise.sort(new ExerciseComparator());

        notifyDataSetChanged();
    }


    public class ExerciseComparator implements Comparator<Exercise> {

        @Override
        public int compare(Exercise o1, Exercise o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
