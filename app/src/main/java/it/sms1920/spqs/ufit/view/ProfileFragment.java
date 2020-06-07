package it.sms1920.spqs.ufit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import it.sms1920.spqs.ufit.contract.Profile;
import it.sms1920.spqs.ufit.model.User;
import it.sms1920.spqs.ufit.presenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements Profile.View {

    private Profile.Presenter presenter;
    private Button btnSignOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        presenter = new ProfilePresenter(ProfileFragment.this);

        btnSignOut = view.findViewById(R.id.btnSignOut);


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.onSignOut();
            }
        });
        // Using the correct layout for this fragment
        return view;
    }

    @Override
    public void resetLauncherActivity() {
        startActivity(new Intent( getActivity(), LauncherActivity.class));
        getActivity().finish();
    }

    @Override
    public void updateInfo(User user) {

    }
}
