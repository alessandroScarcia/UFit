package it.sms1920.spqs.ufit.presenter;

import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.contract.LauncherManagerContract;

import static it.sms1920.spqs.ufit.contract.LauncherManagerContract.view.FragType;
import static it.sms1920.spqs.ufit.contract.LauncherManagerContract.view.FragType.HOME;
import static it.sms1920.spqs.ufit.contract.LauncherManagerContract.view.FragType.PLANS;
import static it.sms1920.spqs.ufit.contract.LauncherManagerContract.view.FragType.PROFILE;
import static it.sms1920.spqs.ufit.contract.LauncherManagerContract.view.FragType.STATS;
import static it.sms1920.spqs.ufit.contract.LauncherManagerContract.view.FragType.TRAINER;

public class LauncherManager implements LauncherManagerContract.presenter {

    LauncherManagerContract.view view;
    FragType currentFragment = HOME;

    public LauncherManager(LauncherManagerContract.view view) {
        this.view = view;
    }


    @Override
    public void onHomeIconClick() {
        view.insertHomeFragment();
        currentFragment = HOME;
    }

    @Override
    public void onPlansIconClick() {
        view.insertPlansFragment();
        currentFragment = PLANS;
    }

    @Override
    public void onTrainerIconClick() {
        view.insertTrainerFragment();
        currentFragment = TRAINER;
    }

    @Override
    public void onStatsIconClick() {
        view.insertStatsFragment();
        currentFragment = STATS;
    }

    @Override
    public void onProfileIconClick() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {//se è già loggato
            view.insertProfileFragment();
            currentFragment = PROFILE;
        } else
            view.startLoginActivity();
    }

    @Override
    public void onSearchIconClick() {
        view.startSearchActivity();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != HOME) {
            view.insertHomeFragment();
            currentFragment = HOME;
        } else view.endActivity();
    }
}
