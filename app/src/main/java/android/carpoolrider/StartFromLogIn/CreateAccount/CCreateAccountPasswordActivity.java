package android.carpoolrider.StartFromLogIn.CreateAccount;

import android.carpoolrider.R;
import android.carpoolrider.StartFromLogIn.LogInActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

public class CCreateAccountPasswordActivity extends AppCompatActivity {


    private RelativeLayout create_account_button;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference RootRef;
    private String fname, lname, uemail;
    private EditText userpw, confirmpw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startfromlogin_createaccount_activity_create_account_password);
        userpw = findViewById(R.id.editText_password_sign_up);
        confirmpw = findViewById(R.id.editText_password_sign_upconfirm);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();


        Bundle gotname = getIntent().getExtras();
        fname = gotname.getString("first_name");
        lname = gotname.getString("last_name");
        uemail = gotname.getString("user_email");

        initContinue();
        initBack();
    }

    private void initContinue() {
        RelativeLayout continueActivity = (RelativeLayout) findViewById(R.id.continue_sign_up_password);
        continueActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upw = userpw.getText().toString();
                String cpw = confirmpw.getText().toString();

                //   todo:
                //    if (not pwd =~ /[0-9]/) return false;
                //    if (not pwd =~ /[a-z]/) return false;
                //    if (not pwd =~ /[A-Z]/) return false;
                //    if (not pwd =~ /[%@$^]/) return false;
                //    if (pwd =~ /\s/) return false;
                //    if (pw.contains(" ")) return false;
                //    https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation

                if (upw.isEmpty()) {
                    Toast.makeText(CCreateAccountPasswordActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                } else if (cpw.isEmpty()) {
                    Toast.makeText(CCreateAccountPasswordActivity.this, "Please Confirm Your Password", Toast.LENGTH_LONG).show();
                } else if (!(upw.equals(cpw))) {
                    Toast.makeText(CCreateAccountPasswordActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                } else if (upw.length() < 7) {
                    Toast.makeText(CCreateAccountPasswordActivity.this,
                            "Password must be at least 7 characters long", Toast.LENGTH_LONG).show();
                } else {

                    createAccount_createUser_SendVerificationEmail(upw);

//                    Intent intent = new Intent(CCreateAccountPasswordActivity.this, LogInActivity.class);
//                    Bundle dataBundle = new Bundle();
//
//                    dataBundle.putString("first_name", fname);
//                    dataBundle.putString("last_name", lname);
//                    dataBundle.putString("user_email", uemail);
//                    dataBundle.putString("user_pw", upw);
//
//                    intent.putExtras(dataBundle);
//                    startActivity(intent);
                }
            }
        });
    }

    private void createAccount_createUser_SendVerificationEmail(String pw) {
        mAuth.createUserWithEmailAndPassword(uemail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    CollectFirstLastNameEmailIntoRealTimeDatabase();
                    SendVerificationEmail();

                } else {
                    // if account is not made
                    Toast.makeText(CCreateAccountPasswordActivity.this,
                            task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void SendVerificationEmail() {

//        try {
//            final FirebaseUser currentUser = mAuth.getCurrentUser();
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                fname, lname, uemail, ucar
                                Intent intent = new Intent(CCreateAccountPasswordActivity.this, LogInActivity.class);
                                startActivity(intent);

                                Toast.makeText(CCreateAccountPasswordActivity.this,
                                        "Verification email sent to \n" + uemail, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CCreateAccountPasswordActivity.this,
                                        "NOT sent!!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

//        } catch (NullPointerException e) {
//            Toast.makeText(CCreateAccountPasswordActivity.this,
//                    "NULL POINTER NOT SENT@@" + e.toString(), Toast.LENGTH_LONG).show();
//        }
    }
//    String firstN, String lastN, String userEmail, String userCar
    private void CollectFirstLastNameEmailIntoRealTimeDatabase() {
        String currentUserID = currentUser.getUid();
        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("uid", currentUserID);
        profileMap.put("firstname", fname);
        profileMap.put("lastname", lname);
        profileMap.put("email", uemail);
//        profileMap.put("carModelMake", ucar);
        RootRef.child("Users").child(currentUserID).setValue(profileMap);
    }

    private void initBack() {
        RelativeLayout back = findViewById(R.id.rl_back_settings_password_account);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
