package it.sms1920.spqs.ufit.launcher.userprofile.choose;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.sms1920.spqs.ufit.launcher.LauncherActivity;
import it.sms1920.spqs.ufit.launcher.LauncherContract;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.userprofile.login.LoginActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.show.ProfileFragment;

/**
 * View that lets the user choose if he wants to register an account or login into an existing one.
 */
public class ChooseFragment extends Fragment implements ChooseContract.View {
    private static final String TAG = ChooseFragment.class.getCanonicalName();

    private ChooseContract.Presenter presenter;

    public ChooseFragment() {
        this.presenter = new ChoosePresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);

        Button btnRegister = view.findViewById(R.id.btnRegister);
        Button btnLogin = view.findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonRegisterClicked();
            }
        });

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonLoginClicked();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onChooseFragmentResume();
    }

    @Override
    public void startRegisterActivity() {
        startActivity(new Intent(getContext(), RegistrationActivity.class));
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void insertProfileFragment() {
        LauncherActivity launcher = (LauncherActivity) getActivity();
        if (launcher != null) {
            launcher.insertProfileFragment();
        }
    }
}