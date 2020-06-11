package it.sms1920.spqs.ufit.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.contract.iLogin;

import static it.sms1920.spqs.ufit.contract.iLogin.Presenter.AuthResultType.EMAILS_NOT_MATCH;
import static it.sms1920.spqs.ufit.contract.iLogin.Presenter.AuthResultType.PASSWORDS_NOT_MATCH;
import static it.sms1920.spqs.ufit.contract.iLogin.Presenter.AuthResultType.SUCCESS;
import static it.sms1920.spqs.ufit.contract.iLogin.Presenter.InputErrorType.EMAIL_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.contract.iLogin.Presenter.InputErrorType.EMAIL_FORMAT_NOT_VALID;
import static it.sms1920.spqs.ufit.contract.iLogin.Presenter.InputErrorType.PASSWORD_FIELD_EMPTY;


public class LoginPresenter implements iLogin.Presenter {
    private iLogin.View view;
    FirebaseAuth firebaseAuth;
    public LoginPresenter(iLogin.View view) {
        this.view = view;
    }


    public boolean checkFields(String emailField, String passwordField) {
        boolean bool = false;

        if (TextUtils.isEmpty(emailField)) {
            view.setInputError(EMAIL_FIELD_EMPTY);
        } else if (TextUtils.isEmpty(passwordField)) {
            view.setInputError(PASSWORD_FIELD_EMPTY);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches()) {
            view.setInputError(EMAIL_FORMAT_NOT_VALID);
        } else bool = true;
        return bool;
    }

    @Override
    public void onSignIn(String email, String password) {
        if (checkFields(email, password)) {
            view.setEnabledUI(false);

            firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    returnSignInResult(EMAILS_NOT_MATCH);
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    returnSignInResult(PASSWORDS_NOT_MATCH);
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }

                            } else
                                returnSignInResult(SUCCESS);
                        }
                    });
        }
    }


    @Override
    public void returnSignInResult(AuthResultType check) {
        if (check != SUCCESS) {
            view.setEnabledUI(true);
            view.setSignInError(check);
        } else
            signInSuccessful();
    }

    @Override
    public void signInSuccessful() {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();

        if( fbUser != null ) {
            view.startLauncherActivity();

        }
    }

    @Override
    public void onBackPressed() {
        view.startLauncherActivity();
    }

}
