package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imgLogo;
    Animation animation;
    private static int splashTimeOut = 2000;//1 s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imgLogo = findViewById(R.id.logo);
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation);
        imgLogo.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LauncherActivity.class));
                overridePendingTransition(R.anim.idle, R.anim.idle);
                finish();
            }
        }, splashTimeOut);
    }
}