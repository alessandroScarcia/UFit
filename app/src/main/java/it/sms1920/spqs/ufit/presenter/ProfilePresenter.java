package it.sms1920.spqs.ufit.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.sms1920.spqs.ufit.contract.iProfile;
import it.sms1920.spqs.ufit.model.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.FirebaseDbSingleton;

import static it.sms1920.spqs.ufit.model.User.BIRTH_DATE_FIELD;
import static it.sms1920.spqs.ufit.model.User.GENDER_FIELD;
import static it.sms1920.spqs.ufit.model.User.HEIGHT_FIELD;
import static it.sms1920.spqs.ufit.model.User.IMG_URL_FIELD;
import static it.sms1920.spqs.ufit.model.User.NAME_FIELD;
import static it.sms1920.spqs.ufit.model.User.SURNAME_FIELD;
import static it.sms1920.spqs.ufit.model.User.WEIGHT_FIELD;


public class ProfilePresenter implements iProfile.Presenter {

    private DatabaseReference database;
    private FirebaseUser firebaseUser;

    private iProfile.View view;

    public ProfilePresenter(iProfile.View view) {
        this.database = FirebaseDbSingleton.getDatabase().getReference(TABLE_USER);
        this.firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        this.view = view;
    }


    @Override
    public void onShowProfileInfo() {
        database.keepSynced(true);

        database.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(NAME_FIELD))
                            view.showName(dataSnapshot.child(NAME_FIELD).getValue().toString());

                        if (dataSnapshot.hasChild(SURNAME_FIELD))
                            view.showSurname(dataSnapshot.child(SURNAME_FIELD).getValue().toString());

                        view.showEmail(firebaseUser.getEmail());

                        if (dataSnapshot.hasChild(HEIGHT_FIELD))
                            view.showHeight(Integer.parseInt(dataSnapshot.child(HEIGHT_FIELD).getValue().toString()));

                        if (dataSnapshot.hasChild(WEIGHT_FIELD))
                            view.showWeight(Integer.parseInt(dataSnapshot.child(WEIGHT_FIELD).getValue().toString()));

                        if (dataSnapshot.hasChild(IMG_URL_FIELD))
                            view.showImagePicture(dataSnapshot.child(IMG_URL_FIELD).getValue().toString());

                        if (dataSnapshot.hasChild(GENDER_FIELD)) {
                            Log.i("pippo", "quaddd");
                            view.showGender(dataSnapshot.child(GENDER_FIELD).getValue().toString());
                        }

                        if (dataSnapshot.hasChild(BIRTH_DATE_FIELD))
                            view.showBirthDate(dataSnapshot.child(BIRTH_DATE_FIELD).getValue().toString());

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


}