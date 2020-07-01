package it.sms1920.spqs.ufit.launcher.workoutplan.showsingle;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.sms1920.spqs.ufit.launcher.LauncherActivity;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist.WorkoutExercisesListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowWorkoutPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowWorkoutPlanFragment extends Fragment implements ShowWorkoutPlanContract.View {
    private final static String TAG = ShowWorkoutPlanFragment.class.getCanonicalName();
    private static final String ARG_WORKOUT_PLAN_ID = "workoutPlanId";

    private ShowWorkoutPlanContract.Presenter presenter;
    private WorkoutExercisesListAdapter adapter;
    private String workoutPlanId;

    LauncherActivity launcher;

    public ShowWorkoutPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param workoutPlanId ID of the workout plan to be shown in the fragment
     * @return A new instance of fragment ShowWorkoutPlanFragment.
     */
    public static ShowWorkoutPlanFragment newInstance(String workoutPlanId) {
        ShowWorkoutPlanFragment fragment = new ShowWorkoutPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORKOUT_PLAN_ID, workoutPlanId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launcher = (LauncherActivity) getActivity();
        if (launcher != null) {
            launcher.showPlanClicked();
        }
        if (getArguments() != null) {
            workoutPlanId = getArguments().getString(ARG_WORKOUT_PLAN_ID);
        }

        presenter = new ShowWorkoutPlanPresenter(this, workoutPlanId);
        adapter = new WorkoutExercisesListAdapter(R.layout.item_exercise_horizontal_detailed, false, getActivity(), workoutPlanId, false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_workout_plan, container, false);

        // initialize view references
        TextView id = view.findViewById(R.id.workoutId);
        id.setText(workoutPlanId);


        RecyclerView rvExerciseSetsList = view.findViewById(R.id.rvExerciseSetsList);
        rvExerciseSetsList.setAdapter(adapter);
        rvExerciseSetsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void setToolbarTextEqualToName(String name) {
        if (launcher != null) {
            launcher.setToolbarTitle(name);
        }
    }

    @Override
    public void showToolbarNavigationButton() {
        launcher.toggleToolbarNavigationButton(true);
        launcher.setToolbarNavigationButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.insertPlansFragment();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        launcher.toggleToolbarNavigationButton(true);
        launcher.showToolbarEditIcon(true);
        adapter.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        launcher.showToolbarEditIcon(false);
        launcher.showToolbarTimerIcon(false);
    }

    @Override
    public void hideToolbarNavigationButton() {
        launcher.toggleToolbarNavigationButton(false);
    }

    @Override
    public void setToolBarMenuEditIcon() {
        launcher.showToolbarEditIcon(true);
        launcher.showToolbarTimerIcon(true);
        launcher.showToolbarMaximalTestIcon(true);
    }


}