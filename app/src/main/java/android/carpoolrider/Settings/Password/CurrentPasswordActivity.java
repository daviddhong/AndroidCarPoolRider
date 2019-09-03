package android.carpoolrider.Settings.Password;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CurrentPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_enter_current_password);

        initBack();
        initContinue();
    }

    private void initBack() {
        RelativeLayout backButton = (RelativeLayout) findViewById(R.id.rl_back_current_password);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                // EFFECTS: Animation to OpenSourceLicenseActivity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
    }

    private void initContinue() {
        RelativeLayout continueButton = (RelativeLayout) findViewById(R.id.continue_change_password);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentPasswordActivity.this, NewPasswordActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
