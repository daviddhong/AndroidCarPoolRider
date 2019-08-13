package android.carpoolrider.StartFromLogIn;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MakeAccountActivity extends AppCompatActivity {

    private Button confirmAccountInformationButton;
    private EditText makeEmail, makePassword, firstName, lastName, confirmEmail, confirmPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference RootRef;
    private static final String TAG = MakeAccountActivity.class.getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);
        initializeFields();
        confirmAccountInformation();
    }

    // initialize the fields at the beginning
    private void initializeFields() {
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        makeEmail = (EditText) findViewById(R.id.editText_email_make);
        makePassword = (EditText) findViewById(R.id.editText_pw_make);
        firstName = (EditText) findViewById(R.id.editText_firstname_make);
        lastName = (EditText) findViewById(R.id.editText_lastname_make);
        currentUser = mAuth.getCurrentUser();
        confirmEmail = (EditText) findViewById(R.id.editText_confirmemail_make);
        confirmPassword = (EditText) findViewById(R.id.editText_confirmpw_make);
    }

    //make account with name email and password. Then goes to verify phone number page
    private void confirmAccountInformation() {
        confirmAccountInformationButton = findViewById(R.id.button_confirm_make);
        confirmAccountInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = makeEmail.getText().toString();
                String password = makePassword.getText().toString();
                String firstN = firstName.getText().toString();
                String lastN = lastName.getText().toString();
                String confirmemail = confirmEmail.getText().toString();
                String confirmpassword = confirmPassword.getText().toString();

                if (firstN.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your First Name", Toast.LENGTH_LONG).show();
                } else if (lastN.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your Last Name", Toast.LENGTH_LONG).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                } else if (confirmemail.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Confirm Your Email", Toast.LENGTH_LONG).show();
                } else if (confirmpassword.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Confirm Your Password", Toast.LENGTH_LONG).show();
                } else if ((!(confirmemail.isEmpty())) && (!(confirmemail.equals(email)))) {
                    Toast.makeText(MakeAccountActivity.this, "Emails do not match", Toast.LENGTH_LONG).show();
                } else if ((!(confirmpassword.isEmpty())) && (!(confirmpassword.equals(password)))) {
                    Toast.makeText(MakeAccountActivity.this, "Passwords  do not match", Toast.LENGTH_LONG).show();
                } else {


                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                CollectFirstLastNameEmailIntoRealTimeDatabase(firstN, lastN, email);
                                SendVerificationEmail();
//
                            } else {
                                // if account is not made
                                Toast.makeText(MakeAccountActivity.this,
                                        task.getException().toString(), Toast.LENGTH_LONG).show();
                                Log.i(TAG,"ACCOUNT NOT MADE");
                            }
                        }
                    });

                }
            }
        });
    }

    // add first last name to realtime database
    private void CollectFirstLastNameEmailIntoRealTimeDatabase(String firstN, String lastN, String email) {
        String currentUserID = mAuth.getCurrentUser().getUid();
        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("uid", currentUserID);
        profileMap.put("firstname", firstN);
        profileMap.put("lastname", lastN);
        profileMap.put("email", email);
        RootRef.child("Users").child(currentUserID).setValue(profileMap);
        Log.i(TAG,"SHOULD STORE TO REALTIME DATABASE");
    }


    // send to phone number activity
    private void SendVerificationEmail() {

        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG,"SHOULD HAVE SENT EMAIL VERI");
                            Intent intent = new Intent(MakeAccountActivity.this, LogInActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            // EFFECTS: Animation from MakeAccountActivity to PhoneNumberActivity.
                            MakeAccountActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                            Toast.makeText(MakeAccountActivity.this,
                                    "Verification email sent!!", Toast.LENGTH_LONG).show();

                        } else {
                            Log.i(TAG,"EMAIL VERI NOT SENT");

                            Toast.makeText(MakeAccountActivity.this,
                                    "NOT sent!!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}


