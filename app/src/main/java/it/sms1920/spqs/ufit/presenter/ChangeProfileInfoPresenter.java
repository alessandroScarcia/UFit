package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.iChangeProfileInfo;

public class ChangeProfileInfoPresenter implements iChangeProfileInfo.Presenter {
    private iChangeProfileInfo.View view;

    public ChangeProfileInfoPresenter(iChangeProfileInfo.View view) {
        this.view = view;
    }


    @Override
    public void onShowAllProfileInfo() {
        view.showAllProfileInfo();
    }
}
