package it.sms1920.spqs.ufit.presenter;


import it.sms1920.spqs.ufit.contract.LauncherManagerContract;

public class LauncherManager implements LauncherManagerContract.presenter {

    LauncherManagerContract.view view;

    public LauncherManager(LauncherManagerContract.view view) {
        this.view = view;
    }

    @Override
    public void onHomeIconClick() {
        view.insertHomeFragment();
    }

    @Override
    public void onPlansIconClick() {
        view.insertPlansFragment();
    }

    @Override
    public void onTrainerIconClick() {
        view.insertTrainerFragment();
    }

    @Override
    public void onStatsIconClick() {
        view.insertStatsFragment();
    }

    @Override
    public void onProfileIconClick() {
        view.insertProfileFragment();
    }

    @Override
    public void onSearchIconClick() {
        view.startSearchActivity();
    }
}
