package it.sms1920.spqs.ufit.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sms1920.spqs.ufit.contract.iShowWorkoutPlan;
import it.sms1920.spqs.ufit.presenter.ShowWorkoutPlan;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowWorkoutPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowWorkoutPlanFragment extends Fragment implements iShowWorkoutPlan.View {
    private final static String TAG = ShowWorkoutPlanFragment.class.getCanonicalName();
    private static final String ARG_WORKOUT_PLAN_ID = "workoutPlanId";

    private iShowWorkoutPlan.Presenter presenter;
    private ExerciseSetsAdapter adapter;
    private String workoutPlanId;

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
        if (getArguments() != null) {
            workoutPlanId = getArguments().getString(ARG_WORKOUT_PLAN_ID);
        }

        presenter = new ShowWorkoutPlan(this);
        adapter = new ExerciseSetsAdapter(workoutPlanId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_workout_plan, container, false);

        // initialize view references
        RecyclerView rvExerciseSetsList = view.findViewById(R.id.rvExerciseSetsList);
        rvExerciseSetsList.setAdapter(adapter);
        rvExerciseSetsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}