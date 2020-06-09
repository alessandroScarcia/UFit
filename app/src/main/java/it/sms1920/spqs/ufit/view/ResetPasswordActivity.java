package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import it.sms1920.spqs.ufit.contract.Login;
import it.sms1920.spqs.ufit.contract.ResetPassword;
import it.sms1920.spqs.ufit.presenter.LoginPresenter;
import it.sms1920.spqs.ufit.presenter.ResetPasswordPresenter;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPassword.View {

    private ResetPasswordPresenter presenter;
    Activity mContext = this;

    private Button btnSendEmail;
    private EditText txtEmail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);


        presenter = new ResetPasswordPresenter((ResetPassword.View) mContext);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        btnSendEmail = findViewById(R.id.btnSendEmail);
        txtEmail = findViewById(R.id.txtEmailReset);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onResetPassword(txtEmail.getText().toString());
            }
        });

    }

    @Override
    public void showCheckMailBox() {
        Toast.makeText(this, "Check Your Email box", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }
}