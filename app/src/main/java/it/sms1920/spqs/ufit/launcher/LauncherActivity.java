package it.sms1920.spqs.ufit.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import it.sms1920.spqs.ufit.launcher.home.HomeFragment;
import it.sms1920.spqs.ufit.launcher.search.SearchActivity;
import it.sms1920.spqs.ufit.launcher.toolworkout.TimerFragment;
import it.sms1920.spqs.ufit.launcher.trainer.BluetoothLinkingFragment;
import it.sms1920.spqs.ufit.launcher.traineradvice.AdviceActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.choose.ChooseFragment;
import it.sms1920.spqs.ufit.launcher.userprofile.settings.ProfileSettingsFragment;
import it.sms1920.spqs.ufit.launcher.userprofile.show.ProfileFragment;
import it.sms1920.spqs.ufit.launcher.userstats.StatsFragment;
import it.sms1920.spqs.ufit.launcher.workoutplan.create.CreatingWorkoutActivity;
import it.sms1920.spqs.ufit.launcher.workoutplan.showlist.WorkoutPlansFragment;

public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {
    private LauncherContract.Presenter presenter;
    private Menu menu;
    private Toolbar toolbar;
    private BottomNavigationView bottombar;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Setting toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        logo = findViewById(R.id.logo);

        presenter = new LauncherPresenter(this);


        // Setting bottom bar
        bottombar = findViewById(R.id.bottom_bar);
        menu = bottombar.getMenu();

        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        logo.setVisibility(View.VISIBLE);
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
                break;
            case R.id.logout:
                presenter.onLogOutIconClicked();
                break;
            case R.id.profile_settings:
                presenter.onProfileSettingsClicked();
                break;
            case R.id.edit:
                presenter.onEditIconClicked();
                break;
            case R.id.timer:
                presenter.onTimerIconClicked();
                break;
            case R.id.adviceTrainer:
                presenter.onAdviceTrainerIconClicked();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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
        setMenuItemIcon(R.id.nav_home, R.drawable.ic_menu_home_selected);
        logo.setVisibility(View.VISIBLE);
        toggleToolbarNavigationButton(false);
        setToolbarTitle("");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    public void insertPlansFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        setMenuItemIcon(R.id.nav_plans, R.drawable.ic_menu_plans_selected);
        logo.setVisibility(View.GONE);
        toggleToolbarNavigationButton(false);
        setToolbarTitle(getString(R.string.workoutplan));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutPlansFragment()).commit();

    }

    @Override
    public void insertTrainerFragment() {
        insertChooseFragment();
    }

    @Override
    public void insertStatsFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        setMenuItemIcon(R.id.nav_stats, R.drawable.ic_menu_stats_selected);
        logo.setVisibility(View.GONE);
        toggleToolbarNavigationButton(false);
        setToolbarTitle(getString(R.string.stats));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatsFragment()).commit();
    }

    @Override
    public void insertProfileFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        setMenuItemIcon(R.id.nav_profile, R.drawable.ic_menu_account_selected);
        toolbar.getMenu().findItem(R.id.logout).setVisible(true);
        toolbar.getMenu().findItem(R.id.profile_settings).setVisible(true);
        logo.setVisibility(View.GONE);
        toggleToolbarNavigationButton(false);
        setToolbarTitle(getString(R.string.profile));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }

    @Override
    public void insertProfileSettingsFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        setMenuItemIcon(R.id.nav_profile, R.drawable.ic_menu_account_selected);
        logo.setVisibility(View.GONE);
        toggleToolbarNavigationButton(true);
        setToolbarTitle(getString(R.string.profile_settings));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileSettingsFragment()).commit();
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
        setMenuItemIcon(R.id.nav_profile, R.drawable.ic_menu_account_selected);
        logo.setVisibility(View.GONE);
        toggleToolbarNavigationButton(false);
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

    @Override
    public String getWorkoutId() {
        TextView id = findViewById(R.id.workoutId);
        return id.getText().toString();
    }

    @Override
    public void startEditWorkoutActivity(String id) {
        Intent intent = new Intent(this, CreatingWorkoutActivity.class);
        intent.putExtra("workoutId", id);
        intent.putExtra("workoutName", toolbar.getTitle().toString());
        startActivity(intent);
    }


    private void setMenuItemIcon(int item, int icon) {
        menu.findItem(item).setChecked(true);
        menu.findItem(item).setIcon(icon);
    }

    private void resetMenuIcons() {
        menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_menu_home_normal);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.ic_menu_plans_normal);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.ic_menu_stats_normal);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.ic_menu_trainer_normal);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.ic_menu_account_normal);
    }

    private void resetToolbarIcons() {
        toolbar.getMenu().findItem(R.id.search).setVisible(false);
        toolbar.getMenu().findItem(R.id.profile_settings).setVisible(false);
        toolbar.getMenu().findItem(R.id.logout).setVisible(false);
        toolbar.getMenu().findItem(R.id.add).setVisible(false);
        toolbar.getMenu().findItem(R.id.confirm).setVisible(false);
        toolbar.getMenu().findItem(R.id.timer).setVisible(false);
        toolbar.getMenu().findItem(R.id.edit).setVisible(false);
        toolbar.getMenu().findItem(R.id.adviceTrainer).setVisible(false);
        toolbar.getMenu().findItem(R.id.maximalTest).setVisible(false);
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }


    public void toggleToolbarNavigationButton(boolean active) {
        if (active) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        } else {
            toolbar.setNavigationIcon(null);
        }
    }

    public void setToolbarNavigationButtonClickListener(View.OnClickListener clickListener) {
        toolbar.setNavigationOnClickListener(clickListener);
    }

    public void showPlanClicked() {
        presenter.onShowPlanClicked();
    }

    public void showToolbarEditIcon(boolean visible) {
        toolbar.getMenu().findItem(R.id.edit).setVisible(visible);
    }

    public void showToolbarTimerIcon(boolean visible) {
        toolbar.getMenu().findItem(R.id.timer).setVisible(visible);
    }

    public void insertBluetoothLinkingFragment() {
        resetMenuIcons();
        resetToolbarIcons();
        setMenuItemIcon(R.id.nav_trainer, R.drawable.ic_menu_trainer_selected);
        toolbar.getMenu().findItem(R.id.adviceTrainer).setVisible(true);
        logo.setVisibility(View.GONE);
        toggleToolbarNavigationButton(false);
        setToolbarTitle(getString(R.string.trainer));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BluetoothLinkingFragment()).commit();
    }

    @Override
    public String getLoginRequiredString() {
        return getString(R.string.login_required);
    }


    @Override
    public void lockView() {
        toolbar.getMenu().findItem(R.id.logout).setEnabled(false);
        toolbar.getMenu().findItem(R.id.profile_settings).setEnabled(false);

        bottombar.getMenu().findItem(R.id.nav_home).setEnabled(false);
        bottombar.getMenu().findItem(R.id.nav_plans).setEnabled(false);
        bottombar.getMenu().findItem(R.id.nav_trainer).setEnabled(false);
        bottombar.getMenu().findItem(R.id.nav_stats).setEnabled(false);
        bottombar.getMenu().findItem(R.id.nav_profile).setEnabled(false);
    }

    @Override
    public void startAdviceTrainerActivity() {
        startActivity(new Intent(this, AdviceActivity.class));
    }

    @Override
    public void startTimer() {
        TimerFragment dialogBox = new TimerFragment();
        dialogBox.show(getSupportFragmentManager(), "example dialog");
    }

    public void showToolbarMaximalTestIcon(boolean visible) {
        toolbar.getMenu().findItem(R.id.maximalTest).setVisible(visible);
    }
}
