package it.sms1920.spqs.ufit.launcher.userprofile.show;

public interface ProfileContract {

    interface View {
        void showProfileImage(String urlImage);

        void showNameSurname(String name, String surname);

        void showGender(Integer gender);

        void showBirthDate(String birthDate);

        void hideNoInfoAvailable();
    }

    interface Presenter {
    }
}
