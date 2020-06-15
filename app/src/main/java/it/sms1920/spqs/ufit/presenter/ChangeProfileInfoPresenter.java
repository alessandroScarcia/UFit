package it.sms1920.spqs.ufit.presenter;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import it.sms1920.spqs.ufit.contract.iChangeProfileInfo;
import it.sms1920.spqs.ufit.model.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.FirebaseDbSingleton;

import static it.sms1920.spqs.ufit.model.User.BIRTH_DATE_FIELD;
import static it.sms1920.spqs.ufit.model.User.GENDER_FIELD;
import static it.sms1920.spqs.ufit.model.User.HEIGHT_FIELD;
import static it.sms1920.spqs.ufit.model.User.IMG_URL_FIELD;
import static it.sms1920.spqs.ufit.model.User.JPG;
import static it.sms1920.spqs.ufit.model.User.NAME_FIELD;
import static it.sms1920.spqs.ufit.model.User.PATH_STORAGE_PIC;
import static it.sms1920.spqs.ufit.model.User.SURNAME_FIELD;
import static it.sms1920.spqs.ufit.model.User.WEIGHT_FIELD;

public class ChangeProfileInfoPresenter implements iChangeProfileInfo.Presenter {
    private iChangeProfileInfo.View view;

    private DatabaseReference database;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public ChangeProfileInfoPresenter(iChangeProfileInfo.View view) {
        this.database = FirebaseDbSingleton.getDatabase().getReference(TABLE_USER);
        firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        this.view = view;
    }


    @Override
    public void onShowAllProfileInfo() {
        view.showAllProfileInfo();
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
    public void uploadPicOnStorage(Uri imageUri) {
        final String nameFile = firebaseUser.getUid();

        final StorageReference riversRef = mStorageRef.child(PATH_STORAGE_PIC + nameFile + JPG);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.child(nameFile).child(IMG_URL_FIELD).setValue(uri.toString());
                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });

        view.updatePic(imageUri.toString());
    }

    @Override
    public void onPicProfileChanged() {
        view.choosePic();
    }



    @Override
    public void onUpdateInfo() {

        database.keepSynced(true);

        database = database.child(firebaseUser.getUid());

        database.child(NAME_FIELD).setValue(view.updateName());
        database.child(SURNAME_FIELD).setValue(view.updateSurname());
        database.child(BIRTH_DATE_FIELD).setValue(view.updateBirthDate());
        database.child(HEIGHT_FIELD).setValue(view.updateHeight());
        database.child(WEIGHT_FIELD).setValue(view.updateWeight());
        database.child(GENDER_FIELD).setValue(view.updateGender());

    }

}
