package it.sms1920.spqs.ufit.contract;

import java.util.Date;

import it.sms1920.spqs.ufit.model.User;

public interface Profile {
    interface View {
        void resetLauncherActivity();

        void updateInfo(User user);
    }

    interface Presenter {

        String TABLE_USER = "User";

        void onSignOut();//chiedere se è sicuro di voler uscire

        void onChangePassword(String newPassword);//chiedi la vecchia password

        void onChangeEmail(String newEmail);//chiedi la vecchia email

        void onChangeName(String newName);

        void onChangeSurname(String newSurname);

        void onChangeHeight(int newHeight, User.HeightUnit heightUnit);

        void onChangeWeight(int newWeight, User.WeightUnit weightUnit);

        void onChangeBirthDate(Date newDate);

        void onUploadPicProfile();

        void onChangeGender(User.Gender newGender);

        void onChangeWeight(int newWeight);

        void onUpdateRequest();

        void onBecomeTrainer();
    }
}
