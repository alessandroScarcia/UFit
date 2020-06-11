package it.sms1920.spqs.ufit.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iSearchForWorkout;
import it.sms1920.spqs.ufit.presenter.SearchExerciseForWorkoutPresenter;

public class SearchExerciseForWorkoutActivity extends AppCompatActivity implements iSearchForWorkout.View {

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

        adapter = new SearchListAdapter(R.layout.item_exercise_in_creation_workout);
        adapter.setMyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", String.valueOf(rvSearchResult.getChildLayoutPosition(view)));
            }
        });

        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void back() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }
}