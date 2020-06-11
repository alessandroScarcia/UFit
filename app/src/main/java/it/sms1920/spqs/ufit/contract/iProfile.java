package it.sms1920.spqs.ufit.contract;

import android.net.Uri;

import java.util.Date;

import it.sms1920.spqs.ufit.model.User;

public interface iProfile {
    interface View {
        void updateInfo(User user);

        void choosePic();
    }

    interface Presenter {

        String TABLE_USER = "User";

        void onPasswordChanged(String newPassword);//chiedi la vecchia password

        void onEmailChanged(String newEmail);//chiedi la vecchia email

        void onNameChanged(String newName);

        void onSurnameChanged(String newSurname);

        void onHeightChanged(int newHeight, User.HeightUnit heightUnit);

        void onWeightChanged(int newWeight, User.WeightUnit weightUnit);

        void onBirthDateChanged(Date newDate);

        void onPicProfileUploaded();

        void uploadPicOnStorage(final Uri imageUri);

        void onPicProfileChanged();

        void onGenderChanged(User.Gender newGender);

        void onWeightChanged(int newWeight);

        void onUpdateRequest();

        void onBecomeTrainer();
    }
}
