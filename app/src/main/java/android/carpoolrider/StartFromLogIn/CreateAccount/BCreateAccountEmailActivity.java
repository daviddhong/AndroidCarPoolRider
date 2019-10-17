package android.carpoolrider.StartFromLogIn.CreateAccount;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class BCreateAccountEmailActivity extends AppCompatActivity {

    private TextView personname;
    private EditText uemail;
    private String fname, lname, fullname;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startfromlogin_createaccount_activity_create_account_email);

        firebaseAuth = FirebaseAuth.getInstance();
        uemail = findViewById(R.id.editText_email_login);

        //getting the data bundle from other activity incoming
        Bundle gotname = getIntent().getExtras();
        fname = gotname.getString("first_name");
        lname = gotname.getString("last_name");
        fullname = fname + " " + lname;

        personname = findViewById(R.id.user_person_name);
        personname.setText(fullname);

        initContinue();
        backButton();
    }

    private void initContinue() {
        RelativeLayout continueActivity = (RelativeLayout) findViewById(R.id.continue_sign_up_email);
        continueActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uemail.getText().toString();
                if (!(email.isEmpty())) {
                    firebaseAuth.fetchSignInMethodsForEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        SignInMethodQueryResult result = task.getResult();
                                        List<String> signInMethods = result.getSignInMethods();

                                        if (signInMethods.isEmpty()) {
                                            Intent intent = new Intent(BCreateAccountEmailActivity.this, CCreateAccountPasswordActivity.class);
                                            Bundle dataBundle = new Bundle();
                                            dataBundle.putString("first_name", fname);
                                            dataBundle.putString("last_name", lname);
                                            dataBundle.putString("user_email", email);
                                            intent.putExtras(dataBundle);
                                            startActivity(intent);

                                        } else if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                            // User can sign in with email/password
                                            Toast.makeText(BCreateAccountEmailActivity.this, "Email Already In Use", Toast.LENGTH_SHORT).show();

                                        } else if (signInMethods.contains(EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD)) {
                                            // User can sign in with email/link
                                            Toast.makeText(BCreateAccountEmailActivity.this, "Email link in use", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(BCreateAccountEmailActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(BCreateAccountEmailActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void backButton() {
        RelativeLayout backRelativeLayout = (RelativeLayout) findViewById(R.id.rl_back_create_account_email);
        backRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
