package it.sms1920.spqs.ufit.presenter;


import it.sms1920.spqs.ufit.contract.AppLoading;

/**
 * Presenter for View 'MainActivity'
 */
public class Loading implements AppLoading.Presenter {
    private AppLoading.View view;

    public Loading(AppLoading.View view) {
        this.view = view;
    }

    @Override
    public void appInitialized() {
        view.showLauncher();
    }
}