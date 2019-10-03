package android.carpoolrider.AppFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.carpoolrider.AppFragments.ARidesAvailable.ARidesAvailableFragment;
import android.carpoolrider.AppFragments.BRequestRides.BRequestRidesFragment;
import android.carpoolrider.AppFragments.CConfirmedRides.CConfirmedRidesFragment;
import android.carpoolrider.AppFragments.DPendingRequests.DPendingRequestFragment;
import android.carpoolrider.AppFragments.ESettings.ESettingsFragment;
import android.carpoolrider.R;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ZBottomNavigationMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_bottom_navigation_main);
        // EFFECTS: Call initFragment.
        initFragment();
        // EFFECTS: Call bottomNavigationView.
        bottomNavigationView();
    }

    // EFFECTS: Replace ZBottomNavigationMainActivity with ARidesAvailableFragment.
    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new ARidesAvailableFragment()).commit();
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
                    fragment = new ARidesAvailableFragment();
                    break;
                case R.id.request_rides:
                    fragment = new BRequestRidesFragment();
                    break;
                case R.id.reserved_rides:
                    fragment = new CConfirmedRidesFragment();
                    break;
                case R.id.rate_drivers:
                    fragment = new DPendingRequestFragment();
                    break;
                case R.id.more:
                    fragment = new ESettingsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment).commit();
            return true;
        }
    };
}
