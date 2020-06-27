package it.sms1920.spqs.ufit.launcher.home;

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
}
