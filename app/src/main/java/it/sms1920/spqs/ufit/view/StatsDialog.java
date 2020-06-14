package it.sms1920.spqs.ufit.view;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;


public class StatsDialog extends AppCompatDialogFragment {

    private ExampleDialogListener listener;
    private EditText statsValueEditText;
    private EditText statsDateEditText;
    private DatePickerDialog datePickerDialog;
    public static int statValue;
    public static String statDate;

    int year;
    int mounth;
    int dayOfMounth;
    Calendar calendar;

    public TextView textViewValue;
    public TextView textViewDate;

    public StatsDialog(TextView textViewValue, TextView textViewDate) {
        this.textViewValue = textViewValue;
        this.textViewDate = textViewDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

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
                        statValue = Integer.parseInt(statsValueEditText.getText().toString());
                        statDate = statsDateEditText.getText().toString();
//                        String password = statsDateEditText.getText().toString();
//                        listener = (ExampleDialogListener) getActivity();
//                        StatsPresenter.value = statValue;
                        listener.applyTexts(statValue, statDate, textViewValue, textViewDate);
                    }
                });

        statsValueEditText = view.findViewById(R.id.stats_value);
        statsDateEditText = view.findViewById(R.id.stats_date);
        statsDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                mounth = calendar.get(Calendar.MONTH);
                dayOfMounth = calendar.get(Calendar.DAY_OF_MONTH);
//                presenter.onCreateDialog(savedInstanceState);
                datePickerDialog = new DatePickerDialog(getTargetFragment().getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                statsDateEditText.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, year, mounth, dayOfMounth);
                datePickerDialog.show();
            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(float value, String date, TextView textViewValue, TextView textViewDate);
    }

}
