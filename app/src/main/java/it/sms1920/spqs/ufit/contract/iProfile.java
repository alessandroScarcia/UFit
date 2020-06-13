package it.sms1920.spqs.ufit.contract;

import android.net.Uri;

import java.util.Date;

import it.sms1920.spqs.ufit.model.User;

public interface iProfile {

    interface View {
        void updatePic(String urlImage);

        void updateName(String name);

        void updateEmail(String email);

        void updateHeight(int height);

        void updateWeight(int weight);

        void updateSurname(String surname);

        void updatePassword();

        void choosePic();

        void startChangeProfileInfoFragment();
    }

    interface Presenter {

        String TABLE_USER = "User";

        void onPasswordChanged(String newPassword);//chiedi la vecchia password

        void onEmailChanged(String newEmail);//chiedi la vecchia email

        void onNameChanged(String newName);

        void onHeightChanged(int newHeight);

        void onSurnameChanged(String newSurname);

        void onBirthDateChanged(Date newDate);

        void onPicProfileUploaded();

        void uploadPicOnStorage(final Uri imageUri);

        void onPicProfileChanged();

        void onGenderChanged(User.Gender newGender);

        void onWeightChanged(int newWeight);

        void onBecomeTrainer();

        void onUpdateInfo();

        void onClickChangeProfileInfo();


    }
}
