package it.sms1920.spqs.ufit.launcher.userprofile.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.AuthResultType;
import it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationContract.Presenter.InputErrorType;
import it.sms1920.spqs.ufit.launcher.userprofile.login.LoginActivity;
import it.sms1920.spqs.ufit.launcher.R;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    private RegistrationPresenter presenter;

    private TextInputEditText txtEmail;
    private TextInputLayout txtEmailLayout;
    private TextInputEditText txtPassword;
    private TextInputLayout txtPasswordLayout;
    private TextInputEditText txtConfirmPassword;
    private TextInputLayout txtConfirmPasswordLayout;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter = new RegistrationPresenter(this);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        // layout views setup
        txtEmail = findViewById(R.id.lblEmail);
        txtEmailLayout = findViewById(R.id.txtEmailLayout);
        txtPassword = findViewById(R.id.lblPassword);
        txtPasswordLayout = findViewById(R.id.txtPasswordLayout);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtConfirmPasswordLayout = findViewById(R.id.txtConfirmPasswordLayout);
        btnRegister = findViewById(R.id.btnSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmailLayout.setError(null);
                txtPasswordLayout.setError(null);
                txtConfirmPasswordLayout.setError(null);

                if (txtEmail.getText() != null
                        && txtPassword.getText() != null
                        && txtConfirmPassword.getText() != null) {

                    presenter.onRegisterButtonClicked(txtEmail.getText().toString(),
                            txtPassword.getText().toString(),
                            txtConfirmPassword.getText().toString());
                }
            }
        });

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginButtonClicked();
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
    public void setRegistrationError(AuthResultType authResultType) {
        if (authResultType == AuthResultType.USER_ALREADY_EXISTS) {
            txtEmailLayout.setError(getString(R.string.email_already_exists));
        }
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void setEnabledUI(boolean enabled) {
        txtEmail.setEnabled(enabled);
        txtPassword.setEnabled(enabled);
        txtConfirmPassword.setEnabled(enabled);
        btnRegister.setEnabled(enabled);
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
