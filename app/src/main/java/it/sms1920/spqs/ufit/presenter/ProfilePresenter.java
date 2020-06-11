package it.sms1920.spqs.ufit.presenter;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import it.sms1920.spqs.ufit.contract.Profile;
import it.sms1920.spqs.ufit.model.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.User;


public class ProfilePresenter implements Profile.Presenter {

    private DatabaseReference database;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    Profile.View view;

    public ProfilePresenter(Profile.View view) {
        this.database = FirebaseDbSingleton.getDatabase().getReference(TABLE_USER);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.view = view;
    }

    @Override
    public void onUpdateGender(User.Gender newGender) {
        /*database.child("gender").setValue(newGender);
        view.updateGender(newGender);*/
    }

    @Override
    public void onUpdateWeight(int newWeight) {
         /*database.child("weight").setValue(newWeight);
        view.updateGender(newWeight);*/
    }

    @Override
    public void onUpdatePassword(String newPassword) {
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
    public void onUpdateEmail(String newEmail) {
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
    public void onUpdateName(String newName) {
         /*database.child("name").setValue(newName);
        view.updateGender(newName);*/
    }

    @Override
    public void onUpdateSurname(String newSurname) {
         /*database.child("surname").setValue(newSurname);
        view.updateGender(newSurname);*/
    }

    @Override
    public void onUpdateBirthDate(Date newDate) {

    }

    @Override
    public void onUpdatePicStorage(Uri imageUri) {
        final String nameFile = FirebaseAuth.getInstance().getUid();

        final StorageReference riversRef = mStorageRef.child("PicsProfile/" + nameFile+".jpg");

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.child(nameFile).child("urlImage").setValue(uri.toString());
                                view.updatePic(uri.toString());//TODO ci vorebbe una snack bar quando viene invocato questo metodo
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });


    }

    //TODO da decidere dove vanno messe le costanti dei nomi dei percorsi e degi attributi delle tabelle
    @Override
    public void onUpdatePic() {
        view.choosePic();
    }



    @Override
    public void onUpdateInfo() {

        database.keepSynced(true);

        database.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("name"))
                    view.updateName(dataSnapshot.child("name").getValue().toString());

                if(dataSnapshot.hasChild("surname"))
                    view.updateSurname(dataSnapshot.child("surname").getValue().toString());

                view.updateEmail(firebaseUser.getEmail());
                view.updatePassword();

                if(dataSnapshot.hasChild("height"))
                    view.updateHeight(Integer.parseInt(dataSnapshot.child("height").getValue().toString()));

                if(dataSnapshot.hasChild("weight"))
                    view.updateWeight(Integer.parseInt(dataSnapshot.child("weight").getValue().toString()));

                if(dataSnapshot.hasChild("urlImage"))
                    view.updatePic(dataSnapshot.child("urlImage").getValue().toString());
                /*if( dataSnapshot.hasChild("gender"))
                    view.updateGender(dataSnapshot.child("gender").getValue().toString());
                if(dataSnapshot.hasChild("birthDate"))
                    view.updateBirthDate(dataSnapshot.child("birthDate").getValue());*/

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onUpdateHeight(int newHeight) {
         /*database.child("height").setValue(newHeight);
        view.updateGender(newHeight);*/
    }

    @Override
    public void onBecomeTrainer() {

    }
}