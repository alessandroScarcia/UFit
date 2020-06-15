package it.sms1920.spqs.ufit.presenter;

import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.contract.iLauncher;
import it.sms1920.spqs.ufit.model.FirebaseAuthSingleton;

import static it.sms1920.spqs.ufit.contract.iLauncher.View.FragType;
import static it.sms1920.spqs.ufit.contract.iLauncher.View.FragType.HOME;
import static it.sms1920.spqs.ufit.contract.iLauncher.View.FragType.PLANS;
import static it.sms1920.spqs.ufit.contract.iLauncher.View.FragType.PROFILE;
import static it.sms1920.spqs.ufit.contract.iLauncher.View.FragType.STATS;
import static it.sms1920.spqs.ufit.contract.iLauncher.View.FragType.TRAINER;

public class LauncherManager implements iLauncher.Presenter {

    private iLauncher.View view;
    private FragType currentFragment = HOME;

    public LauncherManager(iLauncher.View view) {
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

    @Override
    public void onProfileIconClicked() {

        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        if (firebaseUser != null) {

            if (!firebaseUser.isAnonymous()) {//se è già loggato

                view.insertProfileFragment();
                currentFragment = PROFILE;

            }else

                view.startLoginActivity();

        } else
            view.startLoginActivity();

    }

    @Override
    public void onSearchIconClicked() {
        view.startSearchActivity();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != HOME) {
            view.insertHomeFragment();
            currentFragment = HOME;
        } else view.endActivity();
    }

    @Override
    public void onLogOutIconClicked() {
        view.resetActivity();
        FirebaseAuthSingleton.getFirebaseAuth().signOut();
        FirebaseAuthSingleton.getFirebaseAuth().signInAnonymously();
    }
}
