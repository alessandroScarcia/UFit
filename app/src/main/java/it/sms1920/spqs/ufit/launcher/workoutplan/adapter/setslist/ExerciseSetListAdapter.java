package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;


public class ExerciseSetListAdapter
        extends RecyclerView.Adapter<ExerciseSetListAdapter.RowHolder>
        implements ExerciseSetListContract.View {

    private ExerciseSetListContract.Presenter presenter;
    private boolean editable;

    public ExerciseSetListAdapter(boolean editable, String exerciseListId, String exerciseId, List<ExerciseSetItem>/* Object*/ setsListReference) {
        presenter = new ExerciseSetListPresenter(this, exerciseListId, exerciseId, setsListReference);
        this.editable = editable;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutID = R.layout.partial_exercise_details;

        if (editable) {
            layoutID = R.layout.partial_exercise_details_editable;
        }

        return new ExerciseSetListAdapter.RowHolder(LayoutInflater.from(parent.getContext())
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

    public void setSeriesList(List<ExerciseSetItem> list) {
        presenter.setSeriesList(list);
    }

    public void onSaveRequested(String exerciseListId, int pos) {
        presenter.onSaveRequested(exerciseListId, pos);
    }


    /**
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class RowHolder
            extends RecyclerView.ViewHolder
            implements ExerciseSetListContract.View.Item {

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

            txtReps = itemView.findViewById(R.id.txtReps);
            txtLoads = itemView.findViewById(R.id.txtLoads);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemRemoved(getAdapterPosition());
                }
            });
            if (editable) {
                txtReps.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!txtReps.getText().toString().equals("")) {
                            presenter.onUpdateRepsRequested(Integer.parseInt(txtReps.getText().toString()), getAdapterPosition());
                            Log.d("TAG", "onTextChanged: " + txtReps.getText().toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                txtLoads.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!Objects.requireNonNull(txtLoads.getText()).toString().equals("")) {
                            presenter.onUpdateLoadsRequested(Float.parseFloat(Objects.requireNonNull(txtLoads.getText()).toString()), getAdapterPosition());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }

        }

        @Override
        public void setReps(String reps) {
            this.txtReps.setText(reps);
        }

        @Override
        public void setLoad(String load) {
            this.txtLoads.setText(load);
        }

        @Override
        public void setSets(String sets) {
            serie.setText(sets);
        }


    }
}