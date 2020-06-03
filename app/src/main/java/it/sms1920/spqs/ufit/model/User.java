package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.contract.RegistrationContract;

public class User {

    final static String USER = "User";
    final static int SINGUP_SUCCESSFULL = 0;
    final static int PASSWORD_IS_NOT_STRONG_ENOUGH = 1;
    final static int EMAIL_MALFORMED = 2;
    final static int USER_ALREADY_EXISTS = 3;
    final static int EMAIL_NOT_MATCH = 4;
    final static int PASSWORD_NOT_MATCH = 5;


    private String name;
    private String surname;
    private String gender;
    private Date dateBirth;
    private int bodyWeight;
    private int height; // express in cm
    private String email;
    private String password;


    public User(String name, String surname, String gender, Date dateBirth, int bodyWeight, int height) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.bodyWeight = bodyWeight;
        this.height = height;
    }

    public User() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setSex(String gender) {
        this.gender = gender;
    }

    public int getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(int bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }


    public void singInUser(final Login.Presenter presenter) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(this.email, this.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                presenter.onResultSignIn(EMAIL_NOT_MATCH);
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                presenter.onResultSignIn(PASSWORD_NOT_MATCH);
                            } catch (Exception e) {
                                System.out.println(e.getStackTrace());
                            }
                        } else
                            presenter.onResultSignIn(SINGUP_SUCCESSFULL);
                    }
                });

    }

    public void signUpNewUser(final RegistrationContract.Presenter presenter) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(this.email, this.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                presenter.onResultSignUp(PASSWORD_IS_NOT_STRONG_ENOUGH);
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                presenter.onResultSignUp(EMAIL_MALFORMED);
                            } catch (FirebaseAuthUserCollisionException e) {
                                presenter.onResultSignUp(USER_ALREADY_EXISTS);
                            } catch (Exception e) {
                                System.out.println(e.getStackTrace());
                            }
                        } else {
                            presenter.onResultSignUp(SINGUP_SUCCESSFULL);

                            FirebaseDatabase.getInstance().getReference(USER)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(User.this);
                        }

                    }
                });
    }


}

