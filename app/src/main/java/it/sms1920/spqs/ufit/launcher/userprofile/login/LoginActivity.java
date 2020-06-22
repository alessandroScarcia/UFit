package it.sms1920.spqs.ufit.launcher.userprofile.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.sms1920.spqs.ufit.launcher.userprofile.registration.RegistrationActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.resetpassword.ResetPasswordActivity;
import it.sms1920.spqs.ufit.launcher.R;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter presenter;

    private TextInputEditText txtEmail;
    private TextInputLayout txtEmailLayout;
    private TextInputEditText txtPassword;
    private TextInputLayout txtPasswordLayout;

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        // setup views
        txtEmail = findViewById(R.id.lblEmail);
        txtPassword = findViewById(R.id.lblPassword);

        txtEmailLayout = findViewById(R.id.txtEmailLayout);
        txtPasswordLayout = findViewById(R.id.txtPasswordLayout);

        Button btnForgotPassword = findViewById(R.id.btnForgotPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmailLayout.setError(null);
                txtPasswordLayout.setError(null);

                if (txtEmail.getText() != null && txtPassword.getText() != null) {
                    presenter.onLoginButtonClicked(txtEmail.getText().toString(), txtPassword.getText().toString());
                }
            }
        });

        btnForgotPassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

    }

    @Override
    public void setInputError(LoginContract.Presenter.InputErrorType inputErrorType) {
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
    public void setLoginError(LoginContract.Presenter.AuthResultType authResultType) {
        if (authResultType == LoginContract.Presenter.AuthResultType.FAILURE) {
            txtEmailLayout.setError(" ");
            txtPasswordLayout.setError(getString(R.string.wrong_credentials));
        }
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void setEnabledUi(boolean enabled) {
        txtPassword.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
        btnLogin.setEnabled(enabled);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }
}
