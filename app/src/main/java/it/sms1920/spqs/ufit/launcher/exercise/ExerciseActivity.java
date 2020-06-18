package it.sms1920.spqs.ufit.launcher.exercise;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.sms1920.spqs.ufit.model.util.StringUtils;
import it.sms1920.spqs.ufit.launcher.R;


public class ExerciseActivity extends AppCompatActivity implements ExerciseContract.View {
    private static final String TAG = ExerciseActivity.class.getCanonicalName();
    private ExerciseContract.Presenter presenter;

    private Toolbar toolbar;
    private ImageView ivExerciseImage;
    private TextView tvExerciseDescription;
    private TextView tvMuscleList;

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


        presenter = new ExercisePresenter(this);

        ivExerciseImage = findViewById(R.id.ivExerciseImage);
        tvExerciseDescription = findViewById(R.id.tvExerciseDescription);
        tvMuscleList = findViewById(R.id.tvMuscleList);

        presenter.onCreateCompleted(getIntent());
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void setName(String name) {
        this.toolbar.setTitle(StringUtils.capitalize(name));
    }

    @Override
    public void setImage(String imageUrl) {
        Log.d(TAG, "imageUrl: " + imageUrl);
        Picasso.get().load(imageUrl).into(ivExerciseImage);
    }

    @Override
    public void setTvExerciseDescription(String tvExerciseDescription) {
        this.tvExerciseDescription.setText(StringUtils.capitalize(tvExerciseDescription));
    }

    @Override
    public void setMuscleList(List<String> muscleList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String muscle : muscleList) {
            stringBuilder.append("\t\u25CF ")
                    .append(StringUtils.capitalize(muscle))
                    .append("\n");
        }
        tvMuscleList.setText(stringBuilder.toString());
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }
}
