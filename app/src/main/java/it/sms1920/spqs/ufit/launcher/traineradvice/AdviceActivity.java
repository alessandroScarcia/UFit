package it.sms1920.spqs.ufit.launcher.traineradvice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.sms1920.spqs.ufit.launcher.R;

public class AdviceActivity extends AppCompatActivity implements AdviceContract.View {
    private AdviceContract.Presenter presenter;

    private Toolbar toolbar;

    private AdviceAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice_activity);

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(getString(R.string.new_trainer_advice));
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        presenter = new AdvicePresenter(this);
        adapter = new AdviceAdapter(this);

        FloatingActionButton fabAdd = findViewById(R.id.btnAddAdvice);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFabButtonClicked();
            }
        });

        // initialize view references
        RecyclerView rvAdvice = findViewById(R.id.rvAdvice);

        // setup recyclerview for advice list
        rvAdvice.setAdapter(adapter);
        rvAdvice.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar.
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }

    /**
     * Function that communicate with the subclass to show the content of the dialog box
     */
    @Override
    public void openDialog() {
        AdviceDialog dialogBox = new AdviceDialog((AdviceDialog.AdviceDialogListener) presenter);
        dialogBox.show(getSupportFragmentManager(), null);
    }

    @Override
    public void addNewAdvice(String title, String description) {
        adapter.addNewAdvice(title, description);
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    /**
     * This subclass is a component of the fragment that allow to show a dialog box to insert the values
     * of the advice
     */
    public static class AdviceDialog extends AppCompatDialogFragment {
        public String titleAdvice;
        public String descriptionAdvice;
        private AdviceDialogListener presenter;
        private EditText etTitleDialog;
        private EditText etDescriptionDialog;

        public AdviceDialog(AdviceDialogListener presenter) {
            this.presenter = presenter;
        }

        /**
         * When the dialog is created the layout show 2 editText inside it to insert the title and
         * the description of the new advice
         *
         * @param savedInstanceState .
         * @return builder.create()
         */
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.fragment_new_advice_dialog, null);

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
                            presenter.onPositiveButtonClicked(etTitleDialog.getText().toString(), etDescriptionDialog.getText().toString());
                        }
                    });

            etTitleDialog = view.findViewById(R.id.etTitleAdvice);
            etDescriptionDialog = view.findViewById(R.id.etDescriptionAdvice);

            return builder.create();

        }

        interface AdviceDialogListener {
            void onPositiveButtonClicked(String title, String description);
        }
    }


}
