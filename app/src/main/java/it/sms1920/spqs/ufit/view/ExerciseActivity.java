package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;
import it.sms1920.spqs.ufit.contract.ShowExerciseContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.presenter.ShowExercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ExerciseActivity extends AppCompatActivity implements ShowExerciseContract.view {

    ShowExerciseContract.presenter presenter;

    TextView name;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Button btn = findViewById(R.id.btnBack);
        btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
            }
        });

        presenter = new ShowExercise(this);

        name = findViewById(R.id.txtExerciseName);
        description = findViewById(R.id.txtExerciseDescription);

        presenter.onCreateComplete( getIntent() );
    }

    @Override
    public void load(Exercise exercise) {
        name.setText(exercise.getName());
        description.setText(exercise.getDescription());
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void back() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }
}
