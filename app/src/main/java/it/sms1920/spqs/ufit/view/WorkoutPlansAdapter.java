package it.sms1920.spqs.ufit.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.contract.iWorkoutPlans;
import it.sms1920.spqs.ufit.presenter.WorkoutPlansList;

public class WorkoutPlansAdapter extends RecyclerView.Adapter<WorkoutPlansAdapter.WorkoutPlanHolder> implements iWorkoutPlans.View {
    private static final String TAG = WorkoutPlansAdapter.class.getCanonicalName();

    private iWorkoutPlans.Presenter presenter;

    private WorkoutPlansFragment parentFragment;

    public WorkoutPlansAdapter(WorkoutPlansFragment parentFragment) {
        presenter = new WorkoutPlansList(this);
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
    public void showWorkoutPlanDetail(int workoutPlanId) {
        parentFragment.insertShowWorkoutPlanFragment(workoutPlanId);
    }

    public class WorkoutPlanHolder extends RecyclerView.ViewHolder implements iWorkoutPlans.View.Item {
        private TextView tvName;
        private int position;

        public WorkoutPlanHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvWorkoutPlanName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickedItem(position);
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
