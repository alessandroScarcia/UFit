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

import it.sms1920.spqs.ufit.contract.iProfile;
import it.sms1920.spqs.ufit.model.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.User;

import static it.sms1920.spqs.ufit.model.User.HEIGHT_FIELD;
import static it.sms1920.spqs.ufit.model.User.IMG_URL_FIELD;
import static it.sms1920.spqs.ufit.model.User.JPG;
import static it.sms1920.spqs.ufit.model.User.NAME_FIELD;
import static it.sms1920.spqs.ufit.model.User.PATH_STORAGE_PIC;
import static it.sms1920.spqs.ufit.model.User.SURNAME_FIELD;
import static it.sms1920.spqs.ufit.model.User.WEIGHT_FIELD;


public class ProfilePresenter implements iProfile.Presenter {

    private DatabaseReference database;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private iProfile.View view;

    public ProfilePresenter(iProfile.View view) {
        this.database = FirebaseDbSingleton.getDatabase().getReference(TABLE_USER);
        this.firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        this.view = view;
    }




    @Override
    public void onGenderChanged(User.Gender newGender) {
        /*database.child("gender").setValue(newGender);
        view.updateGender(newGender);*/
    }

    @Override
    public void onWeightChanged(int newWeight) {
         /*database.child("weight").setValue(newWeight);
        view.updateGender(newWeight);*/
    }


    @Override
    public void onPasswordChanged(String newPassword) {
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
                        } else {

                        }
                    }

                });
    }


    @Override
    public void onEmailChanged(String newEmail) {
        firebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        e.printStackTrace();
                    } catch (FirebaseAuthUserCollisionException e) {
                        e.printStackTrace();
                    } catch (FirebaseAuthInvalidUserException e) {
                        e.printStackTrace();
                    } catch (FirebaseAuthRecentLoginRequiredException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        });
    }

    @Override
    public void onNameChanged(String newName) {
         /*database.child("name").setValue(newName);
        view.updateGender(newName);*/
    }

    @Override
    public void onSurnameChanged(String newSurname) {
         /*database.child("surname").setValue(newSurname);
        view.updateGender(newSurname);*/
    }

    @Override
    public void onBirthDateChanged(Date newDate) {
        /*database.child("birthDate").setValue(newDate);
        view.updateGender(newDate);*/
    }

    @Override
    public void onPicProfileUploaded() {

    }

    @Override
    public void uploadPicOnStorage(Uri imageUri) {
        final String nameFile = FirebaseAuth.getInstance().getUid();

        final StorageReference riversRef = mStorageRef.child(PATH_STORAGE_PIC + nameFile + JPG);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.child(nameFile).child(IMG_URL_FIELD).setValue(uri.toString());
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

    @Override
    public void onPicProfileChanged() {
        view.choosePic();
    }

    //TODO da decidere dove vanno messe le costanti dei nomi dei percorsi e degi attributi delle tabelle


    @Override
    public void onUpdateInfo() {

        database.keepSynced(true);

        database.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(NAME_FIELD))
                            view.updateName(dataSnapshot.child(NAME_FIELD).getValue().toString());

                        if (dataSnapshot.hasChild(SURNAME_FIELD))
                            view.updateSurname(dataSnapshot.child(SURNAME_FIELD).getValue().toString());

                        view.updateEmail(firebaseUser.getEmail());
                        view.updatePassword();

                        if (dataSnapshot.hasChild(HEIGHT_FIELD))
                            view.updateHeight(Integer.parseInt(dataSnapshot.child(HEIGHT_FIELD).getValue().toString()));

                        if (dataSnapshot.hasChild(WEIGHT_FIELD))
                            view.updateWeight(Integer.parseInt(dataSnapshot.child(WEIGHT_FIELD).getValue().toString()));

                        if (dataSnapshot.hasChild(IMG_URL_FIELD))
                            view.updatePic(dataSnapshot.child(IMG_URL_FIELD).getValue().toString());
                /*if( dataSnapshot.hasChild(GENDER_FIELD))
                    view.updateGender(dataSnapshot.child(GENDER_FIELD).getValue().toString());
                if(dataSnapshot.hasChild(BIRTH_DATE_FIELD))
                    view.updateBirthDate(dataSnapshot.child(BIRTH_DATE_FIELD).getValue());*/

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onClickChangeProfileInfo() {
        view.startChangeProfileInfoFragment();
    }

    @Override
    public void onHeightChanged(int newHeight) {
         /*database.child("height").setValue(newHeight);
        view.updateGender(newHeight);*/
    }

    @Override
    public void onBecomeTrainer() {

    }
}