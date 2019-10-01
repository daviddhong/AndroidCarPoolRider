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

public class CreateAccountName extends AppCompatActivity {

    private EditText first_name, last_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_name);

        first_name = findViewById(R.id.editText_firstname_login);
        last_name = findViewById(R.id.editText_last_name_create_login);
        initContinue();
        initClose();
    }

    private void initContinue() {
        RelativeLayout continueActivity = (RelativeLayout) findViewById(R.id.continue_sign_up_name);
        continueActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = first_name.getText().toString();
                String lname = last_name.getText().toString();

                if (!(fname.isEmpty() || lname.isEmpty())) {
                    Intent intent = new Intent(CreateAccountName.this, CreateAccountEmail.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("first_name", fname);
                    dataBundle.putString("last_name", lname);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateAccountName.this, "Please Enter Your First and Last Name", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void initClose() {
        RelativeLayout close = (RelativeLayout) findViewById(R.id.rl_close_create_account_name);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
