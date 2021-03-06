package it.sms1920.spqs.ufit.launcher.workoutplan.adapter.exerciseslist;

import android.app.Activity;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.workoutplan.adapter.setslist.ExerciseSetListAdapter;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;
import it.sms1920.spqs.ufit.model.util.StringUtils;

import static android.view.View.GONE;

public class WorkoutExercisesListAdapter extends RecyclerView.Adapter<WorkoutExercisesListAdapter.ExerciseHolder> implements WorkoutExercisesListContract.View {

    private AppCompatActivity activity;
    private WorkoutExercisesListContract.Presenter presenter;
    private boolean editable;
    /*
            Resource ID indicating layout to use in binding. Required at least something like below:
            Required: TextView with "txtExerciseName" as ID
                      TextView with "txtExerciseId" as ID
                      ImageView with "imgExercise" as ID
        */
    private int layoutItemID;

    /*
        Listener to apply for each item "setOnClickListener" in list
     */
    private View.OnClickListener myClickListener;


    public void setMyClickListener(View.OnClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public WorkoutExercisesListAdapter(int layoutItemID, boolean editable, Activity activity, boolean isForAthlete, boolean isCreation) {
        this.layoutItemID = layoutItemID;
        this.activity = (AppCompatActivity) activity;
        this.editable = editable;
        presenter = new WorkoutExercisesListPresenter(this, isForAthlete, isCreation);


    }

    public WorkoutExercisesListAdapter(int layoutItemID, boolean editable, Activity activity, String workoutPlanId, boolean isForAthlete, boolean isCreation) {
        this.layoutItemID = layoutItemID;
        this.activity = (AppCompatActivity) activity;
        this.editable = editable;
        presenter = new WorkoutExercisesListPresenter(this, workoutPlanId, isForAthlete, isCreation);
    }


    public void createNewWorkoutPlan(String name) {
        presenter.onSaveNewWorkoutPlanRequested(name);
    }

    public void saveChangesWorkoutPlan(String name) {
        presenter.onSaveWorkoutPlanChangesRequested(name);
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutExercisesListAdapter.ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(layoutItemID, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        presenter.onBindExerciseItemViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getExerciseCount();
    }

    @Override
    public void callNotifyDataSetChanged() {

        notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(int position) {
        presenter.removeItemAt(position);
    }


    public void addNewExercises(ArrayList<String> exercisesId) {
        presenter.onNewExercisesAdded(exercisesId);
    }


    public ArrayList<String> getExercisesIdList() {
        return presenter.onExercisesIdRequested();
    }


    public void update() {
        presenter.onUpdateRequested();
    }


    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class ExerciseHolder extends RecyclerView.ViewHolder implements WorkoutExercisesListContract.View.Item/*, EditExerciseDialog.DialogListener*/ {

        TextView name;
        TextView id;
        ImageView image;
        RecyclerView series;
        MaterialButton btnRemove;
        MaterialButton btnAdd;
        MaterialButton btnToggleExpansion;

        TextView sets;
        TextView reps;
        TextView load;
        TextView noItem;

        ExerciseSetListAdapter adapter;

        public ExerciseHolder(@NonNull final View itemView) {
            super(itemView);

            sets = itemView.findViewById(R.id.tvSerie);
            reps = itemView.findViewById(R.id.tvReps);
            load = itemView.findViewById(R.id.tvLoads);
            noItem = itemView.findViewById(R.id.tvNoFound);


            btnToggleExpansion = itemView.findViewById(R.id.btnToggleExpand);
            btnToggleExpansion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MaterialButton btnToggle = view.findViewById(R.id.btnToggleExpand);

                    View lytSeries = itemView.findViewById(R.id.lytSeries);
                    if (lytSeries.getVisibility() == GONE) {
                        lytSeries.setVisibility(View.VISIBLE);
                        btnToggle.setIcon(activity.getDrawable(R.drawable.ic_arrow_up_black));
                    } else {
                        lytSeries.setVisibility(GONE);
                        btnToggle.setIcon(activity.getDrawable(R.drawable.ic_arrow_down_black));
                    }
                }


            });
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemRemoved(getAdapterPosition());
                }
            });

            series = itemView.findViewById(R.id.rvSeries);

            btnAdd = itemView.findViewById(R.id.addSerie);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addExerciseSet(0, 0);
                }
            });
            if (!editable) {
                btnAdd.setVisibility(GONE);
                btnRemove.setVisibility(GONE);
            }

            name = itemView.findViewById(R.id.txtExerciseName);
            image = itemView.findViewById(R.id.imgExercise);
            id = itemView.findViewById(R.id.txtExerciseId);

            itemView.setOnClickListener(myClickListener);


        }

        @Override
        public ExerciseSetListAdapter setExerciseSetsAdapter(int position, String workoutPlanId, String exerciseId, List<ExerciseSetItem> /*Object*/ setsListReference) {
            adapter = new ExerciseSetListAdapter(editable, workoutPlanId, exerciseId, setsListReference);
            series.setAdapter(adapter);
            series.setLayoutManager(new LinearLayoutManager(activity));
            return adapter;
        }

        @Override
        public void setName(String name) {
            if (name == null) name = "";
            this.name.setText(StringUtils.capitalize(name));
        }

        @Override
        public void setImage(String imageURL) {
            Picasso.get().load(imageURL).into(image);
        }

        @Override
        public void setId(String id) {
            this.id.setText(id);
        }

        @Override
        public void setExerciseSets(List<ExerciseSetItem> exerciseSets) {
            adapter.setSeriesList(exerciseSets);
        }

        @Override
        public void addExerciseSet(int reps, float loads) {
            adapter.addSerieToList(reps, loads);
        }

        @Override
        public void setNoItemView() {
            sets.setVisibility(GONE);
            reps.setVisibility(GONE);
            load.setVisibility(GONE);
            noItem.setVisibility(View.VISIBLE);
        }
    }


}
