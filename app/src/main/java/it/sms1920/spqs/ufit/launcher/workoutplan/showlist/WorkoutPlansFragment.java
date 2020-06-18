package it.sms1920.spqs.ufit.launcher.workoutplan.showlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import it.sms1920.spqs.ufit.launcher.workoutplan.create.CreatingWorkoutActivity;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.workoutplan.showsingle.ShowWorkoutPlanFragment;

public class WorkoutPlansFragment extends Fragment implements iWorkoutPlansFragment.View {
    private static final String TAG = WorkoutPlansFragment.class.getCanonicalName();
    private iWorkoutPlansFragment.Presenter presenter;

    private WorkoutPlansAdapter adapter;

    private TabLayout tlWorkoutPlans;
    private FloatingActionButton fabAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WorkoutPlansPresenter(this);
        adapter = new WorkoutPlansAdapter(this);
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


        // initialize view references
        TabLayout tlWorkoutPlans = view.findViewById(R.id.tlWorkoutPlans);
        RecyclerView rvWorkoutPlans = view.findViewById(R.id.rvWorkoutPlans);

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
    public void showTrainerWorkoutPlans() {
        adapter.showTrainerWorkoutPlans();

        fabAdd.hide();
    }

    @Override
    public void addNewPlan() {
        startActivity(new Intent( getContext(), CreatingWorkoutActivity.class));
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
}
