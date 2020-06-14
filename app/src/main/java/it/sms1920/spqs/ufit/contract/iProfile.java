package it.sms1920.spqs.ufit.contract;

import it.sms1920.spqs.ufit.model.User;

public interface iProfile {

    interface View {

        void showImagePicture(String urlImage);

        void showName(String name);

        void showEmail(String email);

        void showHeight(int height);

        void showWeight(int weight);

        void showSurname(String surname);

        void showGender(User.Gender gender);

        void showBirthDate(String birthDate);

        void startChangeProfileInfoFragment();
    }

    interface Presenter {

        String TABLE_USER = "User";


        void onShowProfileInfo();

        void onClickChangeProfileInfo();


    }
}
