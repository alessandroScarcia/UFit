package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.password;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

public class EditPasswordPresenter implements EditPasswordContract.Presenter {
    private static final String TAG = EditPasswordPresenter.class.getCanonicalName();
    private static final String PASSWORD_REGEX = "(?=.*[A-Z][a-z])(?=.*\\d)(?=.*[@$&+,:;=?#|'<>.^*()%!-])[A-Za-z\\d@$&+,:;=?#|'<>.^*()%!-]{6,}$";
    private final EditPasswordContract.View view;
    private FirebaseUser user;

    public EditPasswordPresenter(EditPasswordContract.View view) {
        this.view = view;

        user = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        if (user == null || user.isAnonymous()) {
            throw new IllegalStateException(TAG + " user is not logged! Profile Settings should not be accessible.");
        }
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    @Override
    public void confirmEdit(String password, String confirmPassword) {
        if (TextUtils.isEmpty(password)) {
            view.setError(PasswordError.PASSWORD_EMPTY);
        } else if (!password.matches(PASSWORD_REGEX)) {
            view.setError(PasswordError.PASSWORD_TOO_WEAK);
        } else if (!password.equals(confirmPassword)) {
            view.setError(PasswordError.PASSWORDS_NOT_MATHING);
        } else {
            user.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "updatePassword:success");
                                view.endActivity();
                            } else {
                                Log.w(TAG, "updatePassword:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthRecentLoginRequiredException) {
                                    view.reauthenticate();
                                }
                            }
                        }
                    });
        }
    }
}
