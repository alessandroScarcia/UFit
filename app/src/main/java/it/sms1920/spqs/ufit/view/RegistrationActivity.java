package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.sms1920.spqs.ufit.contract.RegistrationContract;
import it.sms1920.spqs.ufit.presenter.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    private RegistrationPresenter presenter;
    Activity mContext = this;

    private TextInputEditText emailReg;
    private TextInputEditText passwordReg;
    private TextInputEditText passwordConfirm;
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

                presenter.onSignUp(emailReg.getText().toString(), passwordReg.getText().toString(),
                        passwordConfirm.getText().toString());

            }
        });


    }

    private void initializeView() {
        emailReg = findViewById(R.id.TextInputEditEmailReg);
        passwordReg = findViewById(R.id.TextInputEditTextPassword);
        passwordConfirm = findViewById(R.id.TextInputEditTextPasswordConfirm);
        signUpBtn = findViewById(R.id.singUpBtn);
    }

    @Override
    public void showSignUpSuccessFully(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LauncherActivity.class));
    }

    @Override
    public void showSignUpFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValidationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
