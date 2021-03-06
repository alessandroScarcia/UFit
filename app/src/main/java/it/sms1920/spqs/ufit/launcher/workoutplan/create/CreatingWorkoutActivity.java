package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist.WorkoutExercisesListAdapter;

import static it.sms1920.spqs.ufit.launcher.workoutplan.create.CreatingWorkoutContract.Presenter.PICK_EXERCISE;
import static it.sms1920.spqs.ufit.launcher.workoutplan.create.CreatingWorkoutContract.Presenter.RESULT_SUCCESSFUL;

public class CreatingWorkoutActivity extends AppCompatActivity implements CreatingWorkoutContract.View {
    private final String TAG = this.getClass().getCanonicalName();

    private CreatingWorkoutPresenter presenter;
    private WorkoutExercisesListAdapter adapter;
    private Toolbar toolbar;
    private TextInputEditText txtName;
    private RecyclerView rvExerciseList;
    private boolean isAnEdit = false;
    private boolean isForAthlete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_workout);

        presenter = new CreatingWorkoutPresenter(this);

        if ( getIntent().getIntExtra("tabPosition", 0) == 1){
            isForAthlete = true;
        }

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

        txtName = findViewById(R.id.txtWorkoutName);


        String workoutId = getIntent().getStringExtra("workoutId");
        String workoutName = getIntent().getStringExtra("workoutName");

        if (workoutId != null && !workoutId.isEmpty()) {
            isAnEdit = true;
            adapter = new WorkoutExercisesListAdapter(R.layout.item_exercise_horizontal_detailed, true, this, workoutId, isForAthlete, true);
            txtName.setText(workoutName);
        } else {
            // Setting recycler view adapter for not editable exercises
            isAnEdit = false;
            adapter = new WorkoutExercisesListAdapter(R.layout.item_exercise_horizontal_detailed, true, this, isForAthlete, true);
        }


        // Setting recycler view
        rvExerciseList = findViewById(R.id.lstExercises);
        rvExerciseList.setAdapter(adapter);
        rvExerciseList.setLayoutManager(new LinearLayoutManager(this));



        // Button to confirm the workout creation
        FloatingActionButton btnDone = findViewById(R.id.btnApply);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveDataRequested();
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
        if (item.getItemId() == R.id.add) {
            presenter.onAddIconClicked();
        }
        return super.onOptionsItemSelected(item);
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
        Intent intent = new Intent(CreatingWorkoutActivity.this, SelectExercisesActivity.class);
        intent.putExtra("exercisesId", adapter.getExercisesIdList());
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void communicateNewExercisesToAdapter(ArrayList<String> exerciseId) {
        adapter.addNewExercises(exerciseId);
    }

    @Override
    public boolean checkIfSavable() {
        boolean savable = true;

        if (Objects.requireNonNull(txtName.getText()).toString().equals("") || Objects.requireNonNull(rvExerciseList.getAdapter()).getItemCount() == 0) {
            savable = false;
        }


        return savable;
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.notSavable), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveData() {
        String workoutName = "";

        if (txtName.getText() != null) {
            workoutName = txtName.getText().toString();

            if (isAnEdit) {
                adapter.saveChangesWorkoutPlan(workoutName);
                Log.d(TAG, "saveData: is an edit");
            } else {
                adapter.createNewWorkoutPlan(workoutName);
                Log.d(TAG, "saveData: is a new");
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent();
        intent.putExtra("newExerciseName", workoutName);
        setResult(0, intent);
        finish();
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

        if (requestCode == PICK_EXERCISE && data != null) {
            if (resultCode == RESULT_SUCCESSFUL) {
                presenter.onAddExercisesSuccessfulDone(data.getStringArrayListExtra("exercisesId"));
            }
        }//else { DO nothing }
    }

}