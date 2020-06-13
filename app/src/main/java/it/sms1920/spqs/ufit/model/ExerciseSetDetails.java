package it.sms1920.spqs.ufit.model;

import androidx.annotation.NonNull;

import java.util.List;

public class ExerciseSetDetails {
    private int exerciseId;
    private List<ExerciseSetItem> exerciseSetItems;

    public int getExerciseId() {
        return exerciseId;
    }

    public List<ExerciseSetItem> getExerciseSetItems() {
        return exerciseSetItems;
    }

    @NonNull
    @Override
    public String toString() {
        return "ExerciseSetDetails{" +
                "exerciseId=" + exerciseId +
                ", exerciseSetItems=" + exerciseSetItems +
                '}';
    }
}
