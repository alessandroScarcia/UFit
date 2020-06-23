package it.sms1920.spqs.ufit.launcher;

import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.HOME;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PLANS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.PROFILE;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.SHOW_PLAN;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.STATS;
import static it.sms1920.spqs.ufit.launcher.LauncherContract.View.FragType.TRAINER;

public class LauncherPresenter implements LauncherContract.Presenter {

    private LauncherContract.View view;
    private FragType currentFragment = HOME;

    public LauncherPresenter(LauncherContract.View view) {
        this.view = view;
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
        view.insertTrainerFragment();
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
        switch (currentFragment){
            case HOME:
                view.endActivity();
                break;
            case SHOW_PLAN:
                view.insertPlansFragment();
                break;
            default:
                view.insertHomeFragment();
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
}
