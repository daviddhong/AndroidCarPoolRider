package android.carpoolrider.Profile;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    ImageView backArrowEditProfileImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // EFFECTS: setFinishEditProfile Activity.
        setBackArrowEditProfileImageView();
    }

    // EFFECTS: Set OnClickActivity for finish activity.
    private void setBackArrowEditProfileImageView() {
        backArrowEditProfileImageView = (ImageView) findViewById(R.id.edit_profile_back_image_view);
        backArrowEditProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}
