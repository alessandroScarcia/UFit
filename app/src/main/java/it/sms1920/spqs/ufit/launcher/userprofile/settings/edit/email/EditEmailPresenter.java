package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.email;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.util.StringUtils;

public class EditEmailPresenter implements EditEmailContract.Presenter {
    private static final String TAG = EditEmailPresenter.class.getCanonicalName();
    private final EditEmailContract.View view;

    private FirebaseUser user;

    public EditEmailPresenter(EditEmailContract.View view) {
        this.view = view;

        user = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        if (user == null || user.isAnonymous()) {
            throw new IllegalStateException(TAG + " user is not logged! Profile Settings should not be accessible.");
        }

        view.showEmail(user.getEmail());
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    @Override
    public void confirmEdit(String email) {
        if (TextUtils.isEmpty(email)) {
            view.setError(EmailError.EMAIL_EMPTY);
        } else if (!StringUtils.isEmail(email)) {
            view.setError(EmailError.EMAIL_NOT_VALID);
        } else {
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "updateEmail:success");

                                view.endActivity();
                            } else {
                                Log.w(TAG, "updateEmail:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthRecentLoginRequiredException) {
                                    view.showAskReauthenticateDialog();
                                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    view.setError(EmailError.ALREADY_USED);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onReautenticate() {
        view.reauthenticate();
    }
}
