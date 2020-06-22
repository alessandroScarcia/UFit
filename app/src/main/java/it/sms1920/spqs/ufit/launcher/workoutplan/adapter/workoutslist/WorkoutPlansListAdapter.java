package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.workoutslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.workoutplan.showlist.WorkoutPlansFragment;

public class WorkoutPlansListAdapter extends RecyclerView.Adapter<WorkoutPlansListAdapter.WorkoutPlanHolder> implements WorkoutPlansListContract.View {
    private static final String TAG = WorkoutPlansListAdapter.class.getCanonicalName();

    private WorkoutPlansListContract.Presenter presenter;

    private WorkoutPlansFragment parentFragment;

    public WorkoutPlansListAdapter(WorkoutPlansFragment parentFragment) {
        presenter = new WorkoutPlansListPresenter(this);
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public WorkoutPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutPlanHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_plan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutPlanHolder holder, int position) {
        presenter.onBindWorkoutPlanItemListViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getWorkoutPlansCount();
    }

    public void showPersonalWorkoutPlans() {
        presenter.onPersonalWorkoutPlansRequired();
    }

    public void showTrainerWorkoutPlans() {
        presenter.onTrainerWorkoutPlansRequired();
    }

    @Override
    public void callNotifyDataSetChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void showWorkoutPlanDetail(String workoutPlanId) {
        parentFragment.insertShowWorkoutPlanFragment(workoutPlanId);
    }

    public class WorkoutPlanHolder extends RecyclerView.ViewHolder implements WorkoutPlansListContract.View.Item {
        private TextView tvName;
        private int position;

        public WorkoutPlanHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvWorkoutPlanName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onItemClicked(position);
                }
            });
        }

        @Override
        public void setName(String name) {
            tvName.setText(name);
        }

        @Override
        public void setPosition(int position) {
            this.position = position;
        }
    }
}
