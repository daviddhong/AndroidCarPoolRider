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
    private TextView confirm_carpool_button_word;
    private RelativeLayout confirmButton;
    private DatabaseReference UserRef, RiderRequestingDriverRef, ConfirmedMatchRef, DriverTicketsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_confirmed_ride_ticket_expand_entity);
        initializeFields();
        extractReceiverUID();
        RetrieveTicketStatusInformation();
    }

    private void initializeFields() {
        // initialize fields
        current_state = "new_dontknoweachother";
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RiderRequestingDriverRef = FirebaseDatabase.getInstance().getReference().child("RiderRequestingDriver");
        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        confirmButton = findViewById(R.id.t_confirmed_cancel_carpool);
        confirm_carpool_button_word = findViewById(R.id.delete_text_button_matched_carpool);
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
