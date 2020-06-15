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
import it.sms1920.spqs.ufit.contract.iExerciseDialog;
import it.sms1920.spqs.ufit.model.ExerciseSetItem;
import it.sms1920.spqs.ufit.presenter.EditExerciseDialogPresenter;
import it.sms1920.spqs.ufit.presenter.ExerciseSetsList;

public class EditExerciseDialog extends AppCompatDialogFragment implements iExerciseDialog.View {

    private DialogListener listener;
    private String exerciseId;
    private ArrayList<Integer> reps;
    private ArrayList<Float> loads;
    private RecyclerView rv;
    private ExerciseSeriesRepsListAdapter adapter;
    private View view;
    private TextView lblName;
    private iExerciseDialog.Presenter presenter;


    public EditExerciseDialog(String exerciseId, DialogListener listener) {
        reps = new ArrayList<>();
        loads = new ArrayList<>();
        this.exerciseId = exerciseId;
        try {
            this.listener = listener;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement ExampleDialogListener");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        presenter = new EditExerciseDialogPresenter(this);

        view = inflater.inflate(R.layout.dialog_edit_exercise, null);
        lblName = view.findViewById(R.id.txtExerciseName);


        presenter.onExerciseNameRequested(exerciseId);

        rv = view.findViewById(R.id.rvSeries);
        adapter = new ExerciseSeriesRepsListAdapter(true);

        if ( listener.getList() != null )
            adapter.setSeriesList( listener.getList() );

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


                        listener.saveData(exerciseId, presenter.getExerciseName(),reps, loads);
                    }
                });

        return builder.create();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void setExerciseName(String name) {
        lblName.setText(name);
    }

    interface DialogListener {
        void saveData(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads);
        ArrayList<ExerciseSetItem> getList();
    }


}
