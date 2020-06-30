package it.sms1920.spqs.ufit.launcher.traineradvice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.sms1920.spqs.ufit.launcher.R;



public class AdviceFragment extends Fragment implements AdviceContract.View {
    private AdviceContract.Presenter presenter;

    private static AdviceAdapter adapter;

    private FloatingActionButton fabAdd;

    public static Context context;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AdvicePresenter(this);
        adapter = new AdviceAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice, container, false);
        context = view.getContext();
        fabAdd = view.findViewById(R.id.btnAddAdvice);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        // initialize view references
        RecyclerView rvAdvice = view.findViewById(R.id.rvAdvice);

        // setup recyclerview for advice list
        rvAdvice.setAdapter(adapter);
        rvAdvice.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    /**
     * Function that communicate with the subclass to show the content of the dialog box
     */
    public void openDialog() {
        AdviceDialog dialogBox = new AdviceDialog();
        dialogBox.setTargetFragment(this, 1);
        dialogBox.show(getFragmentManager(), "example dialog");
    }


    /**
     * This subclass is a component of the fragment that allow to show a dialog box to insert the values
     * of the advice
     */
    public static class AdviceDialog extends AppCompatDialogFragment {
        private EditText etTitleDialog;
        private EditText etDescriptionDialog;

        public String titleAdvice;
        public String descriptionAdvice;

        /**
         * Construct od the Advice Dialog
         */
        public AdviceDialog() {}

        /**
         * When the dialog is created the layout show 2 editText inside it to insert the title and
         * the description of the new advice
         * @param savedInstanceState
         * @return builder.create()
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_dialog_advice, null);

            builder.setView(view)
                    .setTitle("Inserisci Dati")
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            titleAdvice = etTitleDialog.getText().toString();
                            descriptionAdvice = etDescriptionDialog.getText().toString();
                            adapter.addNewAdvice(titleAdvice,descriptionAdvice);
                        }
                    });

            etTitleDialog = view.findViewById(R.id.etTitleAdvice);
            etDescriptionDialog = view.findViewById(R.id.etDescriptionAdvice);

            return builder.create();

        }
    }




}
