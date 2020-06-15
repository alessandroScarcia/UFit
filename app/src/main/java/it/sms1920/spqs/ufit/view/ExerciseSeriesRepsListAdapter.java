package it.sms1920.spqs.ufit.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iExerciseSeriesRepsListAdapter;
import it.sms1920.spqs.ufit.presenter.ExerciseSeriesRepsListAdapterPresenter;

class ExerciseSeriesRepsListAdapter extends RecyclerView.Adapter<ExerciseSeriesRepsListAdapter.RowHolder> implements iExerciseSeriesRepsListAdapter.View {

    iExerciseSeriesRepsListAdapter.Presenter presenter;
    boolean editable;

    public ExerciseSeriesRepsListAdapter(boolean editable) {
        presenter = new ExerciseSeriesRepsListAdapterPresenter(this);
        this.editable = editable;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ExerciseSeriesRepsListAdapter.RowHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        (editable ? R.layout.partial_exercise_details_editable : R.layout.partial_exercise_details),
                        parent,
                        false));

    }

    ArrayList<Integer> getReps() {
        return presenter.getReps();
    }

    ArrayList<Float> getLoads() {
        return presenter.getLoads();
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

    void setSeriesList(ArrayList<Integer> reps, ArrayList<Float> loads) {
        presenter.setSeriesList(reps, loads);
    }

    @Override
    public void callNotifyItemRemoved(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public void callNotifyItemRangeChanged(int position, int range) {
        notifyItemRangeChanged(position, range);
    }

    @Override
    public void callNotifyDatasetChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(int position) {
        presenter.removeItemAt(position);
    }

    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class RowHolder extends RecyclerView.ViewHolder implements iExerciseSeriesRepsListAdapter.View.Item {

        TextView serie;
        TextView reps;
        TextView load;
        MaterialButton btnRemove;

        TextInputEditText txtReps;
        TextInputEditText txtLoads;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            serie = itemView.findViewById(R.id.series);
            if (editable) {
                txtReps = itemView.findViewById(R.id.txtReps);
                txtLoads = itemView.findViewById(R.id.txtLoads);
                btnRemove = itemView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemRemoved(getAdapterPosition());
                    }
                });
            } else {
                reps = itemView.findViewById(R.id.reps);
                load = itemView.findViewById(R.id.loads);
            }

            // TODO itemView.setOnClickListener(myClickListener);

        }

        @Override
        public void setSerie(String serie) {
            this.serie.setText(serie);
        }

        @Override
        public void setReps(String reps) {
            if (editable)
                this.txtReps.setText(reps);
            else
                this.reps.setText(reps);
        }

        @Override
        public void setLoad(String load) {
            if (editable)
                this.txtLoads.setText(load);
            else
                this.load.setText(load);
        }
    }
}