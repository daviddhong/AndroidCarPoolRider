package android.carpoolrider.RequestRides.RequestNewRide.Later;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RequestNewRidePassengerNumberLaterActivity extends AppCompatActivity {

    ImageView backRequestNewRidePassengerNumberActivity;
    RelativeLayout nextActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_passenger_number_later);

        // EFFECTS: Call setBackRequestNewRidePassengerNumberActivity.
        setBackRequestNewRidePassengerActivity();

        // EFFECTS: Call setNextActivity.
        setNextActivityRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRidePassengerActivity() {
        backRequestNewRidePassengerNumberActivity = (ImageView) findViewById(R.id.ic_back_arrow_request_new_ride_passenger_number);
        backRequestNewRidePassengerNumberActivity.setOnClickListener(new View.OnClickListener() {
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
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_next_passenger_number);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestNewRidePassengerNumberLaterActivity.this,
                        RequestNewRideFemaleOnlyLaterActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
