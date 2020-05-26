package it.sms1920.spqs.ufit;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.myViewHolder>{

    private Activity activity;
    private List<Exercise> lstExercise;



    ExerciseAdapter(Activity mContext, List<Exercise> lstExercise) {
        this.activity = mContext;
        this.lstExercise = lstExercise;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.layout_exercise_card, parent, false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            holder.image = holder.itemView.findViewById(R.id.imgExercise);
            holder.image.setImageResource(lstExercise.get(position).getImage());

            holder.name = holder.itemView.findViewById(R.id.txtExerciseName);
            holder.name.setText(lstExercise.get(position).getName());


            ImageView cardEsercizio = holder.itemView.findViewById(R.id.lytEsercizio);
            cardEsercizio.setOnClickListener( new ImageView.OnClickListener() {

                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ActivityExercise.class));

                    activity.overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
                }
            });
    }

    @Override
    public int getItemCount() {
        return lstExercise.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        myViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgExercise);
            name = itemView.findViewById(R.id.txtExerciseName);

        }
    }

    public List<Exercise> getLstExercise() {
        return lstExercise;
    }

}
