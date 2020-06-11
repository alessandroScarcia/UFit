package it.sms1920.spqs.ufit.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import it.sms1920.spqs.ufit.contract.iProfile;

import it.sms1920.spqs.ufit.model.PicassoSingleton;
import it.sms1920.spqs.ufit.presenter.ProfilePresenter;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements iProfile.View {

    private iProfile.Presenter presenter;

    private ImageView imgProfilePicture;

    private TextInputLayout txtNameLayout;
    private TextInputLayout txtSurnameLayout;
    private TextInputLayout txtDateLayout;
    private TextInputLayout txtGenderLayout;
    private TextInputLayout txtHeightLayout;
    private TextInputLayout txtWeightLayout;
    private TextInputLayout txtEmailLayout;
    private TextInputLayout txtPasswordLayout;

    private TextInputEditText txtName;
    private TextInputEditText txtSurname;
    //private Datepicker
    private AutoCompleteTextView txtGender;
    private TextInputEditText txtHeight;
    private TextInputEditText txtWeight;
    private TextInputEditText txtUserEmail;
    private TextInputEditText txtUserPassword;


    private ArrayAdapter<CharSequence> adapterGender;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        /*
        Initializing view items
                */
        imgProfilePicture = view.findViewById(R.id.imgProfile);
        txtNameLayout = view.findViewById(R.id.txtNameLayout);
        txtSurnameLayout = view.findViewById(R.id.txtSurnameLayout);
        txtHeightLayout = view.findViewById(R.id.txtHeightLayout);
        txtWeightLayout = view.findViewById(R.id.txtWeightLayout);
        txtEmailLayout = view.findViewById(R.id.txtEmailLayout);
        txtPasswordLayout = view.findViewById(R.id.txtPasswordLayout);
        txtName = view.findViewById(R.id.txtName);
        txtSurname = view.findViewById(R.id.txtSurname);
        txtGender = view.findViewById(R.id.txtGender);
        txtHeight = view.findViewById(R.id.txtHeight);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtUserEmail = view.findViewById(R.id.txtEmail);
        txtUserPassword = view.findViewById(R.id.txtPassword);

        /*
            Setting gender list to drop down menu
         */
        adapterGender = ArrayAdapter.createFromResource(view.getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtGender.setAdapter(adapterGender);

        // TODO datepicker

        presenter = new ProfilePresenter(ProfileFragment.this);

        presenter.onUpdateInfo(); // TODO mettere uno splash per ingannare il caricamento

        imgProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPicProfileChanged();
            }
        });

        return view;
    }


    @Override
    public void updateName(String name) {
        txtName.setText(name);
    }

    @Override
    public void updateEmail(String email) {
        txtUserEmail.setText(email);
    }

    @Override
    public void updateHeight(int height) {
        txtHeight.setText(String.valueOf(height));
    }

    @Override
    public void updateWeight(int weight) {
        txtWeight.setText(String.valueOf(weight));
    }

    @Override
    public void updateSurname(String surname) {
        txtSurname.setText(surname);
    }

    @Override
    public void updatePassword() {
        txtUserPassword.setText(R.string.password);
        txtUserPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    @Override
    public void updatePic(final String urlImage) {


        Picasso.get().load(urlImage).networkPolicy(NetworkPolicy.OFFLINE).into(imgProfilePicture, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(urlImage).into(imgProfilePicture);
            }
        });

    }

    public void choosePic() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            presenter.uploadPicOnStorage(imageUri);
        }
    }

}


