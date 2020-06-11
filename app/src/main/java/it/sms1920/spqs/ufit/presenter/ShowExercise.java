package it.sms1920.spqs.ufit.presenter;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;

import it.sms1920.spqs.ufit.contract.iExercise;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.model.ExerciseTranslation;

public class ShowExercise implements iExercise.Presenter {
    private static final String TAG = ShowExercise.class.getCanonicalName();
    private iExercise.View view;

    private DatabaseReference mDatabase;
    private Exercise exercise;

    private ExerciseTranslation exerciseDefaultTranslation;
    private ExerciseTranslation exerciseLocalTranslation;

    public ShowExercise(iExercise.View view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void onCreateCompleted(Intent intent) {
        final int exerciseId = intent.getIntExtra("exerciseId", -1);
        final String exerciseName = intent.getStringExtra("exerciseName");

        Query myExerciseQuery = mDatabase.child("Exercise").orderByChild("exerciseId").equalTo(exerciseId);

        myExerciseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    Log.d(TAG, Objects.requireNonNull(child.getKey()));
//
//                    Exercise temp = child.getValue(Exercise.class);
//
//                    if (temp != null && temp.getName().equals(exerciseName)) {
//                        exercise = child.getValue(Exercise.class);
//                        view.setName(exerciseName);
//                        // TODO image e muscoli
//                        fetchExerciseTranslation();
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        view.closeActivity();
    }

    private void fetchExerciseTranslation() {
//        Query myExerciseTranslationQuery = mDatabase.child("ExerciseTranslation")
//                .orderByChild("exerciseId").equalTo(exercise.getExerciseId());
//
//        myExerciseTranslationQuery.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                ExerciseTranslation temp = dataSnapshot.getValue(ExerciseTranslation.class);
//
//                if (temp != null) {
//                    if (temp.getCodLanguage().compareTo(exercise.getDefLanguage()) == 0) {
//                        exerciseDefaultTranslation = temp;
//                    } else if (temp.getCodLanguage().compareTo(Locale.getDefault().getISO3Language()) == 0) {
//                        exerciseLocalTranslation = temp;
//                    }
//                }
//
//                if (exerciseDefaultTranslation != null && exerciseLocalTranslation == null) {
//                    view.setDescription(exerciseDefaultTranslation.getDescription());
//                } else if (exerciseLocalTranslation != null) {
//                    view.setDescription(exerciseLocalTranslation.getDescription());
//                } else {
//                    view.setDescription();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
