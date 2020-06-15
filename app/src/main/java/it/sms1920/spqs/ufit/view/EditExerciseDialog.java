package it.sms1920.spqs.ufit.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditExerciseDialog extends AppCompatDialogFragment {

    private DialogListener listener;
    private String exerciseId;
    private ArrayList<Integer> reps;
    private ArrayList<Float> loads;
    RecyclerView rv;
    ExerciseSeriesRepsListAdapter adapter;

    public EditExerciseDialog(String exerciseId) {
        reps = new ArrayList<>();
        loads = new ArrayList<>();
        this.exerciseId = exerciseId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_exercise, null);

        rv = view.findViewById(R.id.rvSeries);
        adapter = new ExerciseSeriesRepsListAdapter(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addSerieToList(0,0);
                adapter.callNotifyDatasetChanged();
            }
        });

        // Populate view properly

        builder.setView(view)
                .setTitle("Aggiungi le serie:")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Hai annullato l'aggiunta dell'esericizio", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        reps = adapter.getReps();
                        loads = adapter.getLoads();


                        listener.saveData(exerciseId,reps, loads);
                    }
                });

        return builder.create();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    interface DialogListener {
        void saveData(String exerciseId, ArrayList<Integer> reps, ArrayList<Float> loads);
    }


}
