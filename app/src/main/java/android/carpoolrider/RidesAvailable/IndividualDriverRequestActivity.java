package android.carpoolrider.RidesAvailable;

import android.carpoolrider.R;
import android.graphics.Color;
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

import java.util.HashMap;

public class IndividualDriverRequestActivity extends AppCompatActivity {

    private String receiverKeyID, senderUID, current_state, receiverUID;
    private TextView confirm_carpool_button_word, message_driver_button_word;
    private RelativeLayout confirmButton;
    private DatabaseReference UserRef, RiderRequestingDriverRef, ConfirmedMatchRef, DriverTicketsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posted_ride_ticket_expand_entity);

        // initialize fields

        current_state = "new_dontknoweachother";
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RiderRequestingDriverRef = FirebaseDatabase.getInstance().getReference().child("RiderRequestingDriver");
        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        confirmButton = findViewById(R.id.confirm_carpool);
        message_driver_button_word = findViewById(R.id.tcancel_request_text_view_carpool);
        confirm_carpool_button_word = findViewById(R.id.confirm_carpool_button_word);

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
        RetrieveTicketStatusInformation();
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
//                            if (requestStatus.equals("sent")) {
                            current_state = "requestissent";
                            confirm_carpool_button_word.setText("Cancel Carpool Request");
                            confirmButton.setBackgroundColor(Color.parseColor("#FF0000"));
                        }
//                        }
//                        } else {
//
//                            // todo this called from the confirmed match fragment
//                            ConfirmedMatchRef.child(senderUID)
//                                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                            //todo should be receiverUID?
//
//                                            if (dataSnapshot.hasChild(receiverKeyID)) {
//                                                current_state = "ConfirmedMatchedCarpool";
//                                                // todo maybe don't have this feature??? but have for now
//                                                confirm_carpool_button_word.setText("Delete Matched Carpool");
////                                                message_driver_button_word.setText("To Delete, Contact Driver");
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                                        }
//                                    });
//                        }
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
                    //todo should not call the last two for the Rides Available fragment
//                    if (current_state.equals("requestisreceived")) {
//                        ConfirmCarpoolRequest();
//                    }
//                    //todo only called in confirmed ride fragment
//                    if (current_state.equals("ConfirmedMatchedCarpool")) {
//                        RemoveSpecificContact();
//                    }
                }
            });
        } else {
//            confirmButton.setVisibility(View.INVISIBLE);
            //todo should not call this, filter out from querying
            confirm_carpool_button_word.setText("My own ride request... cannot request!");
            confirmButton.setEnabled(false);
        }
    }

//    // todo fix this so database removes the matchedRides realtime.
//    private void RemoveSpecificContact() {
//        ConfirmedMatchRef.child(senderUID).child(receiverKeyID)
//                .removeValue()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//
//                            ConfirmedMatchRef.child(receiverKeyID).child(senderUID)
//                                    .removeValue()
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                confirmButton.setEnabled(true);
//                                                current_state = "new_dontknoweachother";
//                                                confirm_carpool_button_word.setText("Send a request");
//                                                message_driver_button_word.setText("Message the Driver");
//                                                finish();
////                                                sendfriendrequest.setText("Send a Friend Request");
////                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
////                                                DeclineFriendRequestButton.setEnabled(false);
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

//    private void ConfirmCarpoolRequest() {
//
//        ConfirmedMatchRef.child(senderUID).child(receiverKeyID)
//                .child("Friends").setValue("Saved")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            ConfirmedMatchRef.child(receiverKeyID).child(senderUID)
//                                    .child("Friends").setValue("Saved")
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//
//                                                // remove chat requests
//                                                RiderRequestingDriverRef.child(senderUID).child(receiverKeyID)
//                                                        .removeValue()
//                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if (task.isSuccessful()) {
//                                                                    RiderRequestingDriverRef.child(receiverKeyID).child(senderUID)
//                                                                            .removeValue()
//                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                @Override
//                                                                                public void onComplete(@NonNull Task<Void> task) {
//                                                                                    if (task.isSuccessful()) {
//                                                                                        confirmButton.setEnabled(true);
//                                                                                        current_state = "ConfirmedMatchedCarpool";
//                                                                                        confirm_carpool_button_word.setText("Remove Friend from Contacts");
////                                                                                        sendfriendrequest.setText("Remove Friend from Contacts");
////                                                                                        DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
////                                                                                        DeclineFriendRequestButton.setEnabled(false);
//                                                                                    }
//                                                                                }
//                                                                            });
//                                                                }
//                                                            }
//                                                        });
//
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

    private void CancelCarpoolRequest() {
        RiderRequestingDriverRef.child(senderUID).child(receiverKeyID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            RiderRequestingDriverRef.child(receiverKeyID).child(senderUID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                confirmButton.setEnabled(true);
                                                current_state = "new_dontknoweachother";
                                                confirm_carpool_button_word.setText("Request to Pickup Rider");
                                                confirmButton.setBackgroundColor(Color.parseColor("#2A2E43"));

//                                                sendfriendrequest.setText("Send a Friend Request");
//                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
//                                                DeclineFriendRequestButton.setEnabled(false);
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
                                                confirm_carpool_button_word.setText("Cancel Carpool Request");
                                                confirmButton.setBackgroundColor(Color.parseColor("#FF0000"));
//                                                sendfriendrequest.setText("Cancel Friend Request");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
