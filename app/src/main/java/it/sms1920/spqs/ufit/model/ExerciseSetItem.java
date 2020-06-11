package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

public class ExerciseSetItem {
    private int reps;
    private float load;

    public int getReps() {
        return reps;
    }

    public float getLoad() {
        return load;
    }

    @NonNull
    @Override
    public String toString() {
        return "ExerciseSetItem{" +
                "reps=" + reps +
                ", load=" + load +
                '}';
    }
}
