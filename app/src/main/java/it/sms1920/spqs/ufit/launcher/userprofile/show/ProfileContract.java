package it.sms1920.spqs.ufit.launcher.userprofile.show;

public interface ProfileContract {

    interface View {

        void showImagePicture(String urlImage);

        void showName(String name);

        void showEmail(String email);

        void showHeight(int height);

        void showWeight(int weight);

        void showSurname(String surname);

        void showGender(String gender);

        void showBirthDate(String birthDate);

        void startChangeProfileInfoFragment();
    }

    interface Presenter {

        String TABLE_USER = "User";


        void onShowProfileInfo();

        void onClickChangeProfileInfo();


    }
}
