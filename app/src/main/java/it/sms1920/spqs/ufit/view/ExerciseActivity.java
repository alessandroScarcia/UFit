package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import it.sms1920.spqs.ufit.contract.ShowExerciseContract;
import it.sms1920.spqs.ufit.model.Exercise;
import it.sms1920.spqs.ufit.presenter.ShowExercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class ExerciseActivity extends AppCompatActivity implements ShowExerciseContract.View {

    private ShowExerciseContract.Presenter presenter;

    private TextView name;
    private TextView description;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Setting toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        presenter = new ShowExercise(this);

        name = findViewById(R.id.txtExerciseName);
        description = findViewById(R.id.txtExerciseDescription);
        Button btn = findViewById(R.id.btnBack);

        btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                finish();
                overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
            }
        });

        presenter.onCreateComplete(getIntent());
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void setName(String name) {
        this.toolbar.setTitle(name);
        this.name.setText(name);
    }

    @Override
    public void setDescription() {
        description.setText(this.getString(R.string.loading));
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void setImage() {
        // TODO implement image setter
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }
}
