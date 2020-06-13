package it.sms1920.spqs.ufit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.lang.reflect.Array;
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
    private RecyclerView rvExerciseList;

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

        adapter = new WorkoutExerciseListAdapter(R.layout.item_exercise_vertical_detailed, this);

        adapter.setMyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View lytSeries = view.findViewById(R.id.lytSeries);
                if (lytSeries.getVisibility() == GONE)
                    lytSeries.setVisibility(View.VISIBLE);
                else
                    lytSeries.setVisibility(GONE);
            }
        });

        rvExerciseList = findViewById(R.id.lstExercises);
        rvExerciseList.setAdapter(adapter);
        rvExerciseList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar.
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

    @Override
    public void startSearchExerciseForWorkout(int requestCode) {
        startActivityForResult(new Intent(CreatingWorkoutActivity.this, SearchExerciseForWorkoutActivity.class), requestCode);
        overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void communicateNewExerciseToAdapter(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads) {
        adapter.addNewExercise(exerciseId, reps, loads);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == 0) { //CODESUCCESSFUL) { // se il codice di richiesta è uguale a quello usato
            ArrayList<Integer> reps = new ArrayList<>();//(ArrayList<Integer>) data.getSerializableExtra("exerciseReps");
            ArrayList<Float> loads = new ArrayList<>();//(ArrayList<Float>) data.getSerializableExtra("exerciseLoads");

            reps.add(3);
            reps.add(2);

            loads.add(10f);
            loads.add(10f);

            presenter.onAddExerciseSuccessfulDone(
                    data.getStringExtra("exerciseId"),
                    reps,
                    loads
            );
        } else {
            // non succede niente
        }
    }
}