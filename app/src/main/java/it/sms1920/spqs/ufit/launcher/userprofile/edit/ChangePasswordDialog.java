package it.sms1920.spqs.ufit.launcher.userprofile.edit;

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

import it.sms1920.spqs.ufit.launcher.R;


public class ChangePasswordDialog extends AppCompatDialogFragment {

    private ChangePasswordDialog.ChangePasswordDialogListener listener;

    private TextInputLayout txtCurentPasswordLayout;
    private TextInputLayout txtNewPasswordLayout;

    private TextInputEditText txtCurrentPassword;
    private TextInputEditText txtNewPassword;

    public interface ChangePasswordDialogListener {
        void applyChangePassword(String currentPassword, String newPassword);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChangePasswordDialog.ChangePasswordDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangeEmailDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_change_password_dialog, null);

        txtCurentPasswordLayout = view.findViewById(R.id.txtCurrPasswordLayout);
        txtNewPasswordLayout = view.findViewById(R.id.txtNewEmailLayout);

        txtCurrentPassword = view.findViewById(R.id.txtCurrPassword);
        txtNewPassword = view.findViewById(R.id.txtNewPassword);

        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String currentPassword = txtCurrentPassword.getText().toString();
                        String newPassword = txtNewPassword.getText().toString();

                        listener.applyChangePassword(currentPassword,newPassword);
                    }
                });


        return builder.create();
    }
}
