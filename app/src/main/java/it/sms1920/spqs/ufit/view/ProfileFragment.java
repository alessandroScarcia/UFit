package it.sms1920.spqs.ufit.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import it.sms1920.spqs.ufit.contract.Profile;
import it.sms1920.spqs.ufit.model.User;
import it.sms1920.spqs.ufit.presenter.ProfilePresenter;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements Profile.View {

    private Profile.Presenter presenter;
    private Activity mContext;
    private Button btnSignOut;

    private ImageView imgProfile;

    private Spinner spinnerGender;
    private Spinner spinnerHeight;
    private Spinner spinnerWeight;

    private TextInputLayout txtUserEmailLayout;
    private TextInputLayout txtUserPasswordLayout;
    private TextInputLayout txtNameLayout;
    private TextInputLayout txtSurnameLayout;
    private TextInputLayout txtHeightLayout;
    private TextInputLayout txtWeightLayout;

    private TextInputEditText txtUserEmail;
    private TextInputEditText txtUserPassword;
    private TextInputEditText txtName;
    private TextInputEditText txtSurname;
    private TextInputEditText txtHeight;
    private TextInputEditText txtWeight;

    private TextView lblDate;

    private ArrayAdapter<CharSequence> adapterGender;
    private ArrayAdapter<CharSequence> adapterHeight;
    private ArrayAdapter<CharSequence> adapterWeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();

        imgProfile = view.findViewById(R.id.imgProfile);

        btnSignOut = view.findViewById(R.id.btnSignOut);

        spinnerGender = view.findViewById(R.id.spinnerGender);
        spinnerHeight = view.findViewById(R.id.spinnerHeight);
        spinnerWeight = view.findViewById(R.id.spinnerWeight);

        txtUserEmailLayout = view.findViewById(R.id.txtUserEmailLayout);
        txtUserPasswordLayout = view.findViewById(R.id.txtUserPasswordLayout);
        txtNameLayout = view.findViewById(R.id.txtNameLayout);
        txtSurnameLayout = view.findViewById(R.id.txtSurnameLayout);
        txtHeightLayout = view.findViewById(R.id.txtUserHeightLayout);
        txtWeightLayout = view.findViewById(R.id.txtUserWeightLayout);

        txtUserEmail = view.findViewById(R.id.txtUserEmail);
        txtUserPassword = view.findViewById(R.id.txtUserPassword);
        txtName = view.findViewById(R.id.txtName);
        txtSurname = view.findViewById(R.id.txtSurname);
        txtHeight = view.findViewById(R.id.txtUserHeight);
        txtWeight = view.findViewById(R.id.txtUserWeight);

        adapterGender = ArrayAdapter.createFromResource(view.getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);

        adapterHeight = ArrayAdapter.createFromResource(view.getContext(), R.array.height_untis, android.R.layout.simple_spinner_item);
        adapterHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeight.setAdapter(adapterHeight);

        adapterWeight = ArrayAdapter.createFromResource(view.getContext(), R.array.weight_units, android.R.layout.simple_spinner_item);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(adapterWeight);


        lblDate = view.findViewById(R.id.lblDate);

        presenter = new ProfilePresenter(ProfileFragment.this);

       // presenter.onUpdateRequest();//mettere uno splash per ingannare il caricamento

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUploadPicProfile();

            }
        });


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.onSignOut();
            }
        });

        return view;
    }

    @Override
    public void resetLauncherActivity() {
        startActivity(new Intent(getActivity(), LauncherActivity.class));
        getActivity().finish();
    }

    @Override
    public void updateInfo(User user) {
        int spinnerPosition;

        txtUserEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        txtUserPassword.setText("");//qua dovrebbero stare i pallini i neri
        txtName.setText(user.getName());
        txtSurname.setText(user.getSurname());

      // Picasso.get().load(user.getLinkImgProfile()).into(imgProfile);

        switch (user.getGender()) {
            case MALE:
                spinnerPosition = adapterGender.getPosition("Male");
                spinnerGender.setSelection(spinnerPosition);
                break;
            case FEMALE:
                spinnerPosition = adapterGender.getPosition("Female");
                spinnerGender.setSelection(spinnerPosition);
                break;
            case NOT_SPECIFIED:
                spinnerPosition = adapterGender.getPosition("Not specified");
                spinnerGender.setSelection(spinnerPosition);
                break;
        }

        switch (user.getHeightUnit()) {
            case CM:
                spinnerPosition = adapterHeight.getPosition("cm");
                spinnerGender.setSelection(spinnerPosition);
                txtHeight.setText(String.valueOf(user.getHeightCm()));
                break;
            case IN:
                spinnerPosition = adapterHeight.getPosition("in");
                spinnerGender.setSelection(spinnerPosition);
                txtHeight.setText(String.valueOf(user.getHeightIn()));
                break;
        }

        switch (user.getHeightUnit()) {
            case CM:
                spinnerPosition = adapterHeight.getPosition("cm");
                spinnerGender.setSelection(spinnerPosition);
                txtHeight.setText(String.valueOf(user.getHeightCm()));
                break;
            case IN:
                spinnerPosition = adapterHeight.getPosition("in");
                spinnerGender.setSelection(spinnerPosition);
                txtHeight.setText(String.valueOf(user.getHeightIn()));
                break;
        }


        switch (user.getWeightUnit()) {
            case KG:
                spinnerPosition = adapterHeight.getPosition("kg");
                spinnerGender.setSelection(spinnerPosition);
                txtHeight.setText(String.valueOf(user.getBodyWeightKg()));
                break;
            case LB:
                spinnerPosition = adapterHeight.getPosition("lb");
                spinnerGender.setSelection(spinnerPosition);
                txtHeight.setText(String.valueOf(user.getBodyWeightLbs()));
                break;
        }

    }

    public void choosePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri;

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            presenter.uploadPicOnStorage(imageUri);
            presenter.onUpdateRequest();
        }
    }


}
