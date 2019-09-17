package android.carpoolrider.ConfirmedRides;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class IndividualConfirmedTicketRiderDriverActivity extends AppCompatActivity {

    private String receiverKeyID, senderUID, current_state, receiverUID;
    private RelativeLayout confirmButton;
    private DatabaseReference UserRef, RiderRequestingDriverRef, ConfirmedMatchRef, DriverTicketsRef, RiderTicketsRef;
    private TextView confirm_carpool_button_word, riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice, riderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_confirmed_ride_ticket_expand_entity);
        initializeFields();
        extractReceiverUID();
        setName();
        setTicketInformation();
        RetrieveTicketStatusInformation();
    }

    private void setTicketInformation() {

        RiderTicketsRef.child(receiverKeyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
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

                } else {
                    DriverTicketsRef.child(receiverKeyID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

    private void setName() {

        //setting name in ticket
        ConfirmedMatchRef.child(senderUID).child(receiverKeyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    receiverUID = dataSnapshot.child("with").getValue().toString();

                    // setting name in ticket
                    UserRef.child(receiverUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String ticketname = dataSnapshot.child("firstname").getValue().toString();
                            riderName.setText(ticketname);
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

    private void initializeFields() {
        // initialize fields
        current_state = "new_dontknoweachother";
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");
        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        RiderRequestingDriverRef = FirebaseDatabase.getInstance().getReference().child("RiderRequestingDriver");
        confirm_carpool_button_word = findViewById(R.id.delete_text_button_matched_carpool);
        confirmButton = findViewById(R.id.t_confirmed_cancel_carpool);

        riderFrom = findViewById(R.id.t_confirmed_origin_data);
        riderTo = findViewById(R.id.t_confirmed_destination_data);
        riderDate = findViewById(R.id.t_carpool_date);
        riderTime = findViewById(R.id.t_carpool_time);
        riderNumberOfSeats = findViewById(R.id.t_carpool_passenger_numb);
        riderPrice = findViewById(R.id.t_confirmed_earnings_text_confirm);
        riderName = findViewById(R.id.t_confirmed_profile_name);
    }

    private void extractReceiverUID() {
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");
        DriverTicketsRef.child(receiverKeyID).child("uid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    receiverUID = dataSnapshot.getValue().toString();

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
                        // todo this called from the confirmed match fragment
                        ConfirmedMatchRef.child(senderUID)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //todo should be receiverUID?
                                        if (dataSnapshot.hasChild(receiverKeyID)) {
                                            current_state = "ConfirmedMatchedCarpool";
                                            // todo maybe don't have this feature??? but have for now
                                            confirm_carpool_button_word.setText("Delete Matched Carpool");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        // todo double check if it should be senderUID = receiverUID
        if (!senderUID.equals(receiverUID)) {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmButton.setEnabled(false);
                    if (current_state.equals("ConfirmedMatchedCarpool")) {
                        RemoveSpecificContact();
                    }
                }
            });
        } else {
            //todo should not call this, filter out from querying
            confirm_carpool_button_word.setText("My own ride request... cannot request!");
            confirmButton.setEnabled(false);
        }
    }

    // todo fix this so database removes the matchedRides realtime.
    private void RemoveSpecificContact() {
        ConfirmedMatchRef.child(senderUID).child(receiverKeyID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            ConfirmedMatchRef.child(receiverKeyID).child(senderUID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                confirmButton.setEnabled(true);
                                                current_state = "new_dontknoweachother";
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

}
