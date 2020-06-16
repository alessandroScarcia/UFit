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


public class DeleteProfileDialog extends AppCompatDialogFragment {

    private DeleteProfileDialog.DeleteProfileDialogListener listener;

    private TextInputLayout txtCPasswordLayout;

    private TextInputEditText txtCPassword;

    public interface DeleteProfileDialogListener {
        void applyDelete(String currentPassword);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DeleteProfileDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangeEmailDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_delete_dialog, null);

        txtCPasswordLayout = view.findViewById(R.id.txtCPasswordLayout);

        txtCPassword = view.findViewById(R.id.txtCPassword);

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
                        String currentPassword = txtCPassword.getText().toString();

                        listener.applyDelete(currentPassword);
                    }
                });


        return builder.create();
    }
}
