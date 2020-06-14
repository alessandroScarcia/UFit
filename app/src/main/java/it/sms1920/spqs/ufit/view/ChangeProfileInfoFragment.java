package it.sms1920.spqs.ufit.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.sms1920.spqs.ufit.contract.iChangeProfileInfo;
import it.sms1920.spqs.ufit.presenter.ChangeProfileInfoPresenter;


public class ChangeProfileInfoFragment extends Fragment implements iChangeProfileInfo.View{


    private ChangeProfileInfoPresenter presenter;

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

    private MaterialButton bntApplyChangeInfo;

    private ArrayAdapter<CharSequence> adapterGender;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_profile_info, container, false);
        // Inflate the layout for this fragment

          /*
        Initializing view items
                */
        imgProfilePicture = view.findViewById(R.id.imgProfile1);
        txtNameLayout = view.findViewById(R.id.txtNameLayout1);
        txtSurnameLayout = view.findViewById(R.id.txtSurnameLayout1);
        txtHeightLayout = view.findViewById(R.id.txtHeightLayout1);
        txtWeightLayout = view.findViewById(R.id.txtWeightLayout1);
        txtEmailLayout = view.findViewById(R.id.txtEmailLayout1);
        txtPasswordLayout = view.findViewById(R.id.txtPasswordLayout1);
        txtName = view.findViewById(R.id.txtName1);
        txtSurname = view.findViewById(R.id.txtSurname1);
        txtGender = view.findViewById(R.id.txtGender1);
        txtHeight = view.findViewById(R.id.txtHeight1);
        txtWeight = view.findViewById(R.id.txtWeight1);
        txtUserEmail = view.findViewById(R.id.txtEmail1);
        txtUserPassword = view.findViewById(R.id.txtPassword1);
        bntApplyChangeInfo = view.findViewById(R.id.btnApplyChange);

        /*
            Setting gender list to drop down menu
         */
        adapterGender = ArrayAdapter.createFromResource(view.getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtGender.setAdapter(adapterGender);

        presenter = new ChangeProfileInfoPresenter((iChangeProfileInfo.View)ChangeProfileInfoFragment.this);
        presenter.onShowAllProfileInfo();


        return view;
    }

    @Override
    public void showAllProfileInfo() {
        Bundle bundle = getArguments();

        String name = bundle.getString(String.valueOf(R.string.name));
        String email = bundle.getString(String.valueOf(R.string.email));
        String surname = bundle.getString(String.valueOf(R.string.surname));
        String gender = bundle.getString(String.valueOf(R.string.gender));


        txtName.setText(name);
        txtUserEmail.setText(email);
        txtSurname.setText(surname);
        txtGender.setText(gender);
    }
}