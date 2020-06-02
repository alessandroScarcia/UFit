package it.sms1920.spqs.ufit.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.sms1920.spqs.ufit.contract.RegistrationContract;
import it.sms1920.spqs.ufit.presenter.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    private RegistrationPresenter presenter;
    Activity mContext = this;
    private EditText emailReg;
    private EditText passwordReg;
    private EditText passwordConfirm;
    private Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeView();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter = new RegistrationPresenter((RegistrationContract.View) mContext);

                presenter.handleSignUp(emailReg.getText().toString(), passwordReg.getText().toString(),
                        passwordConfirm.getText().toString());

            }
        });


    }

    /*private User inputUser() {
        User userReg = new User();

        SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yy");
        Date date = null;
        try {
            date = dataFormatter.parse(dateBirthReg.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userReg.setDateBirth(date);
        userReg.setName(nameReg.getText().toString());
        userReg.setSurname(surnameReg.getText().toString());
        userReg.setDateBirth(date);
        userReg.setBodyWeight(Integer.parseInt(weightReg.getText().toString()));
        userReg.setHeight(Integer.parseInt(heightReg.getText().toString()));

        int radioId = radioGroupChooseGender.getCheckedRadioButtonId();
        genderReg = findViewById(radioId);

        userReg.setGender(genderReg.getText().toString());
        userReg.setEmail(emailReg.getText().toString());
        userReg.setPassword(passwordReg.getText().toString());

        return userReg;
    }*/

    private void initializeView() {
       /* nameReg = findViewById(R.id.editName);
        surnameReg = findViewById(R.id.editSurname);
        dateBirthReg = findViewById(R.id.editDate);
        weightReg = findViewById(R.id.editWeight);
        heightReg = findViewById(R.id.editHeight);
        radioGroupChooseGender = findViewById(R.id.chooseGender);*/
        emailReg = findViewById(R.id.emailReg);
        passwordReg = findViewById(R.id.passwordReg);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        signUpBtn = findViewById(R.id.singUpBtn);
    }

    @Override
    public void showSignUpSuccessFully() {
        Toast.makeText(this, "Login SuccessFully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LauncherActivity.class));
    }

    @Override
    public void showSignUpFail() {
        Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValidationError() {
        Toast.makeText(this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
    }



    /*public void checkButton(View v) {
        int radioId = radioGroupChooseGender.getCheckedRadioButtonId();

        genderReg = findViewById(radioId);
    }*/
}
