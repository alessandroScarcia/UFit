package it.sms1920.spqs.ufit.presenter;

import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.contract.iLauncher;

import static it.sms1920.spqs.ufit.contract.iLauncher.view.FragType;
import static it.sms1920.spqs.ufit.contract.iLauncher.view.FragType.HOME;
import static it.sms1920.spqs.ufit.contract.iLauncher.view.FragType.PLANS;
import static it.sms1920.spqs.ufit.contract.iLauncher.view.FragType.PROFILE;
import static it.sms1920.spqs.ufit.contract.iLauncher.view.FragType.STATS;
import static it.sms1920.spqs.ufit.contract.iLauncher.view.FragType.TRAINER;

public class LauncherManager implements iLauncher.presenter {

    iLauncher.view view;
    FragType currentFragment = HOME;

    public LauncherManager(iLauncher.view view) {
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
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {//se è già loggato
            view.insertProfileFragment();
            currentFragment = PROFILE;
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
        FirebaseAuth.getInstance().signOut();
    }
}
