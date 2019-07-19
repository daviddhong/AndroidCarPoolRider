package android.carpoolrider.RequestRides.RequestNewRide.Later.Confirm;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LaterConfirmActivity extends AppCompatActivity {

    ImageView backRequestNewRideConfirmActivity;
    RelativeLayout editPassengerNumber;
    RelativeLayout editTime;
    RelativeLayout editDate;
    RelativeLayout confirmActivityRelativeLayout;
    RelativeLayout cancelActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_confirm_later);

        // EFFECTS: Call setBackRequestNewRideConfirmActivity.
        setBackRequestNewRideConfirmActivity();

        // EFFECTS: Call setEditPassengerNumber.
        setEditPassengerNumber();

        // EFFECTS: Call setEditTime.
        setEditTime();

        // EFFECTS: Call setEditDate.
        setEditDate();

        // EFFECTS: Call setConfirmActivityRelativeLayout.
        setConfirmActivityRelativeLayout();

        // EFFECTS: Call setCancelActivityRelativeLayout.
        setCancelActivityRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideConfirmActivity() {
        backRequestNewRideConfirmActivity = (ImageView) findViewById(R.id.ic_back_activity_request_new_ride_later_confirm);
        backRequestNewRideConfirmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from Activity.this to new Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set EditPassengerNumber.
    private void setEditPassengerNumber() {
        editPassengerNumber = (RelativeLayout) findViewById(R.id.passenger_number);
        editPassengerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterConfirmActivity.this,
                        ConfirmEditPassengerNumberActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set EditTime.
    private void setEditTime() {
        editTime = (RelativeLayout) findViewById(R.id.time_confirm);
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterConfirmActivity.this,
                        ConfirmEditWhenActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set EditDate.
    private void setEditDate() {
        editDate = (RelativeLayout) findViewById(R.id.date_confirm);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterConfirmActivity.this,
                        ConfirmEditWhenActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // EFFECTS: Set ConfirmNewRideRequest.
    private void setConfirmActivityRelativeLayout() {
        confirmActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_confirm_later);
        confirmActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // EFFECTS: Set CancelNewRideRequest.
    private void setCancelActivityRelativeLayout() {
        cancelActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_cancel_later);
        cancelActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
