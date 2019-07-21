package android.carpoolrider.RequestRides.RequestNewRide.Later;

import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.Later.Confirm.LaterConfirmActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LaterFemaleOnlyActivity extends AppCompatActivity {

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

    // MODIFIES: this
    // EFFECTS: Set OnClickActivity for nextActivity
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_female_only_next);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            // EFFECTS: Data retrieved from LaterPassengerNumberActivity for the number of
            // passengers, date and time.
            Bundle bundle = getIntent().getExtras();
            String date = bundle.getString("DATE_VALUE");
            String time = bundle.getString("TIME_VALUE");
            String selected = bundle.getString("PASSENGER_NUMBER_SELECTED");

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterFemaleOnlyActivity.this,
                        LaterConfirmActivity.class);
                // EFFECTS: Send the passenger number data to LaterConfirmActivity.
                intent.putExtra("PASSENGER_NUMBER_VALUE", selected);
                // EFFECTS: Send the date of the carpool to LaterConfirmActivity.
                intent.putExtra("DATE_VALUE_LFO", date);
                // EFFECTS: Send the time of the carpool to LaterConfirmActivity.
                intent.putExtra("TIME_VALUE_LFO", time);
                startActivity(intent);
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
