package it.sms1920.spqs.ufit.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iExerciseSeriesRepsListAdapter;
import it.sms1920.spqs.ufit.model.ExerciseSetItem;
import it.sms1920.spqs.ufit.presenter.ExerciseSeriesRepsListAdapterPresenter;


class ExerciseSeriesRepsListAdapter
        extends RecyclerView.Adapter<ExerciseSeriesRepsListAdapter.RowHolder>
        implements iExerciseSeriesRepsListAdapter.View {

    private iExerciseSeriesRepsListAdapter.Presenter presenter;
    private boolean editable;

    public ExerciseSeriesRepsListAdapter(boolean editable) {
        presenter = new ExerciseSeriesRepsListAdapterPresenter(this);
        this.editable = editable;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutID = R.layout.partial_exercise_details;

        if (editable) {
            layoutID = R.layout.partial_exercise_details_editable;
        }

        return new ExerciseSeriesRepsListAdapter.RowHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutID, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        presenter.onBindItemViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getSeriesCount();
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

    public ArrayList<Integer> getReps() {
        return presenter.getReps();
    }

    public ArrayList<Float> getLoads() {
        return presenter.getLoads();
    }

    public void addSerieToList(int reps, float loads) {
        presenter.onSerieAdded(reps, loads);
    }

    public void setSeriesList(ArrayList<ExerciseSetItem> list) {
        presenter.setSeriesList(list);
    }

    /**
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class RowHolder
            extends RecyclerView.ViewHolder
            implements iExerciseSeriesRepsListAdapter.View.Item {

        private TextView reps;

        // Variables for not editable row
        private TextView serie;
        private TextView load;

        // Variables for editable row
        private MaterialButton btnRemove;
        private TextInputEditText txtReps;
        private TextInputEditText txtLoads;

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
            serie.setText(String.valueOf(getItemCount()));
        }

        @Override
        public void setReps(String reps) {
            if (editable) {
                this.txtReps.setText(reps);
            } else {
                this.reps.setText(reps);
            }
        }

        @Override
        public void setLoad(String load) {
            if (editable) {
                this.txtLoads.setText(load);
            } else {
                this.load.setText(load);
            }
        }
    }
}