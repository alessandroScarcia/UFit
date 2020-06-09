package it.sms1920.spqs.ufit.contract;

import android.net.Uri;

import java.util.Date;

import it.sms1920.spqs.ufit.model.User;

public interface Profile {
    interface View {
        void updateInfo(User user);

        void choosePic();
    }

    interface Presenter {

        String TABLE_USER = "User";

        void onChangePassword(String newPassword);//chiedi la vecchia password

        void onChangeEmail(String newEmail);//chiedi la vecchia email

        void onChangeName(String newName);

        void onChangeSurname(String newSurname);

        void onChangeHeight(int newHeight, User.HeightUnit heightUnit);

        void onChangeWeight(int newWeight, User.WeightUnit weightUnit);

        void onChangeBirthDate(Date newDate);

        void onUploadPicProfile();

        void uploadPicOnStorage(final Uri imageUri);

        void onChangePicProfile();

        void onChangeGender(User.Gender newGender);

        void onChangeWeight(int newWeight);

        void onUpdateRequest();

        void onBecomeTrainer();
    }
}
