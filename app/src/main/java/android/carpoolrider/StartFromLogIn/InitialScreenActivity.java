package android.carpoolrider.StartFromLogIn;

import android.carpoolrider.NavigationDrawerMainActivity;
import android.carpoolrider.R;
import android.carpoolrider.StartFromLogIn.CreateAccount.ACreateAccountNameActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InitialScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startfromlogin_activity_initial_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        initSignUp();
        initLogin();
        AutomaticLoginFunctionIfUserIsAlreadySignedIn();
    }

    private void initSignUp() {
        RelativeLayout signUpRelativeLayout = findViewById(R.id.init_screen_sign_up);
        signUpRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialScreenActivity.this, ACreateAccountNameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLogin() {
        TextView logInTextView = findViewById(R.id.init_screen_sign_in);
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialScreenActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
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

    private void SendToMainActivity() {
        Intent intent = new Intent(InitialScreenActivity.this, NavigationDrawerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        // EFFECTS: Animation from LogInActivity to BottomNavigationMainActivity.
        InitialScreenActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //why is there finnish
        finish();
    }
}
