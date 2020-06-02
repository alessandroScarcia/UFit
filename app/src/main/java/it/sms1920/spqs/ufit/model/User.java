package it.sms1920.spqs.ufit.model;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import it.sms1920.spqs.ufit.view.LauncherActivity;
import it.sms1920.spqs.ufit.view.RegistrationActivity;

import static androidx.core.content.ContextCompat.startActivity;

public class User implements Cloneable {

    final String USER = "User";

    private String name;
    private String surname;
    private String gender;
    private Date dateBirth;
    private int bodyWeight;
    private int height; // express in cm
    private String email;
    private String password;
    private FirebaseAuth firebaseAuth;

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

    public boolean signUpNewUser() {

        firebaseAuth = FirebaseAuth.getInstance();

        if (!firebaseAuth.fetchSignInMethodsForEmail(this.email).isSuccessful())
            return false;
        else {
            firebaseAuth.createUserWithEmailAndPassword(this.email, this.password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseDatabase.getInstance().getReference(USER)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(this);
                            }

                        }
                    });
        }

        return true;
    }
}
