package it.sms1920.spqs.ufit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iCreatingWorkout;
import it.sms1920.spqs.ufit.presenter.CreatingWorkoutPresenter;

import static android.view.View.GONE;

public class CreatingWorkoutActivity extends AppCompatActivity implements iCreatingWorkout.View {

    private CreatingWorkoutPresenter presenter;
    private WorkoutExerciseListAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_workout);

        presenter = new CreatingWorkoutPresenter(this);

        // setting toolbar
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(getString(R.string.new_workout_plan));
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        // Setting recycler view adapter for not editable exercises
        adapter = new WorkoutExerciseListAdapter(R.layout.item_exercise_vertical_detailed, false, this);
        adapter.setMyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Configuration of card expansion/compression
                View lytSeries = view.findViewById(R.id.lytSeries);
                if (lytSeries.getVisibility() == GONE)
                    lytSeries.setVisibility(View.VISIBLE);
                else
                    lytSeries.setVisibility(GONE);
            }
        });

        // Setting recycler view
        RecyclerView rvExerciseList = findViewById(R.id.lstExercises);
        rvExerciseList.setAdapter(adapter);
        rvExerciseList.setLayoutManager(new LinearLayoutManager(this));

        // Button to confirm the workout creation
        FloatingActionButton btnDone = findViewById(R.id.btnApply);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO saving data
                Toast.makeText(CreatingWorkoutActivity.this, "Saving data not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the action bar: only Add button will be visible
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        toolbar.getMenu().findItem(R.id.add).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                presenter.onAddIconClicked();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void back() {
        super.onBackPressed();
    }

    /**
     * Starting new activity waiting for result. Handled by overriding "onActivityResult"
     *
     * @param requestCode formal request code to send by Intent to new activity.
     */
    @Override
    public void startSearchExerciseForWorkout(int requestCode) {
        startActivityForResult(new Intent(CreatingWorkoutActivity.this, SearchExerciseForWorkoutActivity.class), requestCode);
        overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void communicateNewExerciseToAdapter(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads) {
        adapter.addNewExercise(exerciseId, exerciseName, reps, loads);
    }

    /**
     * Handling result from activity started by "startSearchExerciseFroWorkout"
     *
     * @param requestCode request code sent to new activity
     * @param resultCode  result code received from new activity
     * @param data        Intent object received: contains all info sent back from new activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("AAAA", "onActivityResult: " + requestCode + " " + resultCode);
        // This check if request code received is equal to request code received, and result code is positive.
        if (requestCode == 1){
            if (resultCode == 0) {
                presenter.onAddExerciseSuccessfulDone(
                        data.getStringExtra("exerciseId"),
                        data.getStringExtra("exerciseName"),
                        (ArrayList<Integer>) data.getSerializableExtra("exerciseReps"),
                        (ArrayList<Float>) data.getSerializableExtra("exerciseLoads")
                );
            } else {
                // DO NOTHING
            }
        }  // Here there could be an else statement for handler the negative result
    }

}