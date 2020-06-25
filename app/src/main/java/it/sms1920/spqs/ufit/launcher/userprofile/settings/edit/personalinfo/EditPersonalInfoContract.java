package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.personalinfo;

import it.sms1920.spqs.ufit.model.firebase.database.User;

public interface EditPersonalInfoContract {
    interface View {
        void endActivity();

        void showName(String name);

        void showSurname(String surname);

        void showBirthDate(String birthDate);

        void showGender(Integer gender);

        void showCalendar();

        void setError(Presenter.InputError error);
    }

    interface Presenter {
        void onBackPressed();

        void onEditBirthDateClicked();

        void confirmEdit(String name, String surname, Integer gender, String birthDate);

        enum InputError {
            NAME_EMPTY,
            SURNAME_EMPTY
        }
    }
}
