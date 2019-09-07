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

public class IndividualDriverRequestActivity extends AppCompatActivity {

    private String receiveUserID, senderUserID, current_state;
    private TextView ticketID, confirm_carpool_button_word;
    private RelativeLayout confirmButton;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, PickUpRequestRef, ConfirmedMatchRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posted_ride_ticket_expand_entity);
        // initialize fields
        confirm_carpool_button_word = findViewById(R.id.confirm_carpool_button_word);
        confirmButton = findViewById(R.id.confirm_carpool);
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PickUpRequestRef = FirebaseDatabase.getInstance().getReference().child("PickUpRequest");
        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        current_state = "new_dontknoweachother";
        receiveUserID = getIntent().getExtras().get("clicked_user_id").toString();
        senderUserID = mAuth.getCurrentUser().getUid();
        RetrieveUserInformation();
    }
    private void RetrieveUserInformation() {
        UserRef.child(receiveUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    String userFirstN = dataSnapshot.child("firstname").getValue().toString();
//                    String userLastN = dataSnapshot.child("lastname").getValue().toString();
//                    FirstName.setText(userFirstN);
//                    LastName.setText(userLastN);
                ManageCarpoolRequest();
//                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void ManageCarpoolRequest() {
        PickUpRequestRef.child(senderUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(receiveUserID)) {
                            String requestStatus = dataSnapshot.child(receiveUserID).child("requeststatus").getValue().toString();

                            if (requestStatus.equals("sent")) {
                                current_state = "requestissent";
                                confirm_carpool_button_word.setText("Cancel Carpool Request");
                                confirmButton.setBackgroundColor(Color.parseColor("#FF0000"));
                            }
                            // might not need this one because different layout will be for received possible
//                            } else if (requestStatus.equals("received")) {
//                                current_state = "requestisreceived";
//                                confirm_carpool_button_word.setText("You have request! Confirm!");
//                                sendfriendrequest.setText("Accept Friend Request");
////
//                                DeclineFriendRequestButton.setVisibility(View.VISIBLE);
//                                DeclineFriendRequestButton.setEnabled(true);
//                                DeclineFriendRequestButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        CancelCarpoolRequest();
//                                    }
//                                });
//
//                            }
                        } else {
                            ConfirmedMatchRef.child(senderUserID)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild(receiveUserID)) {
                                                current_state = "friendstatus";
                                                confirm_carpool_button_word.setText("Remove Friend from Contacts");
//                                                sendfriendrequest.setText("Remove Friend from Contacts");

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });


        // double check for senderUserID.equals(receiveUserID) should be differnt!!
        if (!senderUserID.equals(receiveUserID)) {
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
                    if (current_state.equals("requestisreceived")) {
                        ConfirmCarpoolRequest();
                    }
                    if (current_state.equals("friendstatus")) {
                        RemoveSpecificContact();
                    }
                }
            });
        } else {
//            confirmButton.setVisibility(View.INVISIBLE);
            confirm_carpool_button_word.setText("My own ride request... cannot request!");
            confirmButton.setEnabled(false);
        }


    }


    private void RemoveSpecificContact() {
        ConfirmedMatchRef.child(senderUserID).child(receiveUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ConfirmedMatchRef.child(receiveUserID).child(senderUserID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                confirmButton.setEnabled(true);
                                                current_state = "new_dontknoweachother";
                                                confirm_carpool_button_word.setText("Send a request");
//                                                sendfriendrequest.setText("Send a Friend Request");
//
//                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
//                                                DeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void ConfirmCarpoolRequest() {

        ConfirmedMatchRef.child(senderUserID).child(receiveUserID)
                .child("Friends").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ConfirmedMatchRef.child(receiveUserID).child(senderUserID)
                                    .child("Friends").setValue("Saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                // remove chat requests
                                                PickUpRequestRef.child(senderUserID).child(receiveUserID)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    PickUpRequestRef.child(receiveUserID).child(senderUserID)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        confirmButton.setEnabled(true);
                                                                                        current_state = "friendstatus";
                                                                                        confirm_carpool_button_word.setText("Remove Friend from Contacts");
//                                                                                        sendfriendrequest.setText("Remove Friend from Contacts");
//                                                                                        DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
//                                                                                        DeclineFriendRequestButton.setEnabled(false);
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void CancelCarpoolRequest() {
        PickUpRequestRef.child(senderUserID).child(receiveUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PickUpRequestRef.child(receiveUserID).child(senderUserID)
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
//
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
        PickUpRequestRef.child(senderUserID).child(receiveUserID)
                .child("requeststatus").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PickUpRequestRef.child(receiveUserID).child(senderUserID)
                                    .child("requeststatus").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
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
