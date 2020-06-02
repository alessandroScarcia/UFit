package it.sms1920.spqs.ufit.contract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import it.sms1920.spqs.ufit.model.User;

public interface RegistrationContract {
    interface View{
        void showSignUpSuccessFully();
        void showSignUpFail();
        void showValidationError();
    }
    interface Presenter{
        void handleSignUp( String email, String password, String confirmPassword);
    }
}
