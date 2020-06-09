package it.sms1920.spqs.ufit.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import it.sms1920.spqs.ufit.contract.Profile;
import it.sms1920.spqs.ufit.model.User;
import it.sms1920.spqs.ufit.presenter.ProfilePresenter;

import static it.sms1920.spqs.ufit.model.User.Gender.MALE;
import static it.sms1920.spqs.ufit.model.User.HeightUnit.CM;
import static it.sms1920.spqs.ufit.model.User.WeightUnit.KG;

public class ProfileFragment extends Fragment implements Profile.View {

    private Profile.Presenter presenter;

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
    private ArrayAdapter<CharSequence> adapterHeight;
    private ArrayAdapter<CharSequence> adapterWeight;

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

        presenter.onUpdateRequest(); // TODO mettere uno splash per ingannare il caricamento

        return view;
    }

    @Override
    public void updateInfo(User user) {

        txtUserEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        txtUserPassword.setText("");//qua dovrebbero stare i pallini i neri
        txtName.setText("Michele");//user.getName());
        txtSurname.setText("Cironinano");//user.getSurname());
        //txtGender.setText(MALE.toString());//user.getGender().toString());

        txtHeightLayout.setSuffixText(user.getHeightUnit().toString());
        txtWeightLayout.setSuffixText(user.getWeightUnit().toString());

        if (user.getHeightUnit() == CM)
            txtHeight.setText(String.valueOf(user.getHeightCm()));
        else
            txtHeight.setText(String.valueOf(user.getHeightIn()));

        if (user.getWeightUnit() == KG)
            txtWeight.setText(String.valueOf(user.getBodyWeightKg()));
        else
            txtWeight.setText(String.valueOf(user.getBodyWeightLbs()));
    }
}

