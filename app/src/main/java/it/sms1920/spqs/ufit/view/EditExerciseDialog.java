package it.sms1920.spqs.ufit.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sms1920.spqs.ufit.contract.iExerciseDialog;
import it.sms1920.spqs.ufit.model.ExerciseSetItem;
import it.sms1920.spqs.ufit.presenter.EditExerciseDialogPresenter;


/**
 * Dialog used to edit exercise's series
 */
public class EditExerciseDialog extends AppCompatDialogFragment implements iExerciseDialog.View {

    // Reference to implementer of DialogListener interface. Needed to get data back from dialog.
    private DialogListener dialogListener;

    private iExerciseDialog.Presenter presenter;
    private ExerciseSeriesRepsListAdapter subListAdapter;

    // Current exercise ID
    private String exerciseId;
    private TextView lblName;
    private RecyclerView rv;

    public EditExerciseDialog(String exerciseId, DialogListener listener) {
        this.exerciseId = exerciseId;
        this.dialogListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_exercise, null);

        lblName = view.findViewById(R.id.txtExerciseName);
        rv = view.findViewById(R.id.rvSeries);

        // Setting recycler view
        subListAdapter = new ExerciseSeriesRepsListAdapter(true);
        if (dialogListener.getList() != null) {
            subListAdapter.setSeriesList(dialogListener.getList());
        }
        rv.setAdapter(subListAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Setting button to add another
        Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subListAdapter.addSerieToList(0, 0);
                subListAdapter.callNotifyDatasetChanged();
            }
        });

        // Initializing presenter, sending request for exercise name. We'll need it in view builder
        presenter = new EditExerciseDialogPresenter(this);
        presenter.onExerciseNameRequested(exerciseId);

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
                        /*
                            Calling interface's method that will be implemented in each view that use this dialog
                         */
                        dialogListener.saveData(exerciseId, presenter.getExerciseName(), subListAdapter.getReps(), subListAdapter.getLoads());
                    }
                });
        return builder.create();
    }

    @Override
    public void setExerciseName(String name) {
        lblName.setText(name);
    }


    /**
     * This interface should be implemented from each Class that use this Dialog. Needed for passing data back from dialog.
     */
    interface DialogListener {
        void saveData(String exerciseId, String exerciseName, ArrayList<Integer> reps, ArrayList<Float> loads);

        ArrayList<ExerciseSetItem> getList();
    }
}
