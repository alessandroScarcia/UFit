package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.email;

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

public class EditEmailActivity extends AppCompatActivity implements EditEmailContract.View {
    private static final String TAG = EditEmailActivity.class.getCanonicalName();

    private EditEmailContract.Presenter presenter;

    private Toolbar toolbar;

    private TextInputLayout etlEmail;
    private TextInputEditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.profile_settings_edit_email));
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        etlEmail = findViewById(R.id.etlEmail);
        etEmail = findViewById(R.id.etEmail);

        presenter = new EditEmailPresenter(this);
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
        if (item.getItemId() == R.id.confirm && etEmail.getText() != null) {
            etlEmail.setError(null);
            presenter.confirmEdit(etEmail.getText().toString());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void setError(EditEmailContract.Presenter.EmailError error) {
        switch (error) {
            case EMAIL_EMPTY:
                etlEmail.setError(getString(R.string.email_empty));
                break;
            case EMAIL_NOT_VALID:
                etlEmail.setError(getString(R.string.email_not_valid));
                break;
            default:
                break;
        }
    }

    @Override
    public void reauthenticate() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showEmail(String email) {
        etEmail.setText(email);
    }
}