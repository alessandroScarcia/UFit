package it.sms1920.spqs.ufit.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.sms1920.spqs.ufit.contract.iSearchClient;

/**
 * Model class used to search and Exercises inside Firebase Realtime Database with certain
 * characteristics.
 */
public class SearchExercise {
    private static final String TAG = SearchExercise.class.getCanonicalName();

    private iSearchClient client;

    private DatabaseReference rootReference;

    private Map<String, Exercise> exerciseMap = new TreeMap<>();
    private List<ExerciseTranslation> exerciseTranslationList = new ArrayList<>();

    public SearchExercise(iSearchClient client) {
        this.client = client;
        this.rootReference = FirebaseDbSingleton.getDatabase().getReference();
    }

    /**
     * Method used to retrive every exercise that contains 'keyword' inside their name.
     * 'keyword' must be considered as a string within an indefinite language. For this reason,
     * exercise are fetched if one of their translation contains 'keyword'. Results are fetched only
     * for the local language if possible, otherwise they are fetched in the default language specified
     * for each exercise.
     *
     * @param keyword string used to search exercises.
     */
    public void getExerciseWithNameStarting(final String keyword) {
        exerciseMap.clear();
        exerciseTranslationList.clear();

        Query mExerciseWithNameQuery = rootReference.child(ExerciseTranslation.CHILD_NAME);

        mExerciseWithNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> exerciseResultIdSet = new TreeSet<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ExerciseTranslation et = child.getValue(ExerciseTranslation.class);
                    if (et != null && et.getName().contains(keyword)) {
                        exerciseResultIdSet.add(et.getExerciseId());
                    }
                }

                List<String> exerciseResultIdList = new ArrayList<>(exerciseResultIdSet);
                fetchExerciseMapWithId(exerciseResultIdList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    /**
     * This method fetches from Exercise items having id corresponding to an element inside
     * 'exerciseIdList'
     *
     * @param exerciseIdList Reference to Exercise items id to be fetched
     */
    private void fetchExerciseMapWithId(final List<String> exerciseIdList) {
        exerciseMap = new TreeMap<>();

        DatabaseReference mExerciseList = rootReference.child(Exercise.CHILD_NAME);
        mExerciseList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Exercise exercise = child.getValue(Exercise.class);
                    if (exercise != null && exerciseIdList.contains(child.getKey())) {
                        exerciseMap.put(child.getKey(), exercise);
                    }
                }

                fetchExerciseTranslationList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    /**
     * This method fetches ExerciseTranslation data for Exercises fetched by
     * 'fetchExerciseMapWithId()'
     */
    private void fetchExerciseTranslationList() {
        DatabaseReference mExerciseTranslationList = rootReference.child(ExerciseTranslation.CHILD_NAME);
        mExerciseTranslationList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String localeLanguage = Locale.getDefault().getISO3Language();
                Set<ExerciseTranslation> exerciseDefTranslationSet = new TreeSet<>();
                Set<ExerciseTranslation> exerciseLocaleTranslationSet = new TreeSet<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ExerciseTranslation et = child.getValue(ExerciseTranslation.class);
                    if (et != null && exerciseMap.containsKey(et.getExerciseId())) {
                        if (et.getCodLanguage().equals(localeLanguage)) {
                            exerciseLocaleTranslationSet.add(et);
                        } else if (et.getCodLanguage().equals(exerciseMap.get(et.getExerciseId()).getDefLanguage())) {
                            exerciseDefTranslationSet.add(et);
                        }
                    }
                }

                Set<ExerciseTranslation> exerciseTranslationSet = new TreeSet<>(exerciseLocaleTranslationSet);
                exerciseTranslationSet.addAll(exerciseDefTranslationSet);

                exerciseTranslationList = new ArrayList<>(exerciseTranslationSet);

                client.notifyResultListReady();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    /**
     * This method must be called by iSearchClient when they are notified for ResultReady to retrive
     * the list of results.
     *
     * @return list of Exercises fetched from Firebase Realtime Database
     */
    public List<ExerciseDetailed> getExerciseResultList() {
        List<ExerciseDetailed> exerciseDetailedList = new ArrayList<>();
        for (ExerciseTranslation et : exerciseTranslationList) {
            Exercise exercise = exerciseMap.get(et.getExerciseId());
            if (exercise != null) {
                ExerciseDetailed er = new ExerciseDetailed();
                er.setExerciseId(et.getExerciseId());
                er.setName(et.getName());
                er.setDescription(et.getDescription());
                er.setMuscleList(et.getMuscleList());
                er.setImageUrl(exercise.getImageUrl());
                er.setVideoUrl(exercise.getVideoUrl());

                exerciseDetailedList.add(er);
            }
        }

        Log.d(TAG, exerciseDetailedList.toString());
        return exerciseDetailedList;
    }


}
