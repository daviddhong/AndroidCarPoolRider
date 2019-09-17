package android.carpoolrider.PendingRequests;

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

public class IndividualAcceptDeclineRequestActivity extends AppCompatActivity {

    private String receiverKeyID, senderUIDme, receiverUID;
    private DatabaseReference ConfirmedMatchRef, DriverRequestingRiderRef;
    private TextView riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice, riderName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_decline_request);
        initFields();
        initBackButton();
        gettingReceiverUidFromDataSnapShot();
        onCarPoolConfirmedButtonClicked();
        onCarPoolDeclineButtonclicked();
    }

    private void gettingReceiverUidFromDataSnapShot() {
        DriverRequestingRiderRef.child(senderUIDme)
                .child(receiverKeyID).child("senderUID")
                .addValueEventListener(new ValueEventListener() {
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

    private void initFields() {
        // initialize firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        senderUIDme = mAuth.getCurrentUser().getUid();
        DriverRequestingRiderRef = FirebaseDatabase.getInstance().getReference().child("DriverRequestingRider");

        riderFrom = findViewById(R.id.ad_origin_data);
        riderTo = findViewById(R.id.ad_destination_data);
        riderDate = findViewById(R.id.ad_date_of_carpool);
        riderTime = findViewById(R.id.ad_time_of_carpool);
        riderNumberOfSeats = findViewById(R.id.passengernum);
        riderPrice = findViewById(R.id.ad_earnings_text_confirm);
        riderName = findViewById(R.id.profile_name);
    }

    private void onCarPoolConfirmedButtonClicked() {
        RelativeLayout CarpoolConfirmRelativeLayoutButton = findViewById(R.id.ad_confirm_carpool);
        CarpoolConfirmRelativeLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCarpoolConfirmMatchNodeInFireBase();
                //todo ask are you sure before finalizing finish
                deletingDatabase();
            }
        });
    }

    private void onCarPoolDeclineButtonclicked() {
        RelativeLayout CarpoolDeclineRelativeLayoutButton = findViewById(R.id.ad_cancel_carpool);
        CarpoolDeclineRelativeLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete and end activity
                //todo ask are you sure before finalizing finish
                deletingDatabase();
            }
        });
    }

    private void createCarpoolConfirmMatchNodeInFireBase() {
        ConfirmedMatchRef.child(senderUIDme).child(receiverKeyID)
                .child("with").setValue(receiverUID)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ConfirmedMatchRef.child(receiverUID).child(receiverKeyID)
                                    .child("with").setValue(senderUIDme);
                        }
                    }
                });
    }

    private void deletingDatabase() {
        DriverRequestingRiderRef.child(receiverUID).child(receiverKeyID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DriverRequestingRiderRef.child(senderUIDme).child(receiverKeyID)
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

    private void initBackButton() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.back_accept_decline_button_RelativeLayout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
