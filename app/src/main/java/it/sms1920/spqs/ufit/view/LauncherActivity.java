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
import androidx.fragment.app.Fragment;

import it.sms1920.spqs.ufit.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Setting toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //ImageView img = findViewById(R.id.logo);
        //img.getLayoutParams().height = (int) getResources().getDimension(R.dimen.toolbar_height);

        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new FloatingActionButton.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, SearchActivity.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.idle);
            }
        });


        // Starting with Home at the launch
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        // Setting bottom bar
        final BottomNavigationView bottomNav = findViewById(R.id.bottom_bar);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                Menu menu = bottomNav.getMenu();

                // Choosing right fragment ( click on home -> choose FragmentHome, etc.. )
                switch (menuItem.getItemId()) {

                    // Home
                    case R.id.nav_home:
                        resetBottomIcons(menu);
                        // Marking current item icon
                        menu.findItem(R.id.nav_home).setIcon(R.drawable.baseline_home_black_48dp);
                        selectedFragment = new HomeFragment();
                        break;

                    // Plans
                    case R.id.nav_plans:
                        resetBottomIcons(menu);
                        // Marking current item icon
                        menu.findItem(R.id.nav_plans).setIcon(R.drawable.baseline_assignment_black_48dp);
                        selectedFragment = new PlansFragment();
                        break;

                    // Trainer
                    case R.id.nav_trainer:
                        resetBottomIcons(menu);
                        // Marking current item icon
                        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.baseline_supervisor_account_black_48dp);
                        selectedFragment = new TrainerFragment();
                        break;

                    // Stats
                    case R.id.nav_stats:
                        resetBottomIcons(menu);
                        // Marking current item icon
                        menu.findItem(R.id.nav_stats).setIcon(R.drawable.baseline_assessment_black_48dp);
                        selectedFragment = new StatsFragment();
                        break;

                    // Profile
                    case R.id.nav_profile:
                        resetBottomIcons(menu);
                        menu.findItem(R.id.nav_profile).setIcon(R.drawable.baseline_account_box_black_48dp);

                        startActivity(new Intent(LauncherActivity.this, ChooseActivity.class));
                        finish();
                        selectedFragment = new ProfileFragment();
                        break;
                }

                // Replacing old fragment with the selected one
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;

            }
        });



        /*final FloatingActionButton profile = findViewById(R.id.btnProfile);
        profile.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLauncher.this, Profile.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

            }
        });*/

    }

    /**
     * Unmark all bottom bar icons to their outline version, usefull in marking only selected one item icon in bottomBarListener
     *
     * @param menu Bottom bar menu
     */
    private void resetBottomIcons(Menu menu) {
        menu.findItem(R.id.nav_home).setIcon(R.drawable.outline_home_black_48dp);
        menu.findItem(R.id.nav_plans).setIcon(R.drawable.outline_assignment_black_48dp);
        menu.findItem(R.id.nav_stats).setIcon(R.drawable.outline_assessment_black_48dp);
        menu.findItem(R.id.nav_trainer).setIcon(R.drawable.outline_supervisor_account_black_48dp);
        menu.findItem(R.id.nav_profile).setIcon(R.drawable.outline_account_box_black_48dp);
    }

}
