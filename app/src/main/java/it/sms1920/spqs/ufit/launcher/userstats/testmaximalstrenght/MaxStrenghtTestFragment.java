package it.sms1920.spqs.ufit.launcher.userstats.testmaximalstrenght;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;


public class MaxStrenghtTestFragment extends Fragment implements MaxStrenghtTestContract.View{

    private static MaxStrenghtTestAdapter adapter;
    private MaxStrenghtTestContract.Presenter presenter;
    private EditText etInsertWeight;

    private static int checkIdRadioButton = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MaxStrenghtTestAdapter();
        presenter = new MaxStrenghtTestPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        Button btnSelectReps = view.findViewById(R.id.btnRepsDialog);

        btnSelectReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSelectRepsClicked();
            }
        });

        Button btnCalculate = view.findViewById(R.id.btnConfirmCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int weightResult = presenter.checkDataToAdapter(checkIdRadioButton, etInsertWeight.getText().toString());
                adapter.calculateWeight(checkIdRadioButton, weightResult);
            }
        });

        etInsertWeight = view.findViewById(R.id.etWeightUsed);

        // initialize view references
        RecyclerView rvAdvice = view.findViewById(R.id.rvWeight);

        // setup recyclerview for list of weight and reps
        rvAdvice.setAdapter(adapter);
        rvAdvice.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    /**
     * Function that communicate with the subclass to show the content of the dialog box
     */
    @Override
    public void openDialog() {
        RepsChoiceDialog dialogBox = new RepsChoiceDialog();
        dialogBox.setTargetFragment(this, 1);
        dialogBox.show(getParentFragmentManager(), "example dialog");
    }


    /**
     * This subclass is a component of the fragment that allow to show a dialog box to select the values
     * of the reps done in a set
     */
    public static class RepsChoiceDialog extends AppCompatDialogFragment {
        private RadioGroup rgReps;

        /**
         * Construct od the RepsChoiceDialog
         */
        public RepsChoiceDialog() {}

        /**
         * When the dialog is created the layout show a list of checkbox with the reps done in a set
         * @param savedInstanceState istance of the dialog
         * @return builder.create()
         */
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.fragment_choose_reps_dialog, null);

            builder.setView(view)
                    .setTitle(R.string.insertValue)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //on selected checkbox we assign an index
                            switch (rgReps.getCheckedRadioButtonId()){
                                case R.id.RM:
                                    checkIdRadioButton = 1;
                                    break;
                                case R.id.reps3_2:
                                    checkIdRadioButton = 2;
                                    break;
                                case R.id.reps5_4:
                                    checkIdRadioButton = 3;
                                    break;
                                case R.id.reps7_6:
                                    checkIdRadioButton = 4;
                                    break;
                                case R.id.reps9_8:
                                    checkIdRadioButton = 5;
                                    break;
                                case R.id.reps11_10:
                                    checkIdRadioButton = 6;
                                    break;
                                case R.id.reps13_12:
                                    checkIdRadioButton = 7;
                                    break;
                                case R.id.reps15_14:
                                    checkIdRadioButton = 8;
                                    break;
                                case R.id.reps17_16:
                                    checkIdRadioButton = 9;
                                    break;
                                case R.id.reps18_19:
                                    checkIdRadioButton = 10;
                                    break;
                                case R.id.reps20:
                                    checkIdRadioButton = 11;
                                    break;
                            }

                        }
                    });

            rgReps = view.findViewById(R.id.rgRepsChoice);

            return builder.create();

        }
    }


}
