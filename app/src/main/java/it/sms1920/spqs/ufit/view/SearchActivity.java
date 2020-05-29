package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import it.sms1920.spqs.ufit.R;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.presenter.ExerciseAdapter;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button btnBack = findViewById(R.id.btnBackFromSearch);
        btnBack.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
            }
        });

        List<Exercise> lstExercise = new ArrayList<>();
        lstExercise.add( new Exercise("Pippo", R.drawable.esercizio));
        lstExercise.add( new Exercise("Pluto", R.drawable.esercizio));
        lstExercise.add( new Exercise("Mauro", R.drawable.esercizio));

        ExerciseAdapter adapter = new ExerciseAdapter(this, lstExercise);

        RecyclerView rvSearchResult = findViewById(R.id.rvSearchResult);
        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this ));

    }
}
