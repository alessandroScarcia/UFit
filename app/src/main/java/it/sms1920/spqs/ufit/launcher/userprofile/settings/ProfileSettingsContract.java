package it.sms1920.spqs.ufit.launcher.userprofile.settings;

import android.net.Uri;

/**
 * Contract for ProfileSettings functionality.
 */
public interface ProfileSettingsContract {
    interface View {
        void chooseImage();

        void startEditPersonalInfoActivity();

        void startEditEmailActivity();

        void startEditPasswordActivity();

        void showConfirmDeleteProfileDialog();

        void setProfileImage(String imageUrl);

        void reauthenticate();

        void insertChooseFragment();

        void updateRole(boolean isChecked);
    }

    interface Presenter {
        void onEditProfileImageClicked();

        void onEditPersonalInfoClicked();

        void onEditEmailClicked();

        void onEditPasswordClicked();

        void onDeleteProfileClicked();

        void onEditRoleCheckedChanged(boolean isChecked);

        void onResultImageUri(Uri imageUri);

        void deleteProfile();
    }
}
