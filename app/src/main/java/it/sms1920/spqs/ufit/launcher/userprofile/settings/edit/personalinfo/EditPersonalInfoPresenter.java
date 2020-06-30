package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.personalinfo;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;

import static it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.personalinfo.EditPersonalInfoContract.Presenter.InputError.NAME_EMPTY;
import static it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.personalinfo.EditPersonalInfoContract.Presenter.InputError.SURNAME_EMPTY;

public class EditPersonalInfoPresenter implements EditPersonalInfoContract.Presenter {
    private static final String TAG = EditPersonalInfoPresenter.class.getCanonicalName();

    private final EditPersonalInfoContract.View view;

    private FirebaseUser user;
    private DatabaseReference userInfoRef;

    public EditPersonalInfoPresenter(EditPersonalInfoContract.View view) {
        this.view = view;

        user = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        if (user == null || user.isAnonymous()) {
            throw new IllegalStateException(TAG + " user is not logged! Profile Settings should not be accessible.");
        }

        userInfoRef = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME).child(user.getUid());

        fetchUserInfo();
    }

    private void fetchUserInfo() {
        userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "fetchUserInfo:onDataChange");

                User userInfo = dataSnapshot.getValue(User.class);

                if (userInfo != null) {
                    if (userInfo.getName() != null) {
                        view.showName(userInfo.getName());
                    }

                    if (userInfo.getSurname() != null) {
                        view.showSurname(userInfo.getSurname());
                    }

                    if (userInfo.getGender() != null) {
                        view.showGender(userInfo.getGender());
                    }

                    if (userInfo.getBirthDate() != null) {
                        view.showBirthDate(userInfo.getBirthDate());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "fetchUserInfo:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        view.endActivity();
    }

    @Override
    public void onEditBirthDateClicked() {
        view.showCalendar();
    }

    @Override
    public void confirmEdit(String name, String surname, Integer gender, String birthDate) {

        if (!name.isEmpty()) {
            userInfoRef.child(User.FIELD_NAME).setValue(name);
        }

        if (!surname.isEmpty()) {
            userInfoRef.child(User.FIELD_SURNAME).setValue(surname);
        }

        if (gender != -1) {
            userInfoRef.child(User.FIELD_GENDER).setValue(gender);
        }

        if (!birthDate.isEmpty()) {
            userInfoRef.child(User.FIELD_BIRTH_DATE).setValue(birthDate);
        }

        view.endActivity();

    }

    private boolean check(String name, String surname) {
        boolean checkName = false;
        boolean checkSurname = false;

        if (TextUtils.isEmpty(name)) {
            view.setError(NAME_EMPTY);
        } else {
            checkName = true;
        }

        if (TextUtils.isEmpty(surname)) {
            view.setError(SURNAME_EMPTY);
        } else {
            checkSurname = true;
        }

        return (checkName && checkSurname);
    }
}
