package android.carpoolrider;

import android.carpoolrider.AppFragments.ARidesAvailable.ARidesAvailableFragment;
import android.carpoolrider.AppFragments.BRequestRides.BRequestRidesFragment;
import android.carpoolrider.AppFragments.CConfirmedRides.CConfirmedRidesFragment;
import android.carpoolrider.AppFragments.DPendingRequests.DPendingRequestFragment;
import android.carpoolrider.AppFragments.ESettings.ESettingsFragment;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        initToolbar();

        initFragment();

        NavigationView();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.main));
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new ARidesAvailableFragment()).commit();
    }

    private void NavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_carpool_requests:
                fragment = new ARidesAvailableFragment();
                break;
            case R.id.nav_sent_carpool_requests:
//                fragment = new ();
                break;
            case R.id.nav_post_carpool:
                fragment = new BRequestRidesFragment();
                break;
            case R.id.nav_your_carpool:
                fragment = new CConfirmedRidesFragment();
                break;
            case R.id.nav_reply:
                fragment = new DPendingRequestFragment();
                break;
            case R.id.nav_more:
                fragment = new ESettingsFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}

