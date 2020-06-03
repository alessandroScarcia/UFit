package it.sms1920.spqs.ufit.presenter;


import android.text.TextUtils;

import it.sms1920.spqs.ufit.contract.RegistrationContract;
import it.sms1920.spqs.ufit.model.User;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    final static int SINGUP_SUCCESSFULL = 0;
    final static int PASSWORD_IS_NOT_STRONG_ENOUGH = 1;
    final static int EMAIL_MALFORMED = 2;
    final static int USER_ALREADY_EXISTS = 3;

    private RegistrationContract.View view;
    private User userReg;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
    }

    @Override
    public void onSignUp(String email, String password, String confirmPassword) {
        userReg = new User();
        userReg.setEmail(email);
        userReg.setPassword(password);

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) )
            view.showValidationError("Fields are empty");
        else if(password.equals(confirmPassword)){
            userReg.signUpNewUser(this);
        }else{
            view.showValidationError("Passwords do not match");
        }

    }


    @Override
    public void onResultSignUp(int check) {
        switch( check) {
            case SINGUP_SUCCESSFULL:
                view.showSignUpSuccessFully("Signup successfully");
                break;
            case PASSWORD_IS_NOT_STRONG_ENOUGH:
                view.showValidationError("Password is weak");
                break;
            case USER_ALREADY_EXISTS:
                view.showSignUpFail("User already exists");
                break;
            case EMAIL_MALFORMED:
                view.showValidationError("Credentials is not valid");
                break;
            default:
                break;
        }
    }

}
