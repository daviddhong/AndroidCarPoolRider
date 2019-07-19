package android.carpoolrider.RequestRides.RequestNewRide.Later.PassengerNumber;

import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.Later.LaterFemaleOnlyActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class LaterPassengerNumberActivity extends AppCompatActivity {

    ImageView backRequestNewRidePassengerNumberActivity;

    TextView passengerNumber;

    RelativeLayout nextActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_pn_later);

        // EFFECTS: Call setBackRequestNewRidePassengerNumberActivity.
        setBackRequestNewRidePassengerActivity();

        // EFFECTS: Call passengerNumberActivity;
        setPassengerNumber();

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

    // EFFECTS: Set PassengerNumberPicker.
    private void setPassengerNumber() {
        passengerNumber = (TextView) findViewById(R.id.passenger_number);
        passengerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterPassengerNumberActivity.this,
                        SelectPassengerNumberActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    // EFFECTS: Set OnClickActivity for nextActivity
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_next_passenger_number);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterPassengerNumberActivity.this,
                        LaterFemaleOnlyActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
