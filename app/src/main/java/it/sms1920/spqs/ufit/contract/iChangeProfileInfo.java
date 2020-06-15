package it.sms1920.spqs.ufit.contract;

import android.net.Uri;

public interface iChangeProfileInfo {

    interface View {
        void showAllProfileInfo();

        void choosePic();

        void updatePic(final String urlImage);

        String updateName();

        String updateSurname();

        int updateHeight();

        int updateWeight();

        String updateBirthDate();

        String updateGender();


    }

    interface Presenter {
        void onShowAllProfileInfo();

        String TABLE_USER = "User";

        void onPasswordChanged(String newPassword);

        void onUpdateInfo();

        void onEmailChanged(String newEmail);


        void uploadPicOnStorage(final Uri imageUri);

        void onPicProfileChanged();



    }
}
