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
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Setting toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        presenter = new LauncherManager(this);


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
                        presenter.onProfileIconClick();
                        break;
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar.
        getMenuInflater().inflate(R.menu.tool_bar_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                presenter.onSearchIconClick();
                return true;
            case R.id.logout:
                presenter.onLogOutIconClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void insertHomeFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        toolbar.getMenu().findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.nav_home).setChecked(true);
        menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_menu_home_selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    public void insertPlansFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        menu.findItem(R.id.nav_plans).setChecked(true);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.ic_menu_plans_selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutPlansFragment()).commit();

    }

    @Override
    public void insertTrainerFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        menu.findItem(R.id.nav_trainer).setChecked(true);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.ic_menu_trainer_selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrainerFragment()).commit();

    }

    @Override
    public void insertStatsFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        menu.findItem(R.id.nav_stats).setChecked(true);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.ic_menu_stats_selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatsFragment()).commit();

    }

    @Override
    public void insertProfileFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        toolbar.getMenu().findItem(R.id.logout).setVisible(true);
        menu.findItem(R.id.nav_profile).setChecked(true);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.ic_menu_account_selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }

    @Override
    public void startSearchActivity() {
        startActivity(new Intent(LauncherActivity.this, SearchActivity.class));
        overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void resetActivity() {
        startActivity(new Intent(this, LauncherActivity.class));
        finish();
    }

    public void resetMenuIcons() {
        menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_menu_home_normal);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.ic_menu_plans_normal);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.ic_menu_stats_normal);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.ic_menu_trainer_normal);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.ic_menu_account_normal);
    }

    public void resetToolbarIcons() {
        toolbar.getMenu().findItem(R.id.logout).setVisible(false);
        toolbar.getMenu().findItem(R.id.search).setVisible(false);
    }

}
