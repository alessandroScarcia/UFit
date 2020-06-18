package it.sms1920.spqs.ufit.launcher.workoutplan.create;

public interface iExerciseDialog {
    interface View {
        void setExerciseName(String name);
    }

    interface Presenter {
        void onExerciseNameRequested(String exerciseId);
        String getExerciseName();
    }
}
