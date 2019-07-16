package android.carpoolrider.RequestRides.RequestNewRide.Later;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RequestNewRideFemaleOnlyLaterActivity extends AppCompatActivity {

    ImageView backRequestNewRideFemaleOnlyActivity;
    RelativeLayout nextActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_female_only_later);

        // EFFECTS: Call setBackRequestNewRideFemaleOnlyActivity.
        setBackRequestNewRideFemaleOnlyActivity();

        // EFFECTS: Call setNextActivity.
        setNextActivityRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideFemaleOnlyActivity() {
        backRequestNewRideFemaleOnlyActivity = (ImageView) findViewById(R.id.ic_back_arrow_request_new_ride_female_only);
        backRequestNewRideFemaleOnlyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    // EFFECTS: Set OnClickActivity for nextActivity
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_female_only_next);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestNewRideFemaleOnlyLaterActivity.this,
                        RequestNewRideConfirmLaterActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
