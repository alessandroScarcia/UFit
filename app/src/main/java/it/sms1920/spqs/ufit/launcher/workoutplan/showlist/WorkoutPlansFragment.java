package it.sms1920.spqs.ufit.launcher.workoutplan.showlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import it.sms1920.spqs.ufit.launcher.LauncherActivity;
import it.sms1920.spqs.ufit.launcher.workoutplan.adapter.workoutslist.WorkoutPlansListAdapter;
import it.sms1920.spqs.ufit.launcher.workoutplan.create.CreatingWorkoutActivity;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.workoutplan.showsingle.ShowWorkoutPlanFragment;

public class WorkoutPlansFragment extends Fragment implements WorkoutPlansContract.View {
    private static final String TAG = WorkoutPlansFragment.class.getCanonicalName();
    private WorkoutPlansContract.Presenter presenter;

    private WorkoutPlansListAdapter adapter;

    private TabLayout tlWorkoutPlans;
    private FloatingActionButton fabAdd;
    LauncherActivity launcher;
    private RecyclerView rvWorkoutPlans;
    private TextView tvNoFound;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WorkoutPlansPresenter(this);
        adapter = new WorkoutPlansListAdapter(this);
        launcher = (LauncherActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans, container, false);

        fabAdd = view.findViewById(R.id.btnAddPlan);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddClicked();
            }
        });
        tvNoFound = view.findViewById(R.id.tvNoFound);

        // initialize view references
        tlWorkoutPlans = view.findViewById(R.id.tlWorkoutPlans);
        rvWorkoutPlans = view.findViewById(R.id.rvWorkoutPlans);

        // add listener to change workout plans list visualized
        tlWorkoutPlans.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, String.valueOf(tab.getPosition()));
                presenter.onTabSelectedAtPosition(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // setup recyclerview for workout plans list
        rvWorkoutPlans.setAdapter(adapter);
        rvWorkoutPlans.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    @Override
    public void addNewPlan() {
        Intent intent = new Intent(getContext(), CreatingWorkoutActivity.class);
        intent.putExtra("tabPosition", tlWorkoutPlans.getSelectedTabPosition());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            launcher.setToolbarTitle(data.getStringExtra("newExerciseData"));
        }
        launcher.insertPlansFragment();
    }

    @Override
    public void insertShowWorkoutPlanFragment(String workoutPlanId) {
        ShowWorkoutPlanFragment showWorkoutPlanFragment = ShowWorkoutPlanFragment.newInstance(workoutPlanId);

        this.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, showWorkoutPlanFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void showPersonalWorkoutPlans() {
        adapter.showPersonalWorkoutPlans();
        fabAdd.show();
    }

    @Override
    public void showTrainerWorkoutPlans(boolean role) {
        adapter.showTrainerWorkoutPlans();

        if (!role) {
            fabAdd.hide();
        }
    }

    public void noItemFound(boolean isEmpty) {
        if (isEmpty) {
            rvWorkoutPlans.setVisibility(View.GONE);
            tvNoFound.setVisibility(View.VISIBLE);
        } else {
            rvWorkoutPlans.setVisibility(View.VISIBLE);

            tvNoFound.setVisibility(View.GONE);
        }
    }
}
