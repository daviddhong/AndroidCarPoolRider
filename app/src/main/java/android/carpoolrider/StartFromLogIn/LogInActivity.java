package android.carpoolrider.StartFromLogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.carpoolrider.BottomNavigationMainActivity;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    private Button loginButton;
    private RelativeLayout makeAccountPage;
    private TextView ForgotPassword, ResendVerificationEmail;
    private EditText loginEmail, loginPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFields();
        logInWithEmailAndPassword();
        goToMakeAccountPage();
        forgotPassword();
        resendVerificationEmail();
    }


    private void initializeFields() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        makeAccountPage = findViewById(R.id.rl_create_account);
        loginButton = findViewById(R.id.button_login);
        loginEmail = findViewById(R.id.editText_email_login);
        loginPassword = findViewById(R.id.editText_password_login);
        ResendVerificationEmail = findViewById(R.id.textView_resend_verification_email);
        ForgotPassword = findViewById(R.id.textView_forgotpassword);
    }

    // Log In when login button is pressed
    private void logInWithEmailAndPassword() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get strings from email and password editText fields
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Please enter Your Email", Toast.LENGTH_LONG).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Please enter Your Password", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    SendToMainActivity();
                                } else {
                                    Toast.makeText(LogInActivity.this, "please verify email", Toast.LENGTH_LONG).show();
                                    ResendVerificationEmail.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(LogInActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


    // go to main activity
    private void SendToMainActivity() {
        Intent intent = new Intent(LogInActivity.this, BottomNavigationMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        // EFFECTS: Animation from LogInActivity to BottomNavigationMainActivity.
        LogInActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    // Make account to use to log in
    private void goToMakeAccountPage() {
        makeAccountPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToMakeAccountActivity();
            }
        });
    }

    private void SendToMakeAccountActivity() {
        Intent intent = new Intent(LogInActivity.this, MakeAccountActivity.class);
        startActivity(intent);
        // EFFECTS: Animation from LogInActivity to MakeAccountActivity.
        LogInActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    //checks if user is already logged in when app is started
    @Override
    public void onStart() {
        super.onStart();
        ResendVerificationEmail.setVisibility(View.INVISIBLE);
        // Check if user is signed in (non-null) and update UI accordingly.
        AutomaticLoginFunctionIfUserIsAlreadySignedIn();
    }

    // Must check if email is also verified && is logged in already.
    private void AutomaticLoginFunctionIfUserIsAlreadySignedIn() {
        if (currentUser != null) {
            boolean emailVerified = currentUser.isEmailVerified();
            if (emailVerified) {
                SendToMainActivity();
            }
        }
    }

    private void forgotPassword() {
        ForgotPassword.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                // EFFECTS: Animation from LogInActivity to BottomNavigationMainActivity.
                LogInActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }));
    }


    private void resendVerificationEmail() {
        ResendVerificationEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this,
                        "TODO resend verification email", Toast.LENGTH_LONG).show();
            }
        });
    }
}


