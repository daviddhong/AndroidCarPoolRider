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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MakeAccountActivity extends AppCompatActivity {

    private Button confirmAccountInformationButton;
    private EditText makeEmail, makePassword, firstName, lastName;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
//    private DatabaseReference RootRef;


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
//        RootRef = FirebaseDatabase.getInstance().getReference();
        makeEmail = (EditText) findViewById(R.id.editText_email_make);
        makePassword = (EditText) findViewById(R.id.editText_pw_make);
        firstName = (EditText) findViewById(R.id.editText_firstname_make);
        lastName = (EditText) findViewById(R.id.editText_lastname_make);
        currentUser = mAuth.getCurrentUser();
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

                if (firstN.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your First Name", Toast.LENGTH_LONG).show();
                } else if (lastN.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your Last Name", Toast.LENGTH_LONG).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(MakeAccountActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                } else {


                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                CollectFirstLastNameIntoRealTimeDatabase(firstN, lastN);
                                SendVerificationEmail();
//
                            } else {
                                // if account is not made
                                Toast.makeText(MakeAccountActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }

    // add first last name to realtime database
    private void CollectFirstLastNameIntoRealTimeDatabase(String firstN, String lastN) {
//        String currentUserID = mAuth.getCurrentUser().getUid();
//        HashMap<String, String> profileMap = new HashMap<>();
//        profileMap.put("uid", currentUserID);
//        profileMap.put("firstname", firstN);
//        profileMap.put("lastname", lastN);
//        RootRef.child("Users").child(currentUserID).setValue(profileMap);
    }


    // send to phone number activity
    private void SendVerificationEmail() {

        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MakeAccountActivity.this, "Verification email sent!!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MakeAccountActivity.this, LogInActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            // EFFECTS: Animation from MakeAccountActivity to PhoneNumberActivity.
                            MakeAccountActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        } else {
                            Toast.makeText(MakeAccountActivity.this,
                                    "NOT sent!!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

}


