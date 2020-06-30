package it.sms1920.spqs.ufit.launcher.home;

import android.view.View;

import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceList;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceListContract;

public class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = HomePresenter.class.getCanonicalName();

    private final HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void onAssembliesReportClicked() {
        view.startAssembliesReportActivity();
    }

    @Override
    public void callGetRandomAdvice(HomeFragment view) {
        AdviceListContract.Presenter advicePresenter = new AdviceList(view);
        advicePresenter.getRandomAdvice();
    }




}
