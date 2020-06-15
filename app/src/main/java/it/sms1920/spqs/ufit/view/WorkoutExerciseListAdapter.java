package it.sms1920.spqs.ufit.view;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iWorkoutExerciseListAdapter;
import it.sms1920.spqs.ufit.presenter.WorkoutExerciseListAdapterPresenter;

class WorkoutExerciseListAdapter extends RecyclerView.Adapter<WorkoutExerciseListAdapter.ExerciseHolder> implements iWorkoutExerciseListAdapter.View {

    Activity activity;
    iWorkoutExerciseListAdapter.Presenter presenter;
    boolean editable = false;

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
        this.activity = activity;
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


    void addNewExercise(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads) {
        presenter.onNewExerciseAdded(exerciseId, reps, loads);
    }


    /*
     * Inner class, used to extend RecyclerView's ViewHolder for correct item binding
     */
    public class ExerciseHolder extends RecyclerView.ViewHolder implements iWorkoutExerciseListAdapter.View.Item {

        TextView name;
        TextView id;
        ImageView image;
        RecyclerView series;
        ExerciseSeriesRepsListAdapter adapter;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);

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
        public void setId(int Id) {
            this.id.setText(String.valueOf(Id));
        }

        @Override
        public void addSerie(int reps, float loads) {
            adapter.addSerieToList(reps, loads);
        }

        public void setSeriesList(ArrayList<Integer> reps, ArrayList<Float> loads) {
            adapter.setSeriesList(reps, loads);
        }

//        @Override
//        public void setDetails(ArrayList<Integer> reps, ArrayList<Float> loads) {
//
//
//
//
//            //            LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
//            //LayoutInflater inflater = activity.getLayoutInflater();
//
//            Log.d("EHEHEH VEDIAMO UN PO", "setDetails: " + reps + " " + loads);
//
//
//
//            for (int i = 0; i < reps.size(); i++) {
//                View view = inflater.inflate(R.layout.partial_exercise_details, this.details, false);
//
//                TextView txtSeries = view.findViewById(R.id.series);
//                TextView txtReps = view.findViewById(R.id.reps);
//                TextView txtLoads = view.findViewById(R.id.loads);
//
//                txtSeries.setText(String.valueOf(i + 1));
//                txtReps.setText(String.valueOf(reps.get(i)));
//                txtLoads.setText(String.valueOf(loads.get(i)));
//                details.addView(view);
//                Log.d("EHEHEH VEDIAMO UN PO", "setDetails: " + i);
//    }
//
//}
    }


}
