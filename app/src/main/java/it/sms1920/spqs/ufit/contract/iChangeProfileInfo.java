package it.sms1920.spqs.ufit.contract;

import android.net.Uri;

import it.sms1920.spqs.ufit.model.User;

public interface iChangeProfileInfo {

    interface View {
        void showAllProfileInfo();

        void choosePic();

        void updatePic(final String urlImage);

    }

    interface Presenter {
        void onShowAllProfileInfo();

        String TABLE_USER = "User";

        void onPasswordChanged(String newPassword);

        void onNameChanged(String newName);

        void onUpdateInfo();

        void onEmailChanged(String newEmail);

        void onHeightChanged(int newHeight);

        void onSurnameChanged(String newSurname);

        void onBirthDateChanged(String newDate);

        void uploadPicOnStorage(final Uri imageUri);

        void onPicProfileChanged();

        void onGenderChanged(User.Gender newGender);

        void onWeightChanged(int newWeight);

        void onBecomeTrainer();
    }
}
