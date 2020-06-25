package it.sms1920.spqs.ufit.launcher.userprofile.show;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;


public class ProfilePresenter implements ProfileContract.Presenter {

    private DatabaseReference database;
    private FirebaseUser firebaseUser;

    private ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view) {
        this.database = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME);
        this.firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        this.view = view;

        showProfileInfo();
    }

    public void showProfileInfo() {
        database.keepSynced(true);

        database.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user != null) {
                            if (user.getImageUrl() != null) {
                                view.showProfileImage(user.getImageUrl());
                            }

                            if (user.getName() != null && user.getSurname() != null) {
                                view.showNameSurname(user.getName(), user.getSurname());
                                view.hideNoInfoAvailable();
                            }

                            if (user.getGender() != null) {
                                view.showGender(user.getGender());
                                view.hideNoInfoAvailable();
                            }

                            if (user.getBirthDate() != null) {
                                view.showBirthDate(user.getBirthDate());
                                view.hideNoInfoAvailable();
                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}