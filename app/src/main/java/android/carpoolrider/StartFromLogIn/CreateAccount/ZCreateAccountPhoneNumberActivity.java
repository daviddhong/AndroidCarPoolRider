package android.carpoolrider.StartFromLogIn.CreateAccount;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ZCreateAccountPhoneNumberActivity extends AppCompatActivity {

    private String fname, lname, uemail;
    private EditText phonenumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startfromlogin_createaccount_activity_create_account_phone_number);


        Bundle gotname = getIntent().getExtras();
        fname = gotname.getString("first_name");
        lname = gotname.getString("last_name");
        uemail = gotname.getString("user_email");


        phonenumber = findViewById(R.id.editText_phonenumber_sign_upp);

        initcontinue();

        initBack();
    }

    private void initcontinue() {
        RelativeLayout continueActivity = (RelativeLayout) findViewById(R.id.continue_sign_up_phone_number);
        continueActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pnumb = phonenumber.getText().toString();

                if (pnumb.isEmpty()) {
                    Toast.makeText(ZCreateAccountPhoneNumberActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else if ((pnumb.length() < 10) || (pnumb.length() > 11)) {
                    Toast.makeText(ZCreateAccountPhoneNumberActivity.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(ZCreateAccountPhoneNumberActivity.this, CCreateAccountPasswordActivity.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("first_name", fname);
                    dataBundle.putString("last_name", lname);
                    dataBundle.putString("user_email", uemail);
                    dataBundle.putString("phone_number", pnumb);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
            }
        });
    }


    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rl_back_create_account_phone_numbere);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
