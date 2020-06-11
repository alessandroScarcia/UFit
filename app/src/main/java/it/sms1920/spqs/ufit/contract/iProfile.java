package it.sms1920.spqs.ufit.contract;

import android.net.Uri;

import java.util.Date;

import it.sms1920.spqs.ufit.model.User;

public interface Profile {
    interface View {

        void updatePic(String urlImage);

        void updateName(String name);

        void updateEmail(String email);

        void updateHeight(int height);

        void updateWeight(int weight);

        void updateSurname(String surname);

        void updatePassword();

        void choosePic();
    }

    interface Presenter {

        String TABLE_USER = "User";

        void onUpdatePassword(String newPassword);//chiedi la vecchia password

        void onUpdateEmail(String newEmail);//chiedi la vecchia email

        void onUpdateName(String newName);

        void onUpdateSurname(String newSurname);

        void onUpdateBirthDate(Date newDate);

        void onUpdatePicStorage(Uri imageUri);

        void onUpdatePic();

        void onUpdateGender(User.Gender newGender);

        void onUpdateWeight(int newWeight);

        void onUpdateInfo();

        void onUpdateHeight(int newHeight);

        void onBecomeTrainer();
    }
}
