package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.search.SearchListAdapter;

public class SearchExerciseForWorkoutActivity extends AppCompatActivity implements iSearchForWorkout.View, SearchListAdapter.Manager/*, EditExerciseDialog.DialogListener*/ {

    public static final int CODE_SUCCESSFUL = 0;
    public static final int CODE_NOT_SUCCESSFUL = 1;

    private Toolbar toolbar;
    private RecyclerView rvSearchResult;
    private iSearchForWorkout.Presenter presenter;
    private SearchListAdapter adapter;
    private EditExerciseDialog.DialogListener dialogListener;
    private FloatingActionButton btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exercise_for_workout);


        presenter = new SearchExerciseForWorkoutPresenter(this);
        //dialogListener = this;

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

        final TextInputEditText txtSearchField = findViewById(R.id.txtSearchField);
        final TextInputLayout txtSearchFieldLayout = findViewById(R.id.txtSearchFieldLayout);

        txtSearchFieldLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        txtSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onQueryTextChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        rvSearchResult = findViewById(R.id.rvSearchResult);

        adapter = new SearchListAdapter(R.layout.item_exercise_horizontal, true, this);
        adapter.setMyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView lblId = view.findViewById(R.id.txtExerciseId);

                ArrayList<String> exercisesId = new ArrayList<>();
                exercisesId.add(lblId.getText().toString());

                presenter.onExerciseSelectionEnded(exercisesId);
            }
        });

        adapter.setItemsSelection((ArrayList<String>) getIntent().getSerializableExtra("exercisesId"));

        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onExerciseSelectionEnded(adapter.getSelectedItems());
            }
        });

        if (!adapter.getSelectedItems().isEmpty()){
            btnDone.show();
        }

    }


    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void back() {
        Intent intent = new Intent();
        setResult(CODE_NOT_SUCCESSFUL, intent);
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }

    @Override
    public void notifyQueryTextChangedToAdapter(String keyword) {
        adapter.onQueryTextChanged(keyword);
    }

    @Override
    public void sendResultBack(ArrayList<String> exercisesId) {
        Intent intent = new Intent();
        intent.putExtra("exercisesId", exercisesId);
        setResult(CODE_SUCCESSFUL, intent);
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }


    @Override
    public void notifySelectionMode(boolean activated) {
        if (activated) {
            btnDone.show();
        } else {
            btnDone.hide();
        }
    }
}