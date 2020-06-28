package it.sms1920.spqs.ufit.launcher.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.exercise.ExerciseActivity;
import it.sms1920.spqs.ufit.launcher.R;


public class SearchActivity extends AppCompatActivity implements SearchContract.View, SearchListAdapter.Manager {
    private static final String TAG = SearchActivity.class.getCanonicalName();

    private SearchPresenter presenter;
    private SearchListAdapter adapter;
    private RecyclerView rvSearchResult;
    private TextView tvNoFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        presenter = new SearchPresenter(this);

        // Setting toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        // OnClickListener for items of RecyclerView
        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClicked(rvSearchResult.getChildLayoutPosition(v));
            }
        };
        adapter = new SearchListAdapter(R.layout.item_exercise_horizontal, itemClickListener, this);

        tvNoFound = findViewById(R.id.tvNoFound);

        rvSearchResult = findViewById(R.id.rvSearchResult);
        TextInputEditText txtSearchField = findViewById(R.id.txtSearchField);
        TextInputLayout txtSearchFieldLayout = findViewById(R.id.txtSearchFieldLayout);

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

        // Setting adapter to the recycler view for search result
        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void back() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }

    @Override
    public void notifyQueryTextChangedToAdapter(final String query) {
        adapter.onQueryTextChanged(query);
    }

    @Override
    public void showExercise(int position) {
        // Extract single view item from recycler view with used LayoutManager method "findViewByPosition"
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvSearchResult.getLayoutManager();

        if (layoutManager != null) {
            View view = layoutManager.findViewByPosition(position);

            if (view != null) {
                TextView txtId = view.findViewById(R.id.txtExerciseId);

                if (!txtId.getText().toString().equals("")) {
                    startExerciseActivity(txtId.getText().toString());
                }
            }
        }
    }

    public void startExerciseActivity(String exerciseId) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("exerciseId", exerciseId);
        startActivity(intent);
        this.overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void notifySelectionMode(boolean activated) {

    }

    @Override
    public void notifyNoItemFound(boolean isEmpty) {
        if (isEmpty) {
            rvSearchResult.setVisibility(View.GONE);
            tvNoFound.setVisibility(View.VISIBLE);
        } else {
            rvSearchResult.setVisibility(View.VISIBLE);
            tvNoFound.setVisibility(View.GONE);
        }
    }
}
