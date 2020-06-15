package it.sms1920.spqs.ufit.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ChangeEmailDialog extends AppCompatDialogFragment {

    public interface ChangeEmailDialogListener {
        void applyChangeEmail(String currentPassword, String newEmail);

    }

    private TextInputLayout txtCurrentPasswordLayout;
    private TextInputLayout txtNewEmailLayout;

    private TextInputEditText txtCurrentPassword;
    private TextInputEditText txtNewEmail;

    private ChangeEmailDialogListener listener;

    public static String currentPassword;
    public static String newEmail;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_change_email_dialog, null);


        txtCurrentPasswordLayout = view.findViewById(R.id.txtCurrentPasswordLayout);
        txtCurrentPassword = view.findViewById(R.id.txtCurrentPassword);


        txtNewEmailLayout = view.findViewById(R.id.txtNewEmailLayout);
        txtNewEmail = view.findViewById(R.id.txtNewEmail);


        builder.setView(view)
                .setTitle("Change Email")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentPassword = txtCurrentPassword.getText().toString();
                        newEmail = txtNewEmail.getText().toString();

                        listener.applyChangeEmail(currentPassword,newEmail);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChangeEmailDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangeEmailDialogListener");
        }
    }



}
