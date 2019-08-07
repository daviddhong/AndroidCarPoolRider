package android.carpoolrider.StartFromLogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.carpoolrider.MainActivity;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private TextView MakeAccountPage;
    private EditText loginEmail, loginPassword;
    private FirebaseAuth mAuth;
//    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFields();
        logInWithEmailAndPassword();
        goToMakeAccountPage();
    }

    private void initializeFields() {
        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
        MakeAccountPage = findViewById(R.id.textView_makeaccount);
        loginButton = findViewById(R.id.button_login);
        loginEmail = findViewById(R.id.editText_email_login);
        loginPassword = findViewById(R.id.editText_password_login);
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
                                SendToMainActivity();
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
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        // EFFECTS: Animation from LogInActivity to MainActivity.
        LogInActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    // Make account to use to log in
    private void goToMakeAccountPage() {
        MakeAccountPage.setOnClickListener(new View.OnClickListener() {
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
        // Check if user is signed in (non-null) and update UI accordingly.
        AutomaticLoginFunctionIfUserIsAlreadySignedIn();
    }

    private void AutomaticLoginFunctionIfUserIsAlreadySignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
//            SendToMainActivity();
        }
    }
}
