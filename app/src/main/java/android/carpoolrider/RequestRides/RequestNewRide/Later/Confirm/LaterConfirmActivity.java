package android.carpoolrider.RequestRides.RequestNewRide.Later.Confirm;

import android.carpoolrider.MainActivity;
import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.Later.LaterDestinationActivity;
import android.carpoolrider.RequestRides.RequestNewRide.Later.LaterOriginActivity;
import android.carpoolrider.RequestRides.RequestNewRide.Later.LaterPassengerNumberActivity;
import android.carpoolrider.RequestRides.RequestRidesFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
    TextView mOrigin;
    TextView mDestination;

    String currentUserName;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference RootRef, RootKeyRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_confirm_later);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUserName = currentUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets").child(currentUserName);

        // EFFECTS: Call setBackRequestNewRideConfirmActivity.
        setBackRequestNewRideConfirmActivity();

        // EFFECTS: Call setEditPassengerNumber.
        setEditPassengerNumber();

        // EFFECTS: Call setEditTime.
        setEditTime();

        // EFFECTS: Call setEditDate.
        setEditDate();

        // EFFECTS: Call setEditOrigin.
        setEditOrigin();

        // EFFECTS: Call setEditDestination.
        setEditDestination();

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

        // EFFECTS: Retrieve data from LaterPassengerNumberActivity.
        mPassengerNumberConfirm = (TextView) findViewById(R.id.text_view_passenger_number_confirm);
        Bundle bundle = getIntent().getExtras();
        String passengerNumber = bundle.getString("PASSENGER_NUMBER_SELECTED");
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

        // EFFECTS: Retrieve the data LaterPassengerNumberActivity, which got the data from
        // LaterWhenActivity.
        mTimeConfirm = (TextView) findViewById(R.id.time);
        Bundle bundle = getIntent().getExtras();
        String time = bundle.getString("TIME_VALUE");
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

        // EFFECTS: Retrieve the data from LaterPassengerNumberActivity, which got the data rom
        // LaterWhenActivity.
        mDateConfirm = (TextView) findViewById(R.id.date);
        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("DATE_VALUE");
        mDateConfirm.setText(date);
    }

    private void setEditOrigin() {
        mOrigin = (TextView) findViewById(R.id.text_view_confirm_origin);
        Bundle bundle = getIntent().getExtras();
        String origin = bundle.getString("ORIGIN");
        mOrigin.setText(origin);
    }

    private void setEditDestination() {
        mDestination = (TextView) findViewById(R.id.text_view_confirm_destination);
        Bundle bundle = getIntent().getExtras();
        String destination = bundle.getString("DESTINATION");
        mDestination.setText(destination);
    }

    // EFFECTS: Set ConfirmNewRideRequest.
    private void setConfirmActivityRelativeLayout() {
        mConfirmActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_confirm_later);
        mConfirmActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

//            Bundle bundle = getIntent().getExtras();
//            String origin = bundle.getString("ORIGIN");
//            String destination = bundle.getString("DESTINATION");
//            String date = bundle.getString("DATE_VALUE");
//            String time = bundle.getString("TIME_VALUE");
//            String passengerNumber = bundle.getString("PASSENGER_NUMBER_SELECTED");

            @Override
            public void onClick(View v) {
                //save to realtime database

                savetorealtimedatabase();

                Intent intent = new Intent(LaterConfirmActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void savetorealtimedatabase() {
        String messageKey = RootRef.push().getKey();
        HashMap<String, Object> riderTicketKey = new HashMap<>();
        RootRef.updateChildren(riderTicketKey);

        RootKeyRef = RootRef.child(messageKey);

        String currentUserID = mAuth.getCurrentUser().getUid();
        HashMap<String, Object> profileMap = new HashMap<>();
        profileMap.put("uid", currentUserID);
        profileMap.put("From", "from");
        profileMap.put("To", "to");
        profileMap.put("NumberOfSeats", "seat#");
        profileMap.put("Price", "$9");
        profileMap.put("Date", "Jan/02/20");
        RootKeyRef.updateChildren(profileMap);
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
