package it.sms1920.spqs.ufit.launcher;

import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.HOME;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PLANS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PROFILE;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PROFILE_SETTINGS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.SHOW_PLAN;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.STATS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.TRAINER;

public class LauncherPresenter implements LauncherContract.Presenter {
    private static final String TAG = LauncherPresenter.class.getCanonicalName();

    private LauncherContract.View view;
    private FragType currentFragment = HOME;

    public LauncherPresenter(LauncherContract.View view) {
        this.view = view;
    }

    @Override
    public void onHomeIconClicked() {
        if (currentFragment != HOME) {
            view.insertHomeFragment();
            currentFragment = HOME;
        }
    }

    @Override
    public void onPlansIconClicked() {
        if (currentFragment != PLANS) {
            view.insertPlansFragment();
            currentFragment = PLANS;
        }
    }

    @Override
    public void onTrainerIconClicked() {
        if (currentFragment != TRAINER) {
            view.insertTrainerFragment(FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser().isAnonymous());
            currentFragment = TRAINER;
        }
    }

    @Override
    public void onStatsIconClicked() {
        if (currentFragment != STATS) {
            view.insertStatsFragment();
            currentFragment = STATS;
        }
    }

    /**
     * Method called whenever user wants to access profile section.
     * Firstly is checked if user is logged in. If he is, profile information are shown.
     * Otherwise user must decide to Login or Register an account.
     */
    @Override
    public void onProfileIconClicked() {
        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        if (firebaseUser == null) {
            throw new IllegalStateException(TAG + " user should be logged anonymously at least.");
        }

        if (firebaseUser.isAnonymous()) {
            currentFragment = FragType.CHOOSE;
            view.insertChooseFragment();
        } else {
            if (currentFragment != PROFILE) {
                view.insertProfileFragment();
                currentFragment = PROFILE;
            }
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

    @Override
    public void onTimerIconClicked() {
        view.startTimer();
    }

}
