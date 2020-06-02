package it.sms1920.spqs.ufit.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.sms1920.spqs.ufit.model.User;

public class RegistrationActivity extends AppCompatActivity {

    private User userReg;
    private EditText nameReg;
    private EditText surnameReg;
    private EditText dateBirthReg;
    private EditText weightReg;
    private EditText heightReg;
    private RadioGroup radioGroupChooseGender;
    private RadioButton genderReg;
    private EditText emailReg;
    private EditText passwordReg;
    private Button applyBtn;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameReg = findViewById( R.id.editName );
        surnameReg = findViewById( R.id.editSurname );
        dateBirthReg = findViewById( R.id.editDate );
        weightReg = findViewById( R.id.editWeight );
        heightReg = findViewById( R.id.editHeight );
        radioGroupChooseGender = findViewById( R.id.chooseGender );
        emailReg = findViewById( R.id.editEmail );
        passwordReg = findViewById( R.id.editPassword );
        applyBtn = findViewById( R.id.btnDo );

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        applyBtn.setOnClickListener(new View.OnClickListener() {
            String name;
            String surname;
            String gender;
            Date date;
            int weight;
            int height;
            String email;
            String password;
            @Override
            public void onClick(View v) {
                SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yy" );
                name = nameReg.getText().toString();
                surname = surnameReg.getText().toString();
                String dateString = dateBirthReg.getText().toString();
                date = null;
                try {
                    date = dataFormatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                weight = Integer.parseInt(weightReg.getText().toString());
                height = Integer.parseInt( heightReg.getText().toString() );
                int radioId = radioGroupChooseGender.getCheckedRadioButtonId();
                genderReg = findViewById( radioId );
                gender = genderReg.getText().toString();
                email = emailReg.getText().toString();
                password = passwordReg.getText().toString();

                firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    userReg = new User( name, surname, gender, date, weight, height );

                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(userReg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(RegistrationActivity.this,"All right", Toast.LENGTH_SHORT);
                                            startActivity( new Intent(getApplicationContext(), LauncherActivity.class));
                                        }
                                    });
                                }
                            }
                        });

            }
        });


    }

    public void checkButton(View v ){
        int radioId = radioGroupChooseGender.getCheckedRadioButtonId();

        genderReg = findViewById( radioId );
    }
}
