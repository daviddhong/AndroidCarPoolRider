package android.carpoolrider.Settings;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private RelativeLayout profileCloseImageView;

    private TextView Name, PhoneNumber ;
    private DatabaseReference UsersRef;
    private String userUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initFields();
        setFields();
        setFinishProfileActivity();
    }

    private void initFields() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        Name = findViewById(R.id.name_profile);
        PhoneNumber = findViewById(R.id.phone_number_profile);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void setFields() {
        UsersRef.child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   Name.setText(dataSnapshot.child("firstname").getValue().toString());
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void setFinishProfileActivity() {
        profileCloseImageView = (RelativeLayout) findViewById(R.id.close_button_profile);
        profileCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_down, R.anim.slide_vertical_null);
            }
        });
    }
}
