package it.sms1920.spqs.ufit.launcher.userprofile.choose;

import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

public class ChoosePresenter implements ChooseContract.Presenter {
    private static final String TAG = ChoosePresenter.class.getCanonicalName();
    private ChooseContract.View view;

    public ChoosePresenter(ChooseContract.View view) {
        this.view = view;
    }

    @Override
    public void onButtonRegisterClicked() {
        view.startRegisterActivity();
    }

    @Override
    public void onButtonLoginClicked() {
        view.startLoginActivity();
    }

    @Override
    public void onChooseFragmentResume() {
        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        if (firebaseUser != null && !firebaseUser.isAnonymous()) {
            view.insertProfileFragment();
        }
    }
}
