package it.sms1920.spqs.ufit.presenter;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Date;

import it.sms1920.spqs.ufit.contract.Profile;
import it.sms1920.spqs.ufit.model.User;

public class ProfilePresenter implements Profile.Presenter {
    private DatabaseReference database;
    private FirebaseUser firebaseUser;
    Profile.View view;

    public ProfilePresenter(Profile.View view) {
        this.database = FirebaseDatabase.getInstance().getReference(TABLE_USER);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.view = view;
    }


    @Override
    public void onSignOut() {
        FirebaseAuth.getInstance().signOut();
        view.resetLauncherActivity();
    }

    @Override
    public void onChangePassword(String newPassword) {
        firebaseUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                e.printStackTrace();
                            } catch (FirebaseAuthInvalidUserException e) {
                                e.printStackTrace();
                            } catch (FirebaseAuthRecentLoginRequiredException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{

                        }
                    }

                });
    }


    @Override
    public void onChangeEmail(String newEmail) {
        firebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        e.printStackTrace();
                    }catch(FirebaseAuthUserCollisionException e){
                        e.printStackTrace();
                    }catch(FirebaseAuthInvalidUserException e){
                        e.printStackTrace();
                    }catch(FirebaseAuthRecentLoginRequiredException e){
                        e.printStackTrace();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{

                }
            }
        });
    }

    @Override
    public void onChangeName(String newName) {

    }

    @Override
    public void onChangeSurname(String newSurname) {

    }

    @Override
    public void onChangeHeight(int newHeight, User.HeightUnit heightUnit) {

    }

    @Override
    public void onChangeWeight(int newWeight, User.WeightUnit weightUnit) {

    }

    @Override
    public void onChangeBirthDate(Date newDate) {

    }

    @Override
    public void onUploadPicProfile() {

    }

    @Override
    public void onChangeGender(User.Gender newGender) {

    }

    @Override
    public void onChangeWeight(int newWeight) {

    }

    @Override
    public void onUpdateRequest() {
        database.child(firebaseUser.getUid());
        Log.i("Pippo",firebaseUser.getUid());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("pippo","steng a qua");
                User userInfo = dataSnapshot.getValue(User.class);

                if(userInfo!=null)
                   Log.i("pippo",userInfo.getName() + "pippo");
                else
                    Log.i("pippo","è nullo");

                view.updateInfo(userInfo);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBecomeTrainer() {

    }
}