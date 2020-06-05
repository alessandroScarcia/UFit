package it.sms1920.spqs.ufit.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.model.Auth;

import static it.sms1920.spqs.ufit.contract.Login.View.AuthResultType.SUCCESS;
import static it.sms1920.spqs.ufit.contract.Login.View.InputErrorType.EMAIL_FIELD_EMPTY;
import static it.sms1920.spqs.ufit.contract.Login.View.InputErrorType.EMAIL_FORMAT_NOT_VALID;
import static it.sms1920.spqs.ufit.contract.Login.View.InputErrorType.PASSWORD_FIELD_EMPTY;

public class LoginPresenter implements Login.Presenter {
    private Login.View view;
    private Auth auth;

    public LoginPresenter(Login.View view) {
        this.view = view;
    }


    public boolean checkFields(String emailField, String passwordField) {
        boolean bool = true;

        if (TextUtils.isEmpty(emailField)) {
            view.setInputError(EMAIL_FIELD_EMPTY);
            bool = false;
        } else if (TextUtils.isEmpty(passwordField)) {
            view.setInputError(PASSWORD_FIELD_EMPTY);
            bool = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches()) {
            view.setInputError(EMAIL_FORMAT_NOT_VALID);
            bool = false;
        }
        return bool;
    }

    @Override
    public void onSignIn(String email, String password) {
        if (checkFields(email, password)) {
            view.setEnabledUI(false);

            auth = new Auth(email, password);
            auth.signIn(this);
        }
    }

    @Override
    public void returnSignInResult(Login.View.AuthResultType check) {
        if (check != SUCCESS) {
            view.setEnabledUI(true);
            view.setSignInError(check);
        } else
            signInSuccessful();
    }

    @Override
    public void signInSuccessful() {
        view.startLauncherActivity();
    }

}
