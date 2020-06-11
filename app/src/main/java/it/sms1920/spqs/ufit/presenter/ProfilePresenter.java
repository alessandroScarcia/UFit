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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Date;

import it.sms1920.spqs.ufit.contract.iProfile;
import it.sms1920.spqs.ufit.model.User;

public class ProfilePresenter implements iProfile.Presenter {
    private DatabaseReference database;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    iProfile.View view;

    public ProfilePresenter(iProfile.View view) {
        this.database = FirebaseDatabase.getInstance().getReference(TABLE_USER);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.view = view;
    }

    @Override
    public void uploadPicOnStorage(final Uri imageUri) {
        String nameFile = FirebaseAuth.getInstance().getUid();

        final StorageReference riversRef = mStorageRef.child("PicsProfile/" + nameFile);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String string = uri.toString();
                                database.child(FirebaseAuth.getInstance().getUid()).
                                        child("linkImgProfile").setValue(string);

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
                        }else{

                        }
                    }

                });
    }


    @Override
    public void onEmailChanged(String newEmail) {
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
    public void onNameChanged(String newName) {

    }

    @Override
    public void onSurnameChanged(String newSurname) {

    }

    @Override
    public void onHeightChanged(int newHeight, User.HeightUnit heightUnit) {

    }

    @Override
    public void onWeightChanged(int newWeight, User.WeightUnit weightUnit) {

    }

    @Override
    public void onBirthDateChanged(Date newDate) {

    }

    @Override
    public void onPicProfileUploaded() {
        view.choosePic();
    }

    @Override
    public void onPicProfileChanged() {

    }

    @Override
    public void onGenderChanged(User.Gender newGender) {

    }

    @Override
    public void onWeightChanged(int newWeight) {

    }

    @Override
    public void onUpdateRequest() {
        database.child(firebaseUser.getUid());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User userInfo = dataSnapshot.getValue(User.class);

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