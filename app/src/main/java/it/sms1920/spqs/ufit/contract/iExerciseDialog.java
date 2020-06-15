package it.sms1920.spqs.ufit.contract;

public interface iExerciseDialog {
    interface View {
        void setExerciseName(String name);
    }

    interface Presenter {
        void onExerciseNameRequested(String exerciseId);
        String getExerciseName();
    }
}
