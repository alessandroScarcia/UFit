package it.sms1920.spqs.ufit.presenter;

import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.contract.Profile;

public class ProfilePresenter implements Profile.Presenter {
    Profile.View view;

    public ProfilePresenter(Profile.View view) {
        this.view = view;
    }


    @Override
    public void onSingOut() {
        FirebaseAuth.getInstance().signOut();
        view.insertHomeFragment();
    }
}
