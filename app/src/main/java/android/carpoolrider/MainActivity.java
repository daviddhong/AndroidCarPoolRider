package android.carpoolrider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.carpoolrider.AvaliableRides.AvailableRidesFragment;
import android.carpoolrider.Profile.ProfileFragment;
import android.carpoolrider.RateDrivers.RateDriversFragment;
import android.carpoolrider.RequestRides.RequestRidesFragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EFFECTS: Call initFragment.
        initFragment();

        // EFFECTS: Call bottomNavigationView.
        bottomNavigationView();

    }

    // EFFECTS: Replace MainActivity with AvailableRidesFragment.
    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new AvailableRidesFragment()).commit();
    }

    // EFFECTS: Initialize BottomNavigationView.
    private void bottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    // EFFECTS: Helper function for BottomNavigationView click events.
    private BottomNavigationView.OnNavigationItemSelectedListener
            onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.available_rides:
                    fragment = new AvailableRidesFragment();
                    break;
                case R.id.request_rides:
                    fragment = new RequestRidesFragment();
                    break;
                case R.id.rate_drivers:
                    fragment = new RateDriversFragment();
                    break;
                case R.id.profile:
                    fragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment).commit();
            return true;
        }
    };
}
