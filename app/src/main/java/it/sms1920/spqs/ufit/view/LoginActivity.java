package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.contract.RegistrationContract;
import it.sms1920.spqs.ufit.presenter.LoginPresenter;
import it.sms1920.spqs.ufit.presenter.RegistrationPresenter;

public class LoginActivity extends AppCompatActivity implements Login.View {

    private LoginPresenter presenter;
    Activity mContext = this;

    private TextView forgotPassword;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter = new LoginPresenter((Login.View) mContext);

                presenter.onSignIn(email.getText().toString(), password.getText().toString());

            }
        });


    }

    private void initializeView() {
        email = findViewById(R.id.textInputEditTextEmail);
        password = findViewById(R.id.textInputEditTextPassword);
        forgotPassword = findViewById(R.id.textViewForgotPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    @Override
    public void showSignInSuccessFully(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LauncherActivity.class));
    }

    @Override
    public void showSignInFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
