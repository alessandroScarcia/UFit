package it.sms1920.spqs.ufit.launcher.workoutplan.showsingle;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseSetsAdapter extends RecyclerView.Adapter<ExerciseSetsAdapter.ExerciseSetDetailsHolder> implements iShowWorkoutPlanAdapter.View {
    private iShowWorkoutPlanAdapter.Presenter presenter;

    public ExerciseSetsAdapter(String workoutPlanId) {
        presenter = new ExerciseSetsList(this, workoutPlanId);
    }

    @NonNull
    @Override
    public ExerciseSetDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseSetDetailsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ExerciseSetDetailsHolder extends RecyclerView.ViewHolder implements iShowWorkoutPlanAdapter.View.Item {
        public ExerciseSetDetailsHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
