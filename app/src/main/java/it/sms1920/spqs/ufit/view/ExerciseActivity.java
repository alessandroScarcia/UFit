package it.sms1920.spqs.ufit.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import it.sms1920.spqs.ufit.contract.iExercise;
import it.sms1920.spqs.ufit.presenter.ShowExercise;


public class ExerciseActivity extends AppCompatActivity implements iExercise.View {

    private iExercise.Presenter presenter;

    private TextView name;
    private TextView description;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Setting toolbar
        toolbar = findViewById(R.id.tool_bar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });


        presenter = new ShowExercise(this);

        name = findViewById(R.id.txtExerciseName);
        description = findViewById(R.id.txtExerciseDescription);

        presenter.onCreateCompleted(getIntent());
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
