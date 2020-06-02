package it.sms1920.spqs.ufit.presenter;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;

import it.sms1920.spqs.ufit.contract.RegistrationContract;
import it.sms1920.spqs.ufit.model.User;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private RegistrationContract.View view;
    private User userReg;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.userReg = new User();
        this.view = view;
    }

    @Override
    public void handleSignUp(String email, String password, String confirmPassword) {
        userReg.setEmail(email);
        userReg.setPassword(password);

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) )
            view.showValidationError();
        else if(password.equals(confirmPassword)){

        }

        if (password.equals(confirmPassword) && userReg.signUpNewUser())
            view.showSignUpSuccessFully();
        else if (!password.equals(confirmPassword) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            view.showValidationError();
        else if (!userReg.signUpNewUser())
            view.showSignUpFail();
    }


   /* @Override
    public void signUp(DatabaseReference databaseReference, FirebaseAuth firebaseAuth, User user) {
        final User userReg = (User) user.clone();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(userReg.getEmail(), userReg.getPassword())
                .addOnCompleteListener((Executor) RegistrationPresenter.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userReg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                        }
                    }
                });
    }*/
}
