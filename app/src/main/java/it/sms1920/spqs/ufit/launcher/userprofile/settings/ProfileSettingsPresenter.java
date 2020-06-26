package it.sms1920.spqs.ufit.launcher.userprofile.settings;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;

/**
 * Presenter for View 'ProfileSettingsFragment.java'.
 */
public class ProfileSettingsPresenter implements ProfileSettingsContract.Presenter {
    private static final String TAG = ProfileSettingsPresenter.class.getCanonicalName();

    private ProfileSettingsContract.View view;

    private FirebaseUser user;
    private DatabaseReference userInfoRef;

    public ProfileSettingsPresenter(ProfileSettingsContract.View view) {
        this.view = view;

        user = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        if (user == null || user.isAnonymous()) {
            throw new IllegalStateException(TAG + " user is not logged! Profile Settings should not be accessible.");
        }

        userInfoRef = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME).child(user.getUid());

        fetchProfileImage();
    }

    /**
     * Extract User's Profile Image from Firebase Realtime Database and asks view to visualize it.
     */
    private void fetchProfileImage() {
        userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "userInfo:onDataChange");

                User userInfo = dataSnapshot.getValue(User.class);

                if (userInfo != null) {
                    view.setProfileImage(userInfo.getImageUrl());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "userInfo:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onEditProfileImageClicked() {
        view.chooseImage();
    }

    @Override
    public void onEditPersonalInfoClicked() {
        view.startEditPersonalInfoActivity();
    }

    @Override
    public void onEditEmailClicked() {
        view.startEditEmailActivity();
    }

    @Override
    public void onEditPasswordClicked() {
        view.startEditPasswordActivity();
    }

    @Override
    public void onDeleteProfileClicked() {
        view.showConfirmDeleteProfileDialog();
    }

    @Override
    public void onEditRoleCheckedChanged(boolean isChecked) {
        userInfoRef.child(User.FIELD_ROLE).setValue(isChecked);
    }

    @Override
    public void onResultImageUri(Uri imageUri) {
        String fileName = user.getUid();

        final StorageReference profileImageRef =
                FirebaseStorage.getInstance()
                        .getReference(User.PATH_STORAGE_PIC + fileName + User.JPG);

        profileImageRef.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "putFile:success");

                            profileImageRef.getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful() && task.getResult() != null) {
                                                Log.d(TAG, "getDownloadUrl:success");

                                                String imageUrl = task.getResult().toString();
                                                userInfoRef.child(User.FIELD_IMAGE_URL).setValue(imageUrl);

                                                view.setProfileImage(imageUrl);
                                            } else {
                                                Log.w(TAG, "getDownloadUrl:failure", task.getException());
                                            }
                                        }
                                    });
                        } else {
                            Log.w(TAG, "putFile:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void deleteProfile() {
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "deleteProfile:success");
                    view.insertChooseFragment();
                } else {
                    Log.w(TAG, "deleteProfile:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthRecentLoginRequiredException) {
                        view.reauthenticate();
                    }
                }
            }
        });
    }
}
