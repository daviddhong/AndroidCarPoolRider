package android.carpoolrider.StartFromLogIn.CreateAccount;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountPassword extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_password);

        initContinue();
        initBack();
    }

    private void initContinue() {
        RelativeLayout continueActivity = (RelativeLayout) findViewById(R.id.continue_sign_up_password);
        continueActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountPassword.this, CreateAccountPhoneNumber.class);
                startActivity(intent);
            }
        });
    }

    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rl_back_settings_password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
