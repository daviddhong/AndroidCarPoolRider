package android.carpoolrider.RequestRides.RequestNewRide.Later.Confirm;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LaterConfirmActivity extends AppCompatActivity {

    ImageView mBackRequestNewRideConfirmActivity;

    RelativeLayout mEditPassengerNumber;
    RelativeLayout mEditTime;
    RelativeLayout mEditDate;
    RelativeLayout mConfirmActivityRelativeLayout;
    RelativeLayout mCancelActivityRelativeLayout;

    TextView mPassengerNumberConfirm;
    TextView mTimeConfirm;
    TextView mDateConfirm;

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
        mBackRequestNewRideConfirmActivity = (ImageView) findViewById(R.id.ic_back_activity_request_new_ride_later_confirm);
        mBackRequestNewRideConfirmActivity.setOnClickListener(new View.OnClickListener() {
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
        mEditPassengerNumber = (RelativeLayout) findViewById(R.id.passenger_number);
        mEditPassengerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterConfirmActivity.this,
                        ConfirmPassengerNumberActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // EFFECTS: Retrieve data from LaterFemaleOnlyActivity, which got the data from
        // LaterPassengerNumberActivity.
        mPassengerNumberConfirm = (TextView) findViewById(R.id.text_view_passenger_number_confirm);
        Bundle bundle = getIntent().getExtras();
        String passengerNumber = bundle.getString("PASSENGER_NUMBER_VALUE");
        mPassengerNumberConfirm.setText(passengerNumber);
    }

    // MODIFIES: this
    // EFFECTS: Set EditTime.
    private void setEditTime() {
        mEditTime = (RelativeLayout) findViewById(R.id.time_confirm);
        mEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterConfirmActivity.this,
                        ConfirmWhenActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // EFFECTS: Retrieve the data from LaterFemaleOnlyActivity, which got the data from
        // LaterPassengerNumberActivity, which got the data from LaterWhenActivity.
        mTimeConfirm = (TextView) findViewById(R.id.time);
        Bundle bundle = getIntent().getExtras();
        String time = bundle.getString("TIME_VALUE_LFO");
        mTimeConfirm.setText(time);
    }

    // MODIFIES: this
    // EFFECTS: Set EditDate.
    private void setEditDate() {
        mEditDate = (RelativeLayout) findViewById(R.id.date_confirm);
        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterConfirmActivity.this,
                        ConfirmWhenActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // EFFECTS: Retrieve the data from LaterFemaleOnlyActivity, which got the data from
        // LaterPassengerNumberActivity, which got the data rom LaterWhenActivity.
        mDateConfirm = (TextView) findViewById(R.id.date);
        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("DATE_VALUE_LFO");
        mDateConfirm.setText(date);
    }

    // EFFECTS: Set ConfirmNewRideRequest.
    private void setConfirmActivityRelativeLayout() {
        mConfirmActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_confirm_later);
        mConfirmActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // EFFECTS: Set CancelNewRideRequest.
    private void setCancelActivityRelativeLayout() {
        mCancelActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_cancel_later);
        mCancelActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
