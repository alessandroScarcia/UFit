package it.sms1920.spqs.ufit.launcher;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;

import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.HOME;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PLANS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PROFILE;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PROFILE_SETTINGS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.SHOW_PLAN;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.STATS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.TRAINER;

public class LauncherPresenter implements LauncherContract.Presenter {

    private LauncherContract.View view;
    private FragType currentFragment = HOME;
    private User currentUser;
    private boolean isAnonymous = true;
    private boolean isTrainer = false;
    private boolean isLinked = false;

    public LauncherPresenter(LauncherContract.View view) {
        this.view = view;
        updateStatus();
    }


    @Override
    public void updateStatus() {
        if (FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser() != null) {
            isAnonymous = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser().isAnonymous();

            String uid = FirebaseAuthSingleton.getFirebaseAuth().getUid();

            Query query = FirebaseDbSingleton.getInstance().getReference()
                    .child("User")
                    .orderByKey()
                    .equalTo(uid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() == 0) {
                        isTrainer = false;
                        isLinked = false;
                    } else {

                        for (DataSnapshot child : snapshot.getChildren()) {
                            Log.d("TAG", "onDataChange: AOOOOOOOOOOOO" + child.getValue(User.class).getName());
                            currentUser = child.getValue(User.class);
                        }
                        isTrainer = currentUser.getRole();
                        isLinked = !currentUser.getLinkedUserId().isEmpty();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onHomeIconClicked() {
        view.insertHomeFragment();
        currentFragment = HOME;
    }

    @Override
    public void onPlansIconClicked() {
        view.insertPlansFragment();
        currentFragment = PLANS;
    }

    @Override
    public void onTrainerIconClicked() {
        view.insertTrainerFragment(isAnonymous, isTrainer, isLinked);
        currentFragment = TRAINER;
    }

    @Override
    public void onStatsIconClicked() {
        view.insertStatsFragment();
        currentFragment = STATS;
    }

    /**
     * Method called whenever user wants to access profile section.
     * Firstly is checked if user is logged in. If he is, profile information are shown.
     * Otherwise user must decide to Login or Register an account.
     */
    @Override
    public void onProfileIconClicked() {
        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        if (firebaseUser == null || firebaseUser.isAnonymous()) {
            view.insertChooseFragment();
        } else {
            view.insertProfileFragment();
            currentFragment = PROFILE;
        }
    }

    @Override
    public void onSearchIconClicked() {
        view.startSearchActivity();
    }

    @Override
    public void onShowPlanClicked() {
        currentFragment = SHOW_PLAN;
    }

    @Override
    public void onBackPressed() {
        switch (currentFragment) {
            case HOME:
                view.endActivity();
                break;
            case SHOW_PLAN:
                currentFragment = PLANS;
                view.insertPlansFragment();
                break;
            case PROFILE_SETTINGS:
                view.insertProfileFragment();
                break;
            default:
                currentFragment = HOME;
                view.insertHomeFragment();
                break;
        }
    }

    @Override
    public void onLogOutIconClicked() {
        view.resetActivity();
        FirebaseAuthSingleton.getFirebaseAuth().signOut();
        FirebaseAuthSingleton.getFirebaseAuth().signInAnonymously();
    }

    @Override
    public void onShowPlanClosed() {
        currentFragment = PLANS;
    }

    @Override
    public void onProfileSettingsClicked() {
        view.insertProfileSettingsFragment();
        currentFragment = PROFILE_SETTINGS;
    }

    @Override
    public void onEditIconClicked() {
        String id = view.getWorkoutId();
        view.startEditWorkoutActivity(id);
    }

//    @Override
//    public UserLinkingState onCheckUserLinkingRequested() {
//        UserLinkingState value;
//        if (currentUser == null) {
//            value = null;
//        } else {
//            if (!currentUser.getLinkedUserId().isEmpty()) {
//
//                if (currentUser.getRole()) value = TRAINER_LINKED;
//                else value = TRAINER_NO_LINKED;
//
//            } else {
//
//                if (currentUser.getRole()) value = USER_LINKED;
//                else value = USER_NO_LINKED;
//
//            }
//        }
//
//
//        return value;
//    }
}
