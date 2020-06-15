package it.sms1920.spqs.ufit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iSearchForWorkout;
import it.sms1920.spqs.ufit.presenter.SearchExerciseForWorkoutPresenter;

public class SearchExerciseForWorkoutActivity extends AppCompatActivity implements iSearchForWorkout.View, EditExerciseDialog.DialogListener {

    public static final int CODE_SUCCESSFUL = 0;
    public static final int CODE_NOT_SUCCESSFUL = 1;

    private Toolbar toolbar;
    private RecyclerView rvSearchResult;
    private SearchExerciseForWorkoutPresenter presenter;
    private SearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exercise_for_workout);

        presenter = new SearchExerciseForWorkoutPresenter(this);

        // Setting toolbar
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });


        rvSearchResult = findViewById(R.id.rvSearchResult);

        adapter = new SearchListAdapter(R.layout.item_exercise_vertical);
        adapter.setMyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Dialog to pick reps and loads
                TextView id =view.findViewById(R.id.txtExerciseName);
                EditExerciseDialog dialogBox = new EditExerciseDialog(id.getText().toString());
                dialogBox.show(getSupportFragmentManager(), "Dialog");


            }
        });

        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void back() {
        Intent intent = new Intent();
        setResult(CODE_NOT_SUCCESSFUL, intent);
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }

    @Override
    public void saveData(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads) {
        Intent intent = new Intent();
        intent.putExtra("exerciseId", exerciseId);
        intent.putExtra("exerciseReps", reps);
        intent.putExtra("exerciseLoads", loads);
        setResult(CODE_SUCCESSFUL, intent);
        finish();
    }
}