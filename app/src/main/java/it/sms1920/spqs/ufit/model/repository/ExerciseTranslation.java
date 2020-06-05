package it.sms1920.spqs.ufit.model.repository;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"exerciseTransId", "codLanguage"},
        foreignKeys = @ForeignKey(
                entity = Exercise.class,
                parentColumns = "exerciseId",
                childColumns = "exerciseTransId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
)
public class ExerciseTranslation {
    private long exerciseTransId;
    @NonNull
    private String codLanguage;
    @NonNull
    private String name;
    @NonNull
    private String description;

    public String getCodLanguage() {
        return codLanguage;
    }

    public void setCodLanguage(String codLanguage) {
        this.codLanguage = codLanguage;
    }

    public long getExerciseTransId() {
        return exerciseTransId;
    }

    public void setExerciseTransId(long exerciseTransId) {
        this.exerciseTransId = exerciseTransId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
