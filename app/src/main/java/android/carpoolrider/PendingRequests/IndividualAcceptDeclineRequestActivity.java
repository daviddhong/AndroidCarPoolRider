package android.carpoolrider.PendingRequests;

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

public class IndividualAcceptDeclineRequestActivity extends AppCompatActivity {

    private String receiverKeyID, senderUIDme, receiverUID;
    private RelativeLayout CARPOOLCONFIRM, CARPOOLDECLINE;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, RiderRequestingDriverRef, ConfirmedMatchRef, DriverRequestingRiderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_decline_request);

        // initialize fields
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RiderRequestingDriverRef = FirebaseDatabase.getInstance().getReference().child("RiderRequestingDriver");
        ConfirmedMatchRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch");
        receiverKeyID = getIntent().getExtras().get("clicked_user_id").toString();
        senderUIDme = mAuth.getCurrentUser().getUid();
        DriverRequestingRiderRef = FirebaseDatabase.getInstance().getReference().child("DriverRequestingRider");
        CARPOOLCONFIRM = findViewById(R.id.ad_confirm_carpool);
        CARPOOLDECLINE = findViewById(R.id.ad_cancel_carpool);

        initBack();
        onCARPOOLCONFIRMclicked();
        onCARPOOLDECLINEclicked();

        DriverRequestingRiderRef.child(senderUIDme).child(receiverKeyID).child("senderUID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String receiveruID = dataSnapshot
                            .getValue().toString();
                    receiverUID = receiveruID;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void onCARPOOLCONFIRMclicked() {
        CARPOOLCONFIRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makingfriends();
                //todo ask are you sure before finalizing finish
                deletingdatabase();
            }
        });
    }

    private void onCARPOOLDECLINEclicked() {
        CARPOOLDECLINE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete and end activity
                //todo ask are you sure before finalizing finish
                deletingdatabase();
            }
        });
    }

    private void makingfriends() {
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

    private void deletingdatabase() {
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

    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.back_accept_decline_button_RelativeLayout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
