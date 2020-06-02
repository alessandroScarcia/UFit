package it.sms1920.spqs.ufit.model;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.sms1920.spqs.ufit.presenter.SearchAdapter;

public class Search {

    private final SearchAdapter presenter;

    public Search(SearchAdapter presenter){
        this.presenter = presenter;
    }

    public List<Exercise> askForResult(String key) {

        final List<Exercise> lst = new ArrayList<>();


        // SELECT * FROM Esercizi WHERE Nome = key
        Query mDB = FirebaseDatabase.getInstance().getReference("Esercizi")
                .orderByChild("nome")
                .equalTo(key);


        mDB.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                presenter.publishData(dataSnapshot.getValue(Exercise.class));
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

        return lst;
    }


}
