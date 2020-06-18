package it.sms1920.spqs.ufit.launcher.userprofile.edit;

import android.net.Uri;

public interface ChangeProfileInfoContract {

    interface View {
        void showAllProfileInfo();

        void choosePic();

        void updatePic(final String urlImage);

        void updateEmail(String newEmail);

        void updateName(String name);

        void updateSurname(String surname);

        void updateHeight(int height);

        void updateWeight(int weight);

        void updateBirthDate(String date);

        void updateGender(String gender);

    }

    interface Presenter {
        void onShowAllProfileInfo();

        String TABLE_USER = "User";

        void onPasswordChanged(String currentPassword, String newPassword);

        void onUpdateInfo();

        void onEmailChanged(String currentPassword, String newEmail);

        void uploadPicOnStorage(final Uri imageUri);

        void onPicProfileChanged();

        void onDeleteProfile(String currentPassword);


    }
}
