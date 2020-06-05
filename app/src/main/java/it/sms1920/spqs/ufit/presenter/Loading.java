package it.sms1920.spqs.ufit.presenter;

import android.content.Context;

import it.sms1920.spqs.ufit.contract.AppLoading;
import it.sms1920.spqs.ufit.model.repository.Repository;

/**
 * Presenter for View 'MainActivity'
 */
public class Loading implements AppLoading.Presenter {
    private AppLoading.View view;
    private Repository model;
    private Context applicationContext;

    public Loading(AppLoading.View view) {
        this.view = view;
        this.model = new Repository(this);
    }

    /**
     * Called when the app starts. This method must check the local repository consistency with
     * the remote repository and make all necessary updates.
     */
    @Override
    public void appInitialized() {
        this.applicationContext = view.retriveApplicationContext();
        model.buildDatabase(applicationContext);
    }
}
