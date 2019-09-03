package android.carpoolrider.StartFromLogIn;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button sendResetPasswordEmailButton;
    private EditText Email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        sendResetPasswordEmailButton = findViewById(R.id.button_resetpassword);
        Email = findViewById(R.id.textview_emailreset);

        forgotpassword();
    }


    private void forgotpassword() {

        sendResetPasswordEmailButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(Email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    // EFFECTS: Animation from LogInActivity to BottomNavigationMainActivity.
                                    ForgotPasswordActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    Toast.makeText(ForgotPasswordActivity.this,
                                            "sent email to reset PW", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this,
                                            task.getException().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }));
    }
}