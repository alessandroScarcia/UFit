package it.sms1920.spqs.ufit.presenter;


import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.contract.RegistrationContract;

import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.AuthResultType.SIGNUP_SUCCESSFUL;
import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.AuthResultType.USER_ALREADY_EXISTS;
import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.InputErrorType.EMAIL_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.InputErrorType.EMAIL_FORMAT_NOT_VALID;
import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.InputErrorType.PASSWORDS_NOT_MATCHING;
import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.InputErrorType.PASSWORD_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.contract.RegistrationContract.Presenter.InputErrorType.PASSWORD_FORMAT_NOT_VALID;


public class RegistrationPresenter implements RegistrationContract.Presenter {
    private RegistrationContract.View view;
    static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$&+,:;=?#|'<>.^*()%!-])[A-Za-z\\d@$&+,:;=?#|'<>.^*()%!-]{6,}$";

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
    }


    private boolean checkFields(String emailField, String passwordField, String confirmPasswordField) {
        boolean bool = false;

        if (TextUtils.isEmpty(emailField))
            view.setInputError(EMAIL_FIELD_EMPTY); // email vuota
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches())
            view.setInputError(EMAIL_FORMAT_NOT_VALID); // non è una mail
        else if (TextUtils.isEmpty(passwordField))
            view.setInputError(PASSWORD_FIELD_EMPTY); // password vuota
        else if (!passwordField.matches(PASSWORD_REGEX))
            view.setInputError(PASSWORD_FORMAT_NOT_VALID); // formato password
        else if (!passwordField.equals(confirmPasswordField))
            view.setInputError(PASSWORDS_NOT_MATCHING); // password non corrispondono
        else bool = true;
        return bool;
    }


    @Override
    public void onSignUp(String email, String password, String confirmPassword) {

        if (checkFields(email, password, confirmPassword)) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                try {
                                    throw Objects.requireNonNull(task.getException());
                                } catch (FirebaseAuthUserCollisionException e) {
                                    returnSignUpResult(USER_ALREADY_EXISTS);
                                } catch (Exception e) {
                                    System.out.println(Arrays.toString(e.getStackTrace()));
                                }
                            } else
                                returnSignUpResult(SIGNUP_SUCCESSFUL);

                        }
                    });
        }
    }


    @Override
    public void returnSignUpResult(AuthResultType check) {
        if (check != SIGNUP_SUCCESSFUL) {
            view.setEnabledUI(true);
            view.setSignUpError(check);
        } else
            signUpSuccessful();
    }

    @Override
    public void signUpSuccessful() {
        view.startLauncherActivity();
        // TODO SESSION
    }

}
