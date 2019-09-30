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

    private String receiverKeyID, senderUID, receiverUID;
    private RelativeLayout deleteConfirmedRideButton;
    private DatabaseReference UserRef, RiderRequestingDriverRef, ConfirmedMatchRef, DriverTicketsRef, RiderTicketsRef;
    private TextView confirm_carpool_button_word, riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice, riderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_confirmed_ride_ticket_expand_entity);
        initializeFields();
        setName();
        setTicketInformation();
        DeleteConfirmedRide();
    }

    private void initializeFields() {
        // initialize fields
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");

        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        RiderRequestingDriverRef = FirebaseDatabase.getInstance().getReference().child("RiderRequestingDriver");
        confirm_carpool_button_word = findViewById(R.id.delete_text_button_matched_carpool);
        deleteConfirmedRideButton = findViewById(R.id.t_confirmed_cancel_carpool);
        riderFrom = findViewById(R.id.t_confirmed_origin_data);
        riderTo = findViewById(R.id.t_confirmed_destination_data);
        riderDate = findViewById(R.id.t_carpool_date);
        riderTime = findViewById(R.id.t_carpool_time);
        riderNumberOfSeats = findViewById(R.id.t_carpool_passenger_numb);
        riderPrice = findViewById(R.id.t_confirmed_earnings_text_confirm);
        riderName = findViewById(R.id.t_confirmed_profile_name);
    }

    private void setName() {
        //setting name in ticket
        ConfirmedMatchRef.child(senderUID).child(receiverKeyID).child("with").addValueEventListener(new ValueEventListener() {
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

    private void setTicketInformation() {
        RiderTicketsRef.child(receiverKeyID).addValueEventListener(new ValueEventListener() {
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
                } else {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void DeleteConfirmedRide() {
        deleteConfirmedRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveSpecificContact();
            }
        });
    }

    // todo fix this so database removes the matchedRides realtime.
    private void RemoveSpecificContact() {
        ConfirmedMatchRef.child(senderUID).child(receiverKeyID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            ConfirmedMatchRef.child(receiverUID).child(receiverKeyID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
