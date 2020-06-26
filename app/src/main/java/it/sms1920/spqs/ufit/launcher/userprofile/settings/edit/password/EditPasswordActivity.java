package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.userprofile.login.LoginActivity;

public class EditPasswordActivity extends AppCompatActivity implements EditPasswordContract.View {
    private static final String TAG = EditPasswordActivity.class.getCanonicalName();

    private EditPasswordContract.Presenter presenter;

    private Toolbar toolbar;

    private TextInputLayout etlPassword;
    private TextInputEditText etPassword;
    private TextInputLayout etlConfirmPassword;
    private TextInputEditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_actiivty);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.profile_settings_edit_password));
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        etlPassword = findViewById(R.id.etlPassword);
        etPassword = findViewById(R.id.etPassword);
        etlConfirmPassword = findViewById(R.id.etlConfirmPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        presenter = new EditPasswordPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar.
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        toolbar.getMenu().findItem(R.id.confirm).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.confirm && etPassword.getText() != null && etConfirmPassword.getText() != null) {
            etlPassword.setError(null);
            etlConfirmPassword.setError(null);
            presenter.confirmEdit(etPassword.getText().toString(), etConfirmPassword.getText().toString());
        }
        return true;
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void setError(EditPasswordContract.Presenter.PasswordError error) {
        switch (error) {
            case PASSWORD_EMPTY:
                etlPassword.setError(getString(R.string.password_empty));
                break;
            case PASSWORD_TOO_WEAK:
                etlPassword.setError(getString(R.string.password_not_valid));
                break;
            case PASSWORDS_NOT_MATHING:
                etlConfirmPassword.setError(getString(R.string.passwords_not_equal));
                break;
            default:
                break;
        }
    }

    @Override
    public void reauthenticate() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}