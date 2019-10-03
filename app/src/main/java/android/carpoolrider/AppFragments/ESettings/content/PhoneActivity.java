package android.carpoolrider.AppFragments.ESettings.content;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhoneActivity extends AppCompatActivity {

    private TextView PhoneNumber ;
    private DatabaseReference UsersRef;
    private String userUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_esettings_activity_phone);
        initBack();

        initFields();
//        setFields();
    }

//    private void setFields() {
//        UsersRef.child(userUID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    PhoneNumber.setText(dataSnapshot.child("phonenumber").getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void initFields() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        PhoneNumber = findViewById(R.id.editText_phone_number_change_settings);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }


    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rl_back_settings_phone_number);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
