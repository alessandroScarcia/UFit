package it.sms1920.spqs.ufit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import it.sms1920.spqs.ufit.contract.LauncherManagerContract;
import it.sms1920.spqs.ufit.presenter.LauncherManager;

public class LauncherActivity extends AppCompatActivity implements LauncherManagerContract.view {

    LauncherManager presenter;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Setting toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        presenter = new LauncherManager(this);

        //Setting search button
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSearchIconClick();
            }
        });


        // Setting bottom bar
        final BottomNavigationView bottomNav = findViewById(R.id.bottom_bar);
        menu = bottomNav.getMenu();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Choosing right fragment ( click on home -> choose FragmentHome, etc.. )
                switch (menuItem.getItemId()) {
                    // Home
                    case R.id.nav_home:
                        presenter.onHomeIconClick();
                        break;
                    case R.id.nav_plans:
                        presenter.onPlansIconClick();
                        break;
                    case R.id.nav_trainer:
                        presenter.onTrainerIconClick();
                        break;
                    case R.id.nav_stats:
                        presenter.onStatsIconClick();
                        break;
                    case R.id.nav_profile:
                        //qua deve stare il controllo della sessione così se è connesso mostro
                        //il profilo altrimenti il login
                        presenter.onProfileIconClick();
                        startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });
        insertHomeFragment();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void insertHomeFragment() {
        resetMenuIcons();
        menu.findItem(R.id.nav_home).setChecked(true);
        menu.findItem(R.id.nav_home).setIcon(R.drawable.baseline_home_black_48dp);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    public void insertPlansFragment() {
        resetMenuIcons();
        menu.findItem(R.id.nav_plans).setChecked(true);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.baseline_assignment_black_48dp);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlansFragment()).commit();

    }

    @Override
    public void insertTrainerFragment() {
        resetMenuIcons();
        menu.findItem(R.id.nav_trainer).setChecked(true);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.baseline_supervisor_account_black_48dp);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrainerFragment()).commit();

    }

    @Override
    public void insertStatsFragment() {
        resetMenuIcons();
        menu.findItem(R.id.nav_stats).setChecked(true);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.baseline_assessment_black_48dp);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatsFragment()).commit();

    }

    @Override
    public void insertProfileFragment() {
        resetMenuIcons();
        menu.findItem(R.id.nav_profile).setChecked(true);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.baseline_account_box_black_48dp);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();

    }

    @Override
    public void startSearchActivity() {
        startActivity(new Intent(LauncherActivity.this, SearchActivity.class));
        overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void resetMenuIcons() {
        menu.findItem(R.id.nav_home).setIcon(R.drawable.outline_home_black_48dp);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.outline_assignment_black_48dp);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.outline_assessment_black_48dp);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.outline_supervisor_account_black_48dp);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.outline_account_box_black_48dp);
    }

}
