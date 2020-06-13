package it.sms1920.spqs.ufit.presenter;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.sms1920.spqs.ufit.contract.iSearchListAdapter;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.model.ExerciseTranslation;
import it.sms1920.spqs.ufit.model.SearchExerciseResult;
import it.sms1920.spqs.ufit.view.SearchListAdapter;

public class SearchListAdapterPresenter implements iSearchListAdapter.Presenter {
    private static final String TAG = SearchListAdapterPresenter.class.getCanonicalName();

    private iSearchListAdapter.View view;
    private DatabaseReference mDatabase;

    private Set<SearchExerciseResult> searchExerciseResults = new TreeSet<>();
    private List<SearchExerciseResult> searchExerciseResultList = new ArrayList<>();
    private List<String> exerciseKeys = new ArrayList<>();
    private Map<String, Exercise> mapExerciseFull = new TreeMap<>();

    public SearchListAdapterPresenter(iSearchListAdapter.View view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadExerciseList();
        onQueryTextChanged("");
    }

    public void loadExerciseList() {
        Query myExerciseQuery = mDatabase.child("Exercise").orderByKey();

        myExerciseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "exerciseEventListener::onChildAdded:" + dataSnapshot.getKey());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    Exercise exercise = child.getValue(Exercise.class);

                    mapExerciseFull.put(key, exercise);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onQueryTextChanged(final String keyword) {
        exerciseKeys.clear();
        searchExerciseResults.clear();
        Query mExerciseTranslationQuery = mDatabase.child(ExerciseTranslation.CHILD_NAME);

        mExerciseTranslationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ExerciseTranslation et = child.getValue(ExerciseTranslation.class);

                    if (et != null && et.getName().contains(keyword)) {
                        exerciseKeys.add(String.valueOf(et.getExerciseId()));
                    }
                }

                fetchSearchResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void fetchSearchResult() {
        Query mNameTranslatedQuery = mDatabase.child(ExerciseTranslation.CHILD_NAME);

        mNameTranslatedQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ExerciseTranslation temp = child.getValue(ExerciseTranslation.class);

                    if (temp != null
                            && exerciseKeys.contains(String.valueOf(temp.getExerciseId()))
                            && temp.getCodLanguage().equals(Locale.getDefault().getISO3Language())) {

                        int exerciseId = temp.getExerciseId();
                        String name = temp.getName();
                        String imageUrl = mapExerciseFull.get(String.valueOf(temp.getExerciseId())).getImage();

                        searchExerciseResults.add(new SearchExerciseResult(exerciseId, name, imageUrl));
                    }
                }


                searchExerciseResultList.clear();
                searchExerciseResultList = new ArrayList<>(searchExerciseResults);
                view.callNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBindExerciseItemViewAtPosition(iSearchListAdapter.View.Item holder, int position) {
        holder.setName(searchExerciseResultList.get(position).getName());
        // TODO setImage
        holder.setId(searchExerciseResultList.get(position).getExerciseId());
    }

    @Override
    public int getExerciseCount() {
        return searchExerciseResultList.size();
    }

//    @Override
//    public void onClickedExerciseHolder(int position) {
//        view.showExercise(lstExercise.get(position).getExerciseId(), lstExercise.get(position).getName());
//    }


}