package it.sms1920.spqs.ufit.presenter;


import android.text.TextUtils;

import it.sms1920.spqs.ufit.contract.RegistrationContract;
import it.sms1920.spqs.ufit.model.Auth;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private RegistrationContract.View view;
    private Auth auth;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
    }

    @Override
    public void onSignUp(String email, String password, String confirmPassword) {

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) )
            view.showValidationError("Fields are empty");
        else if(password.equals(confirmPassword)){
            auth = new Auth();
            auth.setEmail(email);
            auth.setPassword(password);

            auth.signUp(this);
        }else{
            view.showValidationError("Passwords do not match");
        }

    }


    @Override
    public void onResultSignUp(int check) {
        switch( check) {
            case RegistrationContract.View.SINGUP_SUCCESSFULL:
                view.showSignUpSuccessFully("Signup successfully");
                break;
            case RegistrationContract.View.USER_ALREADY_EXISTS:
                view.showSignUpFail("User already exists");
                break;
            case RegistrationContract.View.EMAIL_NOT_VALID:
                view.showValidationError("Email is not valid");
                break;
            default:
                break;
        }
    }

}
