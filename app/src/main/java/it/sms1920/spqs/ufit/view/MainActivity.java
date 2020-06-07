package it.sms1920.spqs.ufit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import it.sms1920.spqs.ufit.contract.AppLoading;
import it.sms1920.spqs.ufit.presenter.Loading;

public class MainActivity extends AppCompatActivity implements AppLoading.View {

    private AppLoading.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Loading(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.appInitialized();
    }

    @Override
    public void showLauncher() {
        startActivity(new Intent(MainActivity.this, LauncherActivity.class));
        finish();
    }

}