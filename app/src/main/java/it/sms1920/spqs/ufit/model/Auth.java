package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.contract.RegistrationContract;

public class Auth {
    private String Uid;
    private String email;
    private String password;

    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Auth() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return Uid;
    }

    public void signIn(final Login.Presenter presenter) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(this.email, this.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                presenter.onResultSignIn(Login.View.EMAIL_NOT_MATCH);
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                presenter.onResultSignIn(Login.View.PASSWORD_NOT_MATCH);
                            } catch (Exception e) {
                                System.out.println(e.getStackTrace());
                            }
                        } else
                            presenter.onResultSignIn(Login.View.SIGNIN_SUCCESSFULL);
                    }
                });

    }

    public void signUp(final RegistrationContract.Presenter presenter) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(this.email, this.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }  catch (FirebaseAuthUserCollisionException e) {
                                presenter.onResultSignUp(RegistrationContract.View.USER_ALREADY_EXISTS);
                            } catch (Exception e) {
                                System.out.println(e.getStackTrace());
                            }
                        } else
                            presenter.onResultSignUp(RegistrationContract.View.SINGUP_SUCCESSFULL);

                    }
                });
        this.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
