package it.sms1920.spqs.ufit.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.sms1920.spqs.ufit.launcher.home.HomeFragment;
import it.sms1920.spqs.ufit.launcher.userprofile.choose.ChooseFragment;
import it.sms1920.spqs.ufit.launcher.userprofile.login.LoginActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.show.ProfileFragment;
import it.sms1920.spqs.ufit.launcher.search.SearchActivity;
import it.sms1920.spqs.ufit.launcher.userstats.StatsFragment;
import it.sms1920.spqs.ufit.launcher.trainer.TrainerFragment;
import it.sms1920.spqs.ufit.launcher.workoutplan.showlist.WorkoutPlansFragment;

public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {

    LauncherPresenter presenter;
    Menu menu;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Setting toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        presenter = new LauncherPresenter(this);


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
                        presenter.onHomeIconClicked();
                        break;
                    case R.id.nav_plans:
                        presenter.onPlansIconClicked();
                        break;
                    case R.id.nav_trainer:
                        presenter.onTrainerIconClicked();
                        break;
                    case R.id.nav_stats:
                        presenter.onStatsIconClicked();
                        break;
                    case R.id.nav_profile:
                        presenter.onProfileIconClicked();
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
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        toolbar.getMenu().findItem(R.id.search).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                presenter.onSearchIconClicked();
                return true;
            case R.id.logout:
                presenter.onLogOutIconClicked();
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
    public void insertChooseFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        menu.findItem(R.id.nav_profile).setChecked(true);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.ic_menu_account_selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChooseFragment()).commit();
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


    private void resetMenuIcons() {
        menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_menu_home_normal);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.ic_menu_plans_normal);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.ic_menu_stats_normal);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.ic_menu_trainer_normal);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.ic_menu_account_normal);
    }

    private void resetToolbarIcons() {
        toolbar.getMenu().findItem(R.id.logout).setVisible(false);
        toolbar.getMenu().findItem(R.id.search).setVisible(false);
        toolbar.getMenu().findItem(R.id.add).setVisible(false);
    }



}
