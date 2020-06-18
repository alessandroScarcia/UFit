package it.sms1920.spqs.ufit.launcher.userstats;


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

import it.sms1920.spqs.ufit.launcher.R;


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


    /**
     * Construct od the Stats Dialog
     * @param textViewValue textView that will contain the value of the parameter setted
     * @param textViewDate textView that will contain the date of the parameter setted
     */
    public StatsDialog(TextView textViewValue, TextView textViewDate) {
        this.textViewValue = textViewValue;
        this.textViewDate = textViewDate;
    }

    /**
     * When the dialog is created the layout show 2 editText inside it. One of them use datePicker
     * @param savedInstanceState
     * @return builder.create()
     */
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
                        try {
                        statValue = Integer.parseInt(statsValueEditText.getText().toString());
                        } catch (NumberFormatException e) {
//                            Snackbar.make(getContext(), R.string.error_value_message, LENGTH_SHORT)
//                                    .show();
                            statValue = 0;
                        }
                        statDate = statsDateEditText.getText().toString();
                        listener.applyTexts(statValue, statDate, textViewValue, textViewDate);
                    }
                });

        statsValueEditText = view.findViewById(R.id.stats_value);

        //setting options of DatePicker
        statsDateEditText = view.findViewById(R.id.stats_date);
        statsDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //the instance of calendar is necessary to set current date inside the DatePicker
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                mounth = calendar.get(Calendar.MONTH);
                dayOfMounth = calendar.get(Calendar.DAY_OF_MONTH);

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

    /**
     * Method called when a fragment is first attached to its context
     */
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


    /**
     * Interface used to pass the data taked from the tewxt view of the dialog into the text
     * view of the fragment
     */
    public interface ExampleDialogListener {
        void applyTexts(float value, String date, TextView textViewValue, TextView textViewDate);
    }

}
