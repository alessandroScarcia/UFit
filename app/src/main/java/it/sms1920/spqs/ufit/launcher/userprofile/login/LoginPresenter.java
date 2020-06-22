package it.sms1920.spqs.ufit.launcher.userprofile.login;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.WorkoutPlan;

import static it.sms1920.spqs.ufit.launcher.userprofile.login.LoginContract.Presenter.AuthResultType.FAILURE;
import static it.sms1920.spqs.ufit.launcher.userprofile.login.LoginContract.Presenter.AuthResultType.SUCCESS;
import static it.sms1920.spqs.ufit.launcher.userprofile.login.LoginContract.Presenter.InputErrorType.EMAIL_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.launcher.userprofile.login.LoginContract.Presenter.InputErrorType.EMAIL_FORMAT_NOT_VALID;
import static it.sms1920.spqs.ufit.launcher.userprofile.login.LoginContract.Presenter.InputErrorType.PASSWORD_FIELD_EMPTY;


public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginActivity.class.getCanonicalName();

    private LoginContract.View view;
    private FirebaseAuth firebaseAuth;
    private String previousUserId = null;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;

        this.firebaseAuth = FirebaseAuthSingleton.getFirebaseAuth();

        FirebaseUser anonymousUser = firebaseAuth.getCurrentUser();
        if (anonymousUser != null) {
            previousUserId = anonymousUser.getUid();
            Log.d(TAG, "prevoiousUid " + previousUserId);
        }
    }

    @Override
    public void onLoginButtonClicked(String email, String password) {
        if (checkFields(email, password)) {
            view.setEnabledUi(false);

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmailAndPasword:success");
                                returnLoginResult(SUCCESS);
                            } else {
                                Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                                returnLoginResult(FAILURE);
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    private boolean checkFields(String emailField, String passwordField) {
        boolean emailCheck = false;
        boolean passwordCheck = false;

        if (TextUtils.isEmpty(emailField)) {
            view.setInputError(EMAIL_FIELD_EMPTY);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches()) {
            view.setInputError(EMAIL_FORMAT_NOT_VALID);
        } else {
            emailCheck = true;
        }

        if (TextUtils.isEmpty(passwordField)) {
            view.setInputError(PASSWORD_FIELD_EMPTY);
        } else {
            passwordCheck = true;
        }

        return (emailCheck && passwordCheck);
    }

    private void returnLoginResult(AuthResultType check) {
        if (check == FAILURE) {
            view.setEnabledUi(true);
            view.setLoginError(check);
        } else {
            loginSuccessful();
        }
    }

    private void loginSuccessful() {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser != null && !fbUser.isAnonymous()) {
            exportWorkoutPlans(fbUser.getUid());
        }
    }

    private void exportWorkoutPlans(final String userId) {
        final DatabaseReference mWorkoutPlansReference =
                FirebaseDbSingleton.getInstance().getReference(WorkoutPlan.CHILD_NAME);

        Query mWorkoutPlansQuery = mWorkoutPlansReference.orderByChild("userOwnerId").equalTo(previousUserId);
        mWorkoutPlansQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    if (key != null) {
                        Log.d(TAG, "workoutPlanKey " + key);
                        mWorkoutPlansReference.child(child.getKey()).child("userOwnerId").setValue(userId);
                    }
                }

                view.endActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
