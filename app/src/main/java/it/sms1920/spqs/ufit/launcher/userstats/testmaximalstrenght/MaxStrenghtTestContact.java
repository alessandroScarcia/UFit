package it.sms1920.spqs.ufit.launcher.userstats.testmaximalstrenght;

public interface MaxStrenghtTestContact {

    interface View {

        void openDialog();
    }


    interface Presenter{

        int checkDataToAdapter(int checkIdRadioButton, String toString);

        void onSelectRepsClicked();
    }
}
