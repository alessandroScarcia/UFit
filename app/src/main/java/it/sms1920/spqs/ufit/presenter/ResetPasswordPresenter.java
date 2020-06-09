package it.sms1920.spqs.ufit.presenter;


import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.contract.ResetPassword;

public class ResetPasswordPresenter implements ResetPassword.Presenter {
    private ResetPassword.View view;

    public ResetPasswordPresenter(ResetPassword.View view) {
        this.view = view;
    }


    @Override
    public void onResetPassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            view.showCheckMailBox();
                        }
                    }
                });

    }
}
