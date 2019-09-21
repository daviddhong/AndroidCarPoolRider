package android.carpoolrider.Settings;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
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

public class EmailActivity extends AppCompatActivity {

    private TextView Email ;
    private DatabaseReference UsersRef;
    private String userUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_email);
        initBack();

        initFields();
        setFields();
    }

    private void setFields() {
        UsersRef.child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Email.setText(dataSnapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initFields() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        Email = findViewById(R.id.editText_email_change_settings);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }


    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rl_back_settings_email);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
