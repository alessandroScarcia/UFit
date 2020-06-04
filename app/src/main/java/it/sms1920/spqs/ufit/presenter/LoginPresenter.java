package it.sms1920.spqs.ufit.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.model.Auth;

public class LoginPresenter implements Login.Presenter {
    private Login.View view;
    private Auth auth;

    public LoginPresenter(Login.View view) {
        this.view = view;
    }


    @Override
    public void checkFields(String emailField, String passwordField) {


        if (TextUtils.isEmpty(emailField))
            view.showInputFail(view.EMAIL_FIELD_EMPTY);
        else if (TextUtils.isEmpty(passwordField))
            view.showInputFail(view.PASSWORD_FIELD_EMPTY);
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches())
            view.showInputFail(view.EMAIL_NOT_VALID);
        else
            view.showInputFail(view.FIELDS_CORRECT);
    }

    @Override
    public void onSignIn(String email, String password) {
        auth = new Auth(email, password);

        auth.signIn(this);

    }

    @Override
    public void onResultSignIn(int check) {
        switch (check) {
            case Login.View.PASSWORD_NOT_MATCH:
                view.showSignInFail(Login.View.PASSWORD_NOT_MATCH);
                break;
            case Login.View.SIGNIN_SUCCESSFULL:
                view.showSignInFail(Login.View.SIGNIN_SUCCESSFULL);
                break;
            case Login.View.EMAIL_NOT_MATCH:
                view.showSignInFail(Login.View.EMAIL_NOT_MATCH);
                break;
        }
    }

}
