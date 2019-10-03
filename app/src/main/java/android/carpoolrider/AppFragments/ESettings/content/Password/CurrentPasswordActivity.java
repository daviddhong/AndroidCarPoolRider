package android.carpoolrider.AppFragments.ESettings.content.Password;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CurrentPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String userUID, EMAIL;
    private TextView typedemail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_esettings_content_password_activity_current_password);



        initBack();
//        initContinue();

        initFields();
        resetpassword();
    }

    private void initFields() {
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void resetpassword() {
        RelativeLayout continueButton = (RelativeLayout) findViewById(R.id.continue_change_password);
        continueButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typedemail = findViewById(R.id.editText_password_current_settings);
                EMAIL = typedemail.getText().toString();


                mAuth.sendPasswordResetEmail(EMAIL)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
//                                    Intent intent = new Intent(CurrentPasswordActivity.this, LogInActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
                                    // EFFECTS: Animation from LogInActivity to BottomNavigationMainActivity.
//                                    CurrentPasswordActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    Toast.makeText(CurrentPasswordActivity.this,
                                            "sent email to reset PW", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(CurrentPasswordActivity.this,
                                            task.getException().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }));
    }

    private void initBack() {
        RelativeLayout backButton = (RelativeLayout) findViewById(R.id.rl_back_current_password);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                // EFFECTS: Animation to OpenSourceLicenseActivity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
    }

    private void initContinue() {
        RelativeLayout continueButton = (RelativeLayout) findViewById(R.id.continue_change_password);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentPasswordActivity.this, NewPasswordActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
