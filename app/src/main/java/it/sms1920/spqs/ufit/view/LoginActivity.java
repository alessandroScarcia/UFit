package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.presenter.LoginPresenter;


public class LoginActivity extends AppCompatActivity implements Login.View {

    private LoginPresenter presenter;
    Activity mContext = this;

    private TextView lblForgotPassword;
    private TextView lblSignUp;

    private TextInputEditText txtEmail;
    private TextInputLayout txtEmailLayout;
    private TextInputEditText txtPassword;
    private TextInputLayout txtPasswordLayout;

    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmailReset);
        txtPassword = findViewById(R.id.txtPassword);

        txtEmailLayout = findViewById(R.id.txtEmailResetLayout);
        txtPasswordLayout = findViewById(R.id.txtPasswordLayout);

        lblForgotPassword = findViewById(R.id.lblForgotPassword);
        lblSignUp = findViewById(R.id.lblSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        presenter = new LoginPresenter((Login.View) mContext);

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtEmailLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmailLayout.setError(null);
                txtPasswordLayout.setError(null);
                presenter.onSignIn(txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });

        lblForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        lblSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }

    @Override
    public void setInputError(Login.Presenter.InputErrorType inputErrorType) {
        switch (inputErrorType) {
            case EMAIL_FIELD_EMPTY:
                txtEmailLayout.setError(getString(R.string.email_empty));
                break;
            case PASSWORD_FIELD_EMPTY:
                txtPasswordLayout.setError(getString(R.string.password_empty));
                break;
            case EMAIL_FORMAT_NOT_VALID:
                txtEmailLayout.setError(getString(R.string.email_not_valid));
                break;
        }
    }

    @Override
    public void setSignInError(Login.Presenter.AuthResultType authResultType) {
        switch (authResultType) {
            case EMAILS_NOT_MATCH:
                txtEmailLayout.setError(getString(R.string.email_not_exists));
                break;
            case PASSWORDS_NOT_MATCH:
                txtPasswordLayout.setError(getString(R.string.password_not_match));
                break;
        }
    }

    @Override
    public void startLauncherActivity() {
        finish();
        startActivity(new Intent(LoginActivity.this, LauncherActivity.class));
    }

    @Override
    public void setEnabledUI(boolean enabled) {
        txtPassword.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
        btnSignIn.setEnabled(enabled);
    }

}
