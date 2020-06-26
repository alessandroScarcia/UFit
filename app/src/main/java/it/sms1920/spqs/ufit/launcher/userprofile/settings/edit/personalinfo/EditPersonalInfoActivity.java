package it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.personalinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

import it.sms1920.spqs.ufit.launcher.R;

public class EditPersonalInfoActivity extends AppCompatActivity implements EditPersonalInfoContract.View {
    private static final String TAG = EditPersonalInfoActivity.class.getCanonicalName();

    private EditPersonalInfoContract.Presenter presenter;

    private Toolbar toolbar;

    private TextInputLayout etlEditName;
    private TextInputEditText etEditName;
    private TextInputLayout etlEditSurname;
    private TextInputEditText etEditSurname;
    private TextInputLayout etlEditGender;
    private AutoCompleteTextView tvEditGender;
    private TextInputLayout etlEditBirthDate;
    private TextInputEditText etEditBirthDate;

    private ArrayAdapter<CharSequence> genderAdapter;
    private Integer gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.profile_settings_edit_personal_information));
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        etlEditName = findViewById(R.id.etlEditName);
        etEditName = findViewById(R.id.etEditName);
        etlEditSurname = findViewById(R.id.etlEditSurname);
        etEditSurname = findViewById(R.id.etEditSurname);
        etlEditGender = findViewById(R.id.etlEditGender);
        tvEditGender = findViewById(R.id.tvEditGender);
        etlEditBirthDate = findViewById(R.id.etlEditBirthDate);
        etEditBirthDate = findViewById(R.id.etEditBirthDate);

        // TODO change item layouts
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tvEditGender.setAdapter(genderAdapter);

        etEditBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditBirthDateClicked();
            }
        });

        presenter = new EditPersonalInfoPresenter(this);
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
        if (item.getItemId() == R.id.confirm) {
            etlEditName.setError(null);
            etlEditSurname.setError(null);
            etlEditGender.setError(null);
            etlEditBirthDate.setError(null);

            if (etEditName.getText() != null
                    && etEditSurname.getText() != null
                    && tvEditGender.getText() != null
                    && etEditBirthDate.getText() != null) {

                presenter.confirmEdit(etEditName.getText().toString(),
                        etEditSurname.getText().toString(),
                        genderAdapter.getPosition(tvEditGender.getText().toString()),
                        etEditBirthDate.getText().toString());
            }
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
    public void showName(String name) {
        etEditName.setText(name);
    }

    @Override
    public void showSurname(String surname) {
        etEditSurname.setText(surname);
    }

    @Override
    public void showBirthDate(String birthDate) {
        etEditBirthDate.setText(birthDate);
    }

    @Override
    public void showGender(Integer gender) {
        this.gender = gender;
        if (gender < 0 || gender > getResources().getStringArray(R.array.genders).length) {
            throw new IllegalArgumentException(TAG + " Invalid value for argument gender.");
        }

        String strGender = (String) tvEditGender.getAdapter().getItem(gender);
        tvEditGender.setText(strGender, false);
    }

    @Override
    public void showCalendar() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog birthDateDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etEditBirthDate.setText(getString(R.string.date, dayOfMonth, month, year));
                    }
                }, year, month, day);

        birthDateDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        birthDateDialog.show();

    }

    @Override
    public void setError(EditPersonalInfoContract.Presenter.InputError error) {
        switch (error) {
            case NAME_EMPTY:
                etlEditName.setError(getString(R.string.profile_settings_name_empty));
                break;
            case SURNAME_EMPTY:
                etlEditSurname.setError(getString(R.string.profile_settings_surname_empty));
                break;
            default:
                break;
        }
    }
}