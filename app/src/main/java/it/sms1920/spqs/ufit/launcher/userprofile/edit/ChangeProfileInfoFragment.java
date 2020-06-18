package it.sms1920.spqs.ufit.launcher.userprofile.edit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import it.sms1920.spqs.ufit.launcher.userprofile.delete.DeleteProfileDialog;
import it.sms1920.spqs.ufit.launcher.R;

import static android.app.Activity.RESULT_OK;


public class ChangeProfileInfoFragment extends Fragment implements ChangeProfileInfoContract.View, ChangePasswordDialog.ChangePasswordDialogListener,
        ChangeEmailDialog.ChangeEmailDialogListener, DeleteProfileDialog.DeleteProfileDialogListener {


    private ChangeProfileInfoPresenter presenter;

    private ImageView imgChangeProfilePicture;

    private TextInputLayout txtNameLayout;
    private TextInputLayout txtSurnameLayout;
    private TextInputLayout txtDateLayout;
    private TextInputLayout txtGenderLayout;
    private TextInputLayout txtHeightLayout;
    private TextInputLayout txtWeightLayout;

    private TextInputEditText txtName;
    private TextInputEditText txtSurname;
    private TextInputEditText txtHeight;
    private TextInputEditText txtWeight;

    private DatePickerDialog birthDateDialog;

    private AutoCompleteTextView txtGender;

    private MaterialTextView lblChangePassword;
    private MaterialTextView lblChangeEmail;
    private MaterialTextView lblBirthDate;

    private MaterialButton bntApplyChangeInfo;
    private MaterialButton btnDeleteProfile;

    private ArrayAdapter<CharSequence> adapterGender;

    int year;
    int month;
    int day;

    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_profile_info, container, false);
        // Inflate the layout for this fragment

          /*
        Initializing view items
                */
        imgChangeProfilePicture = view.findViewById(R.id.imgChangeProfile);
        txtNameLayout = view.findViewById(R.id.txtNameLayout);
        txtSurnameLayout = view.findViewById(R.id.txtSurnameLayout);
        txtHeightLayout = view.findViewById(R.id.txtHeightLayout);
        txtWeightLayout = view.findViewById(R.id.txtWeightLayout);

        txtName = view.findViewById(R.id.txtName);
        txtSurname = view.findViewById(R.id.txtSurname);
        txtGender = view.findViewById(R.id.txtGender);
        txtHeight = view.findViewById(R.id.txtHeight);
        txtWeight = view.findViewById(R.id.txtWeight);

        lblChangeEmail = view.findViewById(R.id.lblChangeEmail);
        lblChangePassword = view.findViewById(R.id.lblChangePassword);
        lblBirthDate = view.findViewById(R.id.lblBirthDate);


        bntApplyChangeInfo = view.findViewById(R.id.btnApplyChange);
        btnDeleteProfile = view.findViewById(R.id.btnDeleteProfile);
        /*
            Setting gender list to drop down menu
         */
        adapterGender = ArrayAdapter.createFromResource(view.getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtGender.setAdapter(adapterGender);



        presenter = new ChangeProfileInfoPresenter(ChangeProfileInfoFragment.this);
        presenter.onShowAllProfileInfo();


        imgChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPicProfileChanged();
            }
        });

        lblBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                birthDateDialog = new DatePickerDialog(ChangeProfileInfoFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        lblBirthDate.setText(day+"/"+month+"/"+year);
                    }
                }, year, month, day);

                birthDateDialog.show();
            }
        });


        lblChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeEmailDialog();
            }
        });

        lblChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePasswordDialog();
            }
        });

        bntApplyChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUpdateInfo();
            }
        });

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteDialog();
            }
        });

        return view;
    }

    private void openChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.setTargetFragment(this, 1);
        changePasswordDialog.show(getFragmentManager(),"ChangeEmailDialog");

    }

    @Override
    public void showAllProfileInfo() {
        Bundle bundle = getArguments();

        String name = bundle.getString(String.valueOf(R.string.name));
        String email = bundle.getString(String.valueOf(R.string.email));
        String surname = bundle.getString(String.valueOf(R.string.surname));

        String birthDate = bundle.getString(String.valueOf(R.string.date));
        String gender = bundle.getString(String.valueOf(R.string.gender));

        int height = bundle.getInt(String.valueOf(R.string.height));
        int weight = bundle.getInt(String.valueOf(R.string.weight));
        String urlImage = bundle.getString(String.valueOf(R.string.image));


        lblBirthDate.setText(birthDate);
        txtName.setText(name);
        lblChangeEmail.setText(email);
        txtSurname.setText(surname);
        txtGender.setText(gender);
        txtHeight.setText(String.valueOf(height));
        txtWeight.setText(String.valueOf(weight));


        updatePic(urlImage);
    }


    private void openDeleteDialog(){
        DeleteProfileDialog deleteProfileDialog = new DeleteProfileDialog();
        deleteProfileDialog.setTargetFragment(this, 1);
        deleteProfileDialog.show(getFragmentManager(),"DeleteProfileDialog");
    }
    private void openChangeEmailDialog(){
        ChangeEmailDialog changeEmailDialog = new ChangeEmailDialog();
        changeEmailDialog.setTargetFragment(this, 1);
        changeEmailDialog.show(getFragmentManager(),"ChangeEmailDialog");
    }
    @Override
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

    @Override
    public void updatePic(final String urlImage) {


        Picasso.get().load(urlImage).networkPolicy(NetworkPolicy.OFFLINE).into(imgChangeProfilePicture, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(urlImage).into(imgChangeProfilePicture);
            }
        });

    }

    @Override
    public void updateEmail(String newEmail) {
        lblChangeEmail.setText(newEmail);
    }

    @Override
    public void updateName(String name) {
        txtName.setText(name);
    }

    @Override
    public void updateSurname(String surname) {
        txtSurname.setText(surname);
    }

    @Override
    public void updateHeight(int height) {
        txtHeight.setText(height);
    }

    @Override
    public void updateWeight(int weight) {
        txtWeight.setText(String.valueOf(weight));
    }

    @Override
    public void updateBirthDate(String date) {
        lblBirthDate.setText(date);
   }

   @Override
    public void updateGender(String gender) {
        txtGender.setText(gender);

   }

    @Override
    public void applyChangeEmail(String currentPassword, String newEmail) {
        presenter.onEmailChanged(currentPassword,newEmail);
    }

    @Override
    public void applyChangePassword(String currentPassword, String newPassword) {
        presenter.onPasswordChanged(currentPassword,newPassword);
    }

    @Override
    public void applyDelete(String currentPassword) {
        presenter.onDeleteProfile(currentPassword);
    }
}