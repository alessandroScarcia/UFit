package it.sms1920.spqs.ufit.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import it.sms1920.spqs.ufit.contract.iProfile;
import it.sms1920.spqs.ufit.presenter.ProfilePresenter;


public class ProfileFragment extends Fragment implements iProfile.View {

    private iProfile.Presenter presenter;

    private ImageView imgProfilePicture;
    private Bundle bundle;

    private MaterialButton btnChangeProfileInfo;


    private MaterialTextView lblName;
    private MaterialTextView lblSurname;
    private MaterialTextView lblBirthDate;
    private MaterialTextView lblHeight;
    private MaterialTextView lblWeight;
    private MaterialTextView lblEmail;
    private MaterialTextView lblPassword;
    private MaterialTextView lblGender;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        bundle = new Bundle();

        imgProfilePicture = view.findViewById(R.id.imgProfile);

        lblName = view.findViewById(R.id.lblName);
        lblSurname = view.findViewById(R.id.lblSurname);
        lblBirthDate = view.findViewById(R.id.lblBirthDate);
        lblEmail = view.findViewById(R.id.lblEmail);
        lblPassword = view.findViewById(R.id.lblPassword);
        lblHeight = view.findViewById(R.id.lblHeight);
        lblWeight = view.findViewById(R.id.lblWeight);
        lblGender = view.findViewById(R.id.lblGender);



        btnChangeProfileInfo = view.findViewById(R.id.btnChangeProfileInfo);

        btnChangeProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickChangeProfileInfo();
            }
        });

        /*
            Setting gender list to drop down menu
         */
      /*  adapterGender = ArrayAdapter.createFromResource(view.getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtGender.setAdapter(adapterGender);*/

        // TODO datepicker

        presenter = new ProfilePresenter(ProfileFragment.this);

        presenter.onShowProfileInfo(); // TODO mettere uno splash per ingannare il caricamento


        return view;
    }


   @Override
    public void showName(String name) {
        lblName.setText(name);
        bundle.putString(String.valueOf(R.string.name),name);
    }

    @Override
    public void showEmail(String email) {
        lblEmail.setText(email);
        bundle.putString(String.valueOf(R.string.email),email);
    }

    @Override
    public void showHeight(int height) {
        lblHeight.setText(String.valueOf(height)+ "cm");
        bundle.putInt(String.valueOf(R.string.height),height);


    }

    @Override
    public void showWeight(int weight) {
        lblWeight.setText(String.valueOf(weight)+ "kg");
        bundle.putInt(String.valueOf(R.string.weight),weight);
    }

    @Override
    public void showSurname(String surname) {
        lblSurname.setText(surname);
        bundle.putString(String.valueOf(R.string.surname),surname);
    }

    @Override
    public void showGender(String gender) {
        lblGender.setText(gender);
        bundle.putString(String.valueOf(R.string.gender),gender);
    }

    @Override
    public void showBirthDate(String birthDate) {
        lblBirthDate.setText(birthDate);
        bundle.putString(String.valueOf(R.string.date),birthDate);
    }


    @Override
    public void showImagePicture(final String urlImage) {


        Picasso.get().load(urlImage).networkPolicy(NetworkPolicy.OFFLINE).into(imgProfilePicture, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(urlImage).into(imgProfilePicture);
            }
        });

        bundle.putString(String.valueOf(R.string.image), urlImage);
    }



    @Override
    public void startChangeProfileInfoFragment() {

        bundle.putString(String.valueOf(R.string.email),lblEmail.getText().toString());

        ChangeProfileInfoFragment changeProfileInfoFragment = new ChangeProfileInfoFragment();
        changeProfileInfoFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, changeProfileInfoFragment).commit();

    }



}


