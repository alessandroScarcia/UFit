package it.sms1920.spqs.ufit.model;

import androidx.annotation.Nullable;

import java.util.List;

public class ExerciseTranslation implements Comparable<ExerciseTranslation> {
    public static final String CHILD_NAME = "ExerciseTranslation";

    private String codLanguage;
    private String name;
    private String description;
    private List<String> muscleList;
    private String exerciseId;

    public String getName() {
        return name;
    }

    public String getCodLanguage() {
        return codLanguage;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getMuscleList() {
        return muscleList;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    @Override
    public int compareTo(ExerciseTranslation o) {
        return this.exerciseId.compareTo(o.getExerciseId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseTranslation that = (ExerciseTranslation) o;

        return exerciseId.equals(that.exerciseId);
    }

    @Override
    public int hashCode() {
        return exerciseId.hashCode();
    }
}
