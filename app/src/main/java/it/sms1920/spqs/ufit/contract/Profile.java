package it.sms1920.spqs.ufit.contract;

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

        void onUpdateRequest();
    }
}
