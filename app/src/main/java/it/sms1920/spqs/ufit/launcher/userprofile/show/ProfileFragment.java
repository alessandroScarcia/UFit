package it.sms1920.spqs.ufit.launcher.userprofile.show;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import it.sms1920.spqs.ufit.launcher.R;


public class ProfileFragment extends Fragment implements ProfileContract.View {
    private static final String TAG = ProfileFragment.class.getCanonicalName();

    private ImageView imgProfile;
    private TextView tvNameSurname;
    private TextView tvGender;
    private TextView tvBirthDate;
    private TextView tvNoInfoAvailable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ProfileContract.Presenter presenter = new ProfilePresenter(ProfileFragment.this);

        imgProfile = view.findViewById(R.id.imgProfile);
        tvNameSurname = view.findViewById(R.id.tvNameSurname);
        tvBirthDate = view.findViewById(R.id.tvBirthDate);
        tvGender = view.findViewById(R.id.tvGender);
        tvNoInfoAvailable = view.findViewById(R.id.tvNoInfoAvailable);

        return view;
    }

    @Override
    public void showProfileImage(String urlImage) {
        Picasso.get()
                .load(urlImage)
                .placeholder(R.drawable.img_profile_placeholder)
                .into(imgProfile);
    }

    @Override
    public void showNameSurname(String name, String surname) {
        tvNameSurname.setText(getString(R.string.profile_name_surname_placeholder, name, surname));
        tvNameSurname.setVisibility(View.VISIBLE);
    }


    @Override
    public void showGender(Integer gender) {
        tvGender.setText(getResources().getStringArray(R.array.genders)[gender]);
        tvGender.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBirthDate(String birthDate) {
        tvBirthDate.setText(birthDate);
        tvBirthDate.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoInfoAvailable() {
        if (tvNoInfoAvailable.getVisibility() == View.VISIBLE) {
            tvNoInfoAvailable.setVisibility(View.INVISIBLE);
        }
    }
}


