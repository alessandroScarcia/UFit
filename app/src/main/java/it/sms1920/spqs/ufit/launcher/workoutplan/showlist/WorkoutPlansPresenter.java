package it.sms1920.spqs.ufit.launcher.workoutplan.showlist;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;

public class WorkoutPlansPresenter implements WorkoutPlansContract.Presenter {
    private final WorkoutPlansContract.View view;
    private User me;

    public WorkoutPlansPresenter(final WorkoutPlansContract.View view) {
        this.view = view;

        FirebaseDbSingleton.getInstance().getReference().child("User").orderByKey().equalTo(FirebaseAuthSingleton.getFirebaseAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    me = item.getValue(User.class);
                    onTabSelectedAtPosition(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onTabSelectedAtPosition(int position) {
        if (position == 0) {
            view.showPersonalWorkoutPlans();
        } else if (position == 1) {
            if (me != null) {
                view.showTrainerWorkoutPlans(me.getRole());
            } else {
                view.showTrainerWorkoutPlans(false);
            }
        } else {
            throw new IllegalArgumentException("Invalid value for argument position.");
        }
    }

    @Override
    public void onAddClicked() {
        view.addNewPlan();
    }
}
