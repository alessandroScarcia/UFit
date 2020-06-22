package it.sms1920.spqs.ufit.launcher.userprofile.registration;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Objects;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.AuthResultType.SIGNUP_SUCCESSFUL;
import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.AuthResultType.USER_ALREADY_EXISTS;
import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.InputErrorType.EMAIL_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.InputErrorType.EMAIL_FORMAT_NOT_VALID;
import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.InputErrorType.PASSWORDS_NOT_MATCHING;
import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.InputErrorType.PASSWORD_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.InputErrorType.PASSWORD_FORMAT_NOT_VALID;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private static final String TAG = RegistrationPresenter.class.getCanonicalName();

    private RegistrationContract.View view;
    private static final String PASSWORD_REGEX = "(?=.*[A-Z][a-z])(?=.*\\d)(?=.*[@$&+,:;=?#|'<>.^*()%!-])[A-Za-z\\d@$&+,:;=?#|'<>.^*()%!-]{6,}$";

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
    }

    @Override
    public void onRegisterButtonClicked(String email, String password, String confirmPassword) {
        view.setEnabledUI(false);

        if (checkFields(email, password, confirmPassword)) {
            FirebaseAuth firebaseAuth = FirebaseAuthSingleton.getFirebaseAuth();
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser != null) {
                firebaseUser.linkWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "linkWithCredentials:success");
                                    registrationResult(SIGNUP_SUCCESSFUL);
                                } else {
                                    Log.w(TAG, "linkWithCredentials:failure", task.getException());
                                    registrationResult(USER_ALREADY_EXISTS);
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onLoginButtonClicked() {
        view.startLoginActivity();
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    private void registrationResult(AuthResultType check) {
        if (check != SIGNUP_SUCCESSFUL) {
            view.setEnabledUI(true);
            view.setRegistrationError(check);
        } else {
            registrationSuccessful();
        }
    }

    private void registrationSuccessful() {
        view.endActivity();
    }

    private boolean checkFields(String emailField, String passwordField, String confirmPasswordField) {
        boolean emailCheck = false;
        boolean passwordCheck = false;
        boolean confirmPasswordCheck = false;

        if (TextUtils.isEmpty(emailField)) {
            view.setInputError(EMAIL_FIELD_EMPTY); // email vuota
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches()) {
            view.setInputError(EMAIL_FORMAT_NOT_VALID); // non è una mail
        } else {
            emailCheck = true;
        }

        if (TextUtils.isEmpty(passwordField)) {
            view.setInputError(PASSWORD_FIELD_EMPTY); // password vuota
        } else if (!passwordField.matches(PASSWORD_REGEX)) {
            view.setInputError(PASSWORD_FORMAT_NOT_VALID);// formato password
        } else {
            passwordCheck = true;
        }

        if (!passwordField.equals(confirmPasswordField)) {
            view.setInputError(PASSWORDS_NOT_MATCHING); // password non corrispondono
        } else {
            confirmPasswordCheck = true;
        }

        return (emailCheck && passwordCheck && confirmPasswordCheck);
    }
}
