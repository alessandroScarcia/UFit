package it.sms1920.spqs.ufit.presenter;

import android.content.Intent;

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

import it.sms1920.spqs.ufit.contract.ShowExerciseContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.model.ExerciseTranslation;

public class ShowExercise implements ShowExerciseContract.Presenter {

    private ShowExerciseContract.View view;

    private DatabaseReference mDatabase;
    private Exercise exercise;
    private ExerciseTranslation exerciseDefaultTranslation;
    private ExerciseTranslation exerciseLocalTranslation;

    public ShowExercise(ShowExerciseContract.View view) {
        this.view = view;
    }


    @Override
    public void onCreateComplete(Intent intent) {
        this.exercise = (Exercise) intent.getSerializableExtra("Exercise");
        view.setName(exercise.getName());

        fetchExerciseTranslation();
    }

    @Override
    public void onBackPressed() {
        view.closeActivity();
    }

    private void fetchExerciseTranslation() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query myExerciseTranslationQuery = mDatabase.child("ExerciseTranslation")
                .orderByChild("exerciseId").equalTo(exercise.getExerciseId());

        myExerciseTranslationQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ExerciseTranslation temp = dataSnapshot.getValue(ExerciseTranslation.class);

                if (temp != null) {
                    if (temp.getCodLanguage().compareTo(exercise.getDefLanguage()) == 0) {
                        exerciseDefaultTranslation = temp;
                    } else if (temp.getCodLanguage().compareTo(Locale.getDefault().getISO3Language()) == 0) {
                        exerciseLocalTranslation = temp;
                    }
                }

                if (exerciseDefaultTranslation != null && exerciseLocalTranslation == null) {
                    view.setDescription(exerciseDefaultTranslation.getDescription());
                } else if (exerciseLocalTranslation != null) {
                    view.setDescription(exerciseLocalTranslation.getDescription());
                } else {
                    view.setDescription();
                }
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
}
