package it.sms1920.spqs.ufit.launcher.workoutplan.create;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.model.firebase.database.ExerciseSetItem;

class WorkoutExerciseListAdapter extends RecyclerView.Adapter<WorkoutExerciseListAdapter.ExerciseHolder> implements iWorkoutExerciseListAdapter.View {

    AppCompatActivity activity;
    iWorkoutExerciseListAdapter.Presenter presenter;
    boolean editable;

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

    public WorkoutExerciseListAdapter(int layoutItemID, boolean editable, Activity activity) {
        this.layoutItemID = layoutItemID;
        this.activity = (AppCompatActivity) activity;
        this.editable = editable;
        presenter = new WorkoutExerciseListAdapterPresenter(this);

    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutExerciseListAdapter.ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(layoutItemID, parent, false));
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


    void addNewExercises(ArrayList<String> exercisesId) {
        presenter.onNewExercisesAdded(exercisesId);
    }



    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class ExerciseHolder extends RecyclerView.ViewHolder implements iWorkoutExerciseListAdapter.View.Item, EditExerciseDialog.DialogListener {

        TextView name;
        TextView id;
        ImageView image;
        RecyclerView series;
        ExerciseSeriesRepsListAdapter adapter;
        MaterialButton btnEdit;
        EditExerciseDialog.DialogListener dialogListener;

        public ExerciseHolder(@NonNull final View itemView) {
            super(itemView);





            if (!editable) {
                dialogListener = this;
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView lblId = itemView.findViewById(R.id.txtExerciseId);
                        EditExerciseDialog dialogBox = new EditExerciseDialog(lblId.getText().toString(), dialogListener);
                        dialogBox.show(activity.getSupportFragmentManager(), "Dialog");
                    }
                });
            }

            series = itemView.findViewById(R.id.rvSeries);
            adapter = new ExerciseSeriesRepsListAdapter(editable);
            // adapter.setMyClickListener();
            series.setAdapter(adapter);
            series.setLayoutManager(new LinearLayoutManager(activity));

            name = itemView.findViewById(R.id.txtExerciseName);
            image = itemView.findViewById(R.id.imgExercise);
            id = itemView.findViewById(R.id.txtExerciseId);

            itemView.setOnClickListener(myClickListener);

        }


        @Override
        public void setName(String name) {
            if (name == null || name.length() == 0) {
                name = "";
            } else if (name.length() == 1) {
                name = name.toUpperCase();
            } else {
                name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            }
            this.name.setText(name);
        }

        @Override
        public void setImage(Image image) {
            //TODO image fetch
            this.image.setImageResource(R.drawable.img_exercise);
        }

        @Override
        public void setId(String id) {
            this.id.setText(id);
        }

        @Override
        public void addSerie(int reps, float loads) {
            adapter.addSerieToList(reps, loads);
        }

        @Override
        public void saveData(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads) {
            ArrayList<ExerciseSetItem> serieList = new ArrayList<>();
            for (int i = 0; i < reps.size(); i++)
                serieList.add(new ExerciseSetItem(reps.get(i), loads.get(i)));

            adapter.setSeriesList(serieList);
            setName(exerciseName);

            notifyDataSetChanged();
        }

        @Override
        public ArrayList<ExerciseSetItem> getList() {
            ArrayList<ExerciseSetItem> list = new ArrayList<>();
            TextView reps;
            TextView load;

            for (int i = 0; i < series.getChildCount(); i++) {
                reps = series.getChildAt(i).findViewById(R.id.reps);
                load = series.getChildAt(i).findViewById(R.id.loads);
                list.add(new ExerciseSetItem(
                        Integer.parseInt(reps.getText().toString()),
                        Float.parseFloat(load.getText().toString())));
            }


            return list;
        }
    }

    ArrayList<String> getExercisesIdList(){
        return presenter.onExercisesIdRequested();
    }

}
