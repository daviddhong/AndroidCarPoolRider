package android.carpoolrider.AppFragments.ARidesAvailable.content;

import android.carpoolrider.R;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class IndividualDriverRequestActivity extends AppCompatActivity {

    private String receiverKeyID, senderUID, current_state, receiverUID;
    private RelativeLayout confirmButton, backButton;
    private DatabaseReference UserRef, RiderRequestingDriverRef, DriverTicketsRef, RiderTicketsRef;
    private TextView confirm_carpool_button_word, riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice, riderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_aridesavailable_content_layout_posted_ride_ticket_expand_entity);
        initializeFields();
        backButtonFunction();
        extractReceiverUID();
        fillTicketInformationFromDatabase();
        RetrieveTicketStatusInformation();
    }

    private void backButtonFunction() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeFields() {
        // initialize fields
        current_state = "new_dontknoweachother";
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");

        RiderRequestingDriverRef = FirebaseDatabase.getInstance().getReference().child("RiderRequestingDriver");
        confirm_carpool_button_word = findViewById(R.id.confirm_carpool_button_word);
        confirmButton = findViewById(R.id.confirm_carpool);
        backButton = findViewById(R.id.back_accept_decline_button_RelativeLayout);

        riderFrom = findViewById(R.id.origin_data);
        riderTo = findViewById(R.id.destination_data);
        riderDate = findViewById(R.id.dateofcarpool);
        riderTime = findViewById(R.id.time_of_carpool);
        riderNumberOfSeats = findViewById(R.id.passengernum);
        riderPrice = findViewById(R.id.earnings_text_confirm);
        riderName = findViewById(R.id.profile_name);
    }

    private void extractReceiverUID() {

        DriverTicketsRef.child(receiverKeyID).child("uid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    receiverUID = dataSnapshot.getValue().toString();
                    // setting name in ticket
                    UserRef.child(receiverUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                final String ticketname = dataSnapshot.child("firstname").getValue().toString();
                                riderName.setText(ticketname);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void fillTicketInformationFromDatabase() {
        DriverTicketsRef.child(receiverKeyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final String ticketTo = dataSnapshot.child("To").getValue().toString();
                    final String ticketFrom = dataSnapshot.child("From").getValue().toString();
                    final String ticketDate = dataSnapshot.child("Date").getValue().toString();
                    final String ticketTime = dataSnapshot.child("Time").getValue().toString();
                    final String ticketPrice = dataSnapshot.child("Price").getValue().toString();
                    final String ticketNumberOfSeats = dataSnapshot.child("NumberOfSeats").getValue().toString();
                    riderTo.setText(ticketTo);
                    riderFrom.setText(ticketFrom);
                    riderDate.setText(ticketDate);
                    riderTime.setText(ticketTime);
                    riderPrice.setText(ticketPrice);
                    riderNumberOfSeats.setText(ticketNumberOfSeats);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void RetrieveTicketStatusInformation() {

        //todo should be recievrUID?

        UserRef.child(receiverKeyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ManageCarpoolRequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void ManageCarpoolRequest() {

        //todo this is called from rides available fragment
        RiderRequestingDriverRef.child(senderUID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(receiverKeyID)) {
                            String requestStatus = dataSnapshot
                                    .child(receiverKeyID)
                                    .child("requeststatus").getValue().toString();
                            if (requestStatus.equals("sent")) {
                                current_state = "requestissent";

                                Map<String, Object> profileMap = new HashMap<>();
                                String status = "1";
                                profileMap.put("status", status);
                                profileMap.put("status_uid", status+receiverUID);
                                DriverTicketsRef.child(receiverKeyID).updateChildren(profileMap);

                                confirm_carpool_button_word.setText("Cancel Carpool Request");
                                confirmButton.setBackgroundColor(Color.parseColor("#FF0000"));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        // double check for senderUID.equals(receiverKeyID) should be differnt!!
        if (!senderUID.equals(receiverUID)) {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmButton.setEnabled(false);
                    if (current_state.equals("new_dontknoweachother")) {
                        SendRequestToPickUpRider();
                    }
                    if (current_state.equals("requestissent")) {
                        CancelCarpoolRequest();
                    }
                }
            });
        } else {
            //todo should not call this eventually, filter out from querying
            confirm_carpool_button_word.setText("My own ride request... cannot request!");
            confirmButton.setEnabled(false);
        }
    }

    private void CancelCarpoolRequest() {
        RiderRequestingDriverRef.child(senderUID).child(receiverKeyID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            RiderRequestingDriverRef.child(receiverUID).child(receiverKeyID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                confirmButton.setEnabled(true);
                                                current_state = "new_dontknoweachother";

                                                Map<String, Object> profileMap = new HashMap<>();
                                                String status = "0";
                                                profileMap.put("status", status);
                                                profileMap.put("status_uid", status+receiverUID);
                                                DriverTicketsRef.child(receiverKeyID).updateChildren(profileMap);

                                                confirm_carpool_button_word.setText("Request to Pickup Rider");
                                                confirmButton.setBackgroundColor(Color.parseColor("#2A2E43"));
                                                finish();
                                                Toast.makeText(IndividualDriverRequestActivity.this, "Canceled Request", Toast.LENGTH_LONG).show();


                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void SendRequestToPickUpRider() {
        RiderRequestingDriverRef.child(senderUID).child(receiverKeyID)
                .child("requeststatus").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            //putting the receivers uid
                            HashMap<String, Object> ticketuserMap = new HashMap<>();
                            ticketuserMap.put("receiverUID", receiverUID);
                            RiderRequestingDriverRef.child(senderUID)
                                    .child(receiverKeyID).updateChildren(ticketuserMap);

                            RiderRequestingDriverRef.child(receiverUID).child(receiverKeyID)
                                    .child("requeststatus").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                //putting the sender uid
                                                HashMap<String, Object> ticketuserMap = new HashMap<>();
                                                ticketuserMap.put("senderUID", senderUID);
                                                RiderRequestingDriverRef.child(receiverUID)
                                                        .child(receiverKeyID).updateChildren(ticketuserMap);

                                                confirmButton.setEnabled(true);
                                                current_state = "requestissent";

                                                Map<String, Object> profileMap = new HashMap<>();
                                                String status = "1";
                                                profileMap.put("status", status);
                                                profileMap.put("status_uid", status+receiverUID);
                                                DriverTicketsRef.child(receiverKeyID).updateChildren(profileMap);

                                                confirm_carpool_button_word.setText("Cancel Carpool Request");
                                                confirmButton.setBackgroundColor(Color.parseColor("#FF0000"));
                                                finish();
                                                Toast.makeText(IndividualDriverRequestActivity.this, "Sent Request", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
