package it.sms1920.spqs.ufit.launcher.userprofile.resetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.sms1920.spqs.ufit.launcher.R;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordContract.View {
    private static final String TAG = ResetPasswordActivity.class.getCanonicalName();

    private ResetPasswordPresenter presenter;

    private TextInputLayout txtEmailLayout;
    private TextInputEditText txtEmail;
    private Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        presenter = new ResetPasswordPresenter(this);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });

        txtEmailLayout = findViewById(R.id.txtEmailLayout);
        txtEmail = findViewById(R.id.txtEmailReset);
        btnSendEmail = findViewById(R.id.btnSendEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEmail.getText() != null) {
                    presenter.onSendEmailButtonClicked(txtEmail.getText().toString());
                }
            }
        });

    }

    @Override
    public void showEmailSentMessage() {
        Toast.makeText(this, getString(R.string.password_reset_email_sent), Toast.LENGTH_LONG).show();
        closeActivity();
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.idle, R.anim.exit_to_right);
    }

    @Override
    public void setEnabledUi(boolean enabled) {
        btnSendEmail.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
    }

    @Override
    public void setError() {
        txtEmailLayout.setError(getString(R.string.email_not_valid));
    }
}