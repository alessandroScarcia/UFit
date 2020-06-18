package it.sms1920.spqs.ufit.launcher.userprofile.resetpassword;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {
    private ResetPasswordContract.View view;

    public ResetPasswordPresenter(ResetPasswordContract.View view) {
        this.view = view;
    }


    @Override
    public void onResetPassword(String email) {
        FirebaseAuth auth = FirebaseAuthSingleton.getFirebaseAuth();

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

    @Override
    public void onBackPressed() {
        view.closeActivity();
    }
}
