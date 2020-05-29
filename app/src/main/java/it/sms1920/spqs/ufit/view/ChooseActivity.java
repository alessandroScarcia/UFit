package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.sms1920.spqs.ufit.R;

public class ChooseActivity extends AppCompatActivity {

    private Button buttonSignUp;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        buttonSignIn = findViewById(R.id.signInChooseBtn);
        buttonSignUp = findViewById(R.id.btnSignUpChoose);

        buttonSignUp.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        buttonSignIn.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this, LoginActivity.class));
                finish();
            }
        });


    }
}
