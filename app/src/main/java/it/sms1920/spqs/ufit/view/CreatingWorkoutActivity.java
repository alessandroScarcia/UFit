package it.sms1920.spqs.ufit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iCreatingWorkout;
import it.sms1920.spqs.ufit.presenter.CreatingWorkoutPresenter;

public class CreatingWorkoutActivity extends AppCompatActivity implements iCreatingWorkout.View {


    private CreatingWorkoutPresenter presenter;
    private SearchListAdapter adapter;
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
        startActivityForResult( new Intent(CreatingWorkoutActivity.this, SearchExerciseForWorkoutActivity.class), requestCode);
        overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }
}