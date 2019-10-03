package android.carpoolrider.AppFragments.ESettings.content.Profile;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileEditNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_esettings_content_profile_activity_edit_name);

        backButton();
    }

    private void backButton() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rl_back_profile_edit_name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
