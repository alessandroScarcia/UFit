package it.sms1920.spqs.ufit.view;

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

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import it.sms1920.spqs.ufit.contract.iRegistration;
import it.sms1920.spqs.ufit.contract.iRegistration.Presenter.AuthResultType;
import it.sms1920.spqs.ufit.contract.iRegistration.Presenter.InputErrorType;
import it.sms1920.spqs.ufit.presenter.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity implements iRegistration.View {

    private RegistrationPresenter presenter;
    Activity mContext = this;

    private TextInputEditText txtEmail;
    private TextInputLayout txtEmailLayout;
    private TextInputEditText txtPassword;
    private TextInputLayout txtPasswordLayout;
    private TextInputEditText txtConfirmPassword;
    private TextInputLayout txtConfirmPasswordLayout;
    private Button btnSignup;
    private TextView lblLogin;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        txtEmail = findViewById(R.id.txtEmail);
        txtEmailLayout = findViewById(R.id.txtEmailLayout);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordLayout = findViewById(R.id.txtPasswordLayout);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtConfirmPasswordLayout = findViewById(R.id.txtConfirmPasswordLayout);
        btnSignup = findViewById(R.id.btnSignUp);
        lblLogin = findViewById(R.id.lblLogin);

        presenter = new RegistrationPresenter((iRegistration.View) mContext);

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
        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtConfirmPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmailLayout.setError(null);
                txtPasswordLayout.setError(null);
                txtConfirmPasswordLayout.setError(null);

                presenter.onSignUp(txtEmail.getText().toString(), txtPassword.getText().toString(),
                        txtConfirmPassword.getText().toString());

            }
        });

        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginRequest();
            }
        });


    }


    @Override
    public void setInputError(InputErrorType inputErrorType) {
        switch (inputErrorType) {
            case EMAIL_FIELD_EMPTY:
                txtEmailLayout.setError(getString(R.string.email_empty));
                break;
            case EMAIL_FORMAT_NOT_VALID:
                txtEmailLayout.setError(getString(R.string.email_not_valid));
                break;
            case PASSWORD_FIELD_EMPTY:
                txtPasswordLayout.setError(getString(R.string.password_empty));
                break;
            case PASSWORD_FORMAT_NOT_VALID:
                txtPasswordLayout.setError(getString(R.string.password_not_valid));
                break;
            case PASSWORDS_NOT_MATCHING:
                txtConfirmPasswordLayout.setError(getString(R.string.passwords_not_equal));
                break;
        }
    }

    @Override
    public void setSignUpError(AuthResultType authResultType) {
        switch (authResultType) {
            case USER_ALREADY_EXISTS:
                txtEmailLayout.setError(getString(R.string.email_already_exists));
                break;
        }
    }

    @Override
    public void startLauncherActivity() {
        finish();
        startActivity(new Intent(RegistrationActivity.this, LauncherActivity.class));
    }

    @Override
    public void setEnabledUI(boolean enabled) {
        txtEmail.setEnabled(enabled);
        txtPassword.setEnabled(enabled);
        txtConfirmPassword.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
    }

    @Override
    public void startLoginActivity() {
        finish();
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }
}
