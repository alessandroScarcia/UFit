package it.sms1920.spqs.ufit.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iExerciseSeriesRepsListAdapter;
import it.sms1920.spqs.ufit.presenter.ExerciseSeriesRepsListAdapterPresenter;

class ExerciseSeriesRepsListAdapter extends RecyclerView.Adapter<ExerciseSeriesRepsListAdapter.RowHolder> implements iExerciseSeriesRepsListAdapter.View {

    iExerciseSeriesRepsListAdapter.Presenter presenter;

    public ExerciseSeriesRepsListAdapter() {
        presenter = new ExerciseSeriesRepsListAdapterPresenter(this);
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ExerciseSeriesRepsListAdapter.RowHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_exercise_details, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        presenter.onBindItemViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getSeriesCount();
    }


    void addSerieToList(int reps, float loads) {
        presenter.onSerieAdded(reps, loads);
    }

    void setSeriesList(ArrayList<Integer> reps, ArrayList<Float> loads){
        presenter.setSeriesList(reps, loads);
    }

    @Override
    public void callNotifyDatasetChanged() {
        notifyDataSetChanged();
    }


    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class RowHolder extends RecyclerView.ViewHolder implements iExerciseSeriesRepsListAdapter.View.Item {

        TextView serie;
        TextView reps;
        TextView load;


        public RowHolder(@NonNull View itemView) {
            super(itemView);
            serie = itemView.findViewById(R.id.series);
            reps = itemView.findViewById(R.id.reps);
            load = itemView.findViewById(R.id.loads);
            // TODO itemView.setOnClickListener(myClickListener);

        }

        @Override
        public void setSerie(String serie) {
            this.serie.setText(serie);
        }

        @Override
        public void setReps(String reps) {
            this.reps.setText(reps);
        }

        @Override
        public void setLoad(String load) {
            this.load.setText(load);
        }
    }
}