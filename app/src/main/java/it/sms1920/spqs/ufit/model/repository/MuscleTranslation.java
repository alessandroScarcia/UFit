package it.sms1920.spqs.ufit.model.repository;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"muscleTransId", "codLanguage"},
        foreignKeys = @ForeignKey(
                entity = Muscle.class,
                parentColumns = "muscleId",
                childColumns = "muscleTransId",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        )
)
public class MuscleTranslation {
    private long muscleTransId;
    @NonNull
    private String codLanguage;
    @NonNull
    private String name;

    public long getMuscleTransId() {
        return muscleTransId;
    }

    public void setMuscleTransId(long muscleTransId) {
        this.muscleTransId = muscleTransId;
    }

    public String getCodLanguage() {
        return codLanguage;
    }

    public void setCodLanguage(String codLanguage) {
        this.codLanguage = codLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
