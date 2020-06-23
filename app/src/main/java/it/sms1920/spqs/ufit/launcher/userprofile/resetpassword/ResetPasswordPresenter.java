package it.sms1920.spqs.ufit.launcher.userprofile.resetpassword;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.util.StringUtils;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {
    private ResetPasswordContract.View view;

    public ResetPasswordPresenter(ResetPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void onSendEmailButtonClicked(String email) {
        view.setEnabledUi(false);

        if (checkEmail(email)) {
            FirebaseAuth auth = FirebaseAuthSingleton.getFirebaseAuth();

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                view.showEmailSentMessage();
                            }
                        }
                    });
        } else {
            view.setError();
            view.setEnabledUi(true);
        }
    }

    private boolean checkEmail(String email) {
        return !TextUtils.isEmpty(email) && StringUtils.isEmail(email);
    }

    @Override
    public void onBackPressed() {
        view.closeActivity();
    }
}
