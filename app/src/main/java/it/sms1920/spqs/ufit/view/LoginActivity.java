package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.presenter.LoginPresenter;
import it.sms1920.spqs.ufit.presenter.RegistrationPresenter;


public class LoginActivity extends AppCompatActivity implements Login.View {

    private LoginPresenter presenter;
    Activity mContext = this;

    private TextView textViewforgotPassword;
    private TextView textViewSignUp;
    private TextView textViewError;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();

       textInputEditTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter = new LoginPresenter((Login.View) mContext);
                presenter.checkFields(textInputEditTextEmail.getText().toString(), textViewforgotPassword.getText().toString());
            }
        });

        textInputEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter = new LoginPresenter((Login.View) mContext);
                presenter.checkFields(textInputEditTextEmail.getText().toString(), textViewforgotPassword.getText().toString());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter = new LoginPresenter((Login.View) mContext);

                presenter.onSignIn(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());

            }
        });

        textViewforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }

    private void initializeView() {

        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textViewforgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewSignUp = findViewById(R.id.textViewSIgnup);
        textViewError = findViewById(R.id.textViewError);
        btnSignIn = findViewById(R.id.btnSignIn);

        textViewError.setVisibility(View.INVISIBLE);
        btnSignIn.setEnabled(false);
    }

    @Override
    public void showSignInFail(int signInError) {
        switch (signInError) {
            case PASSWORD_NOT_MATCH:
                textViewError.setText(getResources().getString(R.string.password_not_match));
                textViewError.setVisibility(View.VISIBLE);
                break;
            case EMAIL_NOT_MATCH:
                textViewError.setText(getResources().getString(R.string.email_not_match));
                textViewError.setVisibility(View.VISIBLE);
                break;
            default:
                startActivity(new Intent(LoginActivity.this, LauncherActivity.class));
                break;
        }
    }


    @Override
    public void showInputFail(int inputError) {
        if (inputError == EMAIL_FIELD_EMPTY)
            textInputEditTextEmail.setError(getResources().getString(R.string.email_empty));
        else if (inputError == PASSWORD_FIELD_EMPTY)
            textInputEditTextPassword.setError(getResources().getString(R.string.password_empty));
        else if (inputError == EMAIL_NOT_VALID)
            textInputEditTextEmail.setError(getResources().getString(R.string.email_not_valid));
        else
            btnSignIn.setEnabled(true);
    }

}
