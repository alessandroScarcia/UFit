package it.sms1920.spqs.ufit.contract;

public interface iCreatingWorkout {

    interface View {
        void back();

        void startSearchExerciseForWorkout(int requestCode);
    }

    interface Presenter {
        void onBackPressed();

        void onAddIconClicked();
    }

}
