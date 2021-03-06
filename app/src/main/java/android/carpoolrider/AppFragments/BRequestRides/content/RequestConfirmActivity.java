package android.carpoolrider.AppFragments.BRequestRides.content;

//import android.carpoolrider.AppFragments.ARidesAvailable.content.IndividualDriverRequestActivity;
//import android.carpoolrider.AppFragments.BottomNavigationMainActivity;
import android.carpoolrider.NavigationDrawerMainActivity;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RequestConfirmActivity extends AppCompatActivity {

    RelativeLayout mBackRequestNewRideConfirmActivity;
    RelativeLayout mConfirmActivityRelativeLayout;
    RelativeLayout mCancelActivityRelativeLayout;

    TextView mPassengerNumberConfirm;
    TextView mTimeConfirm;
    TextView mDateConfirm;
    TextView mOrigin;
    TextView mDestination;
    TextView mCosts;
    String messageKey;
    String currentUserName;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference RootRef, RootKeyRef;
    private String passengerNumber, time, date, origin, destination, earnings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_brequestrides_content_activity_request_confirm);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUserName = currentUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");

        // EFFECTS: Call setBackRequestNewRideConfirmActivity.
        setBackRequestNewRideConfirmActivity();

        // EFFECTS: Call setPassengerNumber.
        setPassengerNumber();

        // EFFECTS: Call setTime.
        setTime();

        // EFFECTS: Call setDate.
        setDate();

        // EFFECTS: Call setOrigin.
        setOrigin();

        // EFFECTS: Call setDestination.
        setDestination();

        // EFFECTS: Call setCosts.
        setCost();

        // EFFECTS: Call setConfirmActivityRelativeLayout.
        setConfirmActivityRelativeLayout();

        // EFFECTS: Call setCancelActivityRelativeLayout.
        setCancelActivityRelativeLayout();
    }

    private void saveToRealTimeDatabase() {
        messageKey = RootRef.push().getKey();
        HashMap<String, Object> riderTicketKey = new HashMap<>();
        RootRef.updateChildren(riderTicketKey);
        RootKeyRef = RootRef.child(messageKey);
        String currentUserID = mAuth.getCurrentUser().getUid();
        HashMap<String, Object> profileMap = new HashMap<>();
        profileMap.put("uid", currentUserID);
        profileMap.put("From", origin);
        profileMap.put("To", destination);
        profileMap.put("NumberOfSeats", passengerNumber);
        profileMap.put("Price", earnings);
        profileMap.put("Date", date);
        profileMap.put("Time", time);
        profileMap.put("status", "0");
        profileMap.put("status_uid", "0"+currentUserID);
        RootKeyRef.updateChildren(profileMap);
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideConfirmActivity() {
        mBackRequestNewRideConfirmActivity = (RelativeLayout) findViewById(R.id.relative_layout_ic_back_activity_confirm);
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
    private void setPassengerNumber() {

        // EFFECTS: Retrieve data from RequestPassengerNumberActivity.
        mPassengerNumberConfirm = (TextView) findViewById(R.id.text_view_passenger_number_confirm);
        Bundle bundle = getIntent().getExtras();
        passengerNumber = bundle.getString("SEATS_STRING");
        mPassengerNumberConfirm.setText(passengerNumber);
    }

    // MODIFIES: this
    // EFFECTS: Set EditTime.
    private void setTime() {
        // EFFECTS: Retrieve the data RequestPassengerNumberActivity, which got the data from
        // RequestDateActivity.
        mTimeConfirm = (TextView) findViewById(R.id.time);
        Bundle bundle = getIntent().getExtras();
        String hour = bundle.getString("HOUR_STRING");
        String minutes = bundle.getString("MINUTES_STRING");
        String period = bundle.getString("PERIOD_STRING");
        time = hour + " " + ":" + " " + minutes + " " + period;
        mTimeConfirm.setText(time);
    }

    // MODIFIES: this
    // EFFECTS: Set EditDate.
    private void setDate() {

        // EFFECTS: Retrieve the data from RequestPassengerNumberActivity, which got the data rom
        // RequestDateActivity.
        mDateConfirm = (TextView) findViewById(R.id.date);
        Bundle bundle = getIntent().getExtras();
        String month = bundle.getString("MONTH_STRING");
        String day = bundle.getString("DAY_STRING");
        String year = bundle.getString("YEAR_STRING");
        date = month + " " + day + ", " + year;
        mDateConfirm.setText(date);
    }

    private void setOrigin() {
        mOrigin = (TextView) findViewById(R.id.text_view_confirm_origin);
        Bundle bundle = getIntent().getExtras();
        origin = bundle.getString("ORIGIN_LOCATION_STRING_KEY");
        mOrigin.setText(origin);
    }

    private void setDestination() {
        mDestination = (TextView) findViewById(R.id.text_view_confirm_destination);
        Bundle bundle = getIntent().getExtras();
        destination = bundle.getString("DESTINATION_LOCATION_STRING_KEY");
        mDestination.setText(destination);
    }

    private void setCost() {
        Bundle bundle = getIntent().getExtras();
        earnings = bundle.getString("EARNINGS_STRING_KEY");
        mCosts = (TextView) findViewById(R.id.earnings_text_confirm);
        mCosts.setText(earnings);
    }

    // EFFECTS: Set ConfirmNewRideRequest.
    private void setConfirmActivityRelativeLayout() {
        mConfirmActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_confirm);
        mConfirmActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save to realtime database

                saveToRealTimeDatabase();

                Intent intent = new Intent(RequestConfirmActivity.this, NavigationDrawerMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                // EFFECTS: Animation from ()Activity to ()Activity.
                Toast.makeText(RequestConfirmActivity.this, "Made Carpool Ticket", Toast.LENGTH_LONG).show();

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    // EFFECTS: Set CancelNewRideRequest.
    private void setCancelActivityRelativeLayout() {
        mCancelActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_cancel);
        mCancelActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
