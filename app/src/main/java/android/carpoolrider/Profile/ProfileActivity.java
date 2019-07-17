package android.carpoolrider.Profile;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageView profileBackImageView;
    ImageView editProfileImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // EFFECTS: Call setFinishProfileActivity.
        setFinishProfileActivity();

        // EFFECTS: Call setEditProfileActivity.
        setEditProfileActivity();
    }

    // EFFECTS: Set OnClickActivity for finish activity.
    private void setFinishProfileActivity() {
        profileBackImageView = (ImageView) findViewById(R.id.profile_close_image_view);
        profileBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ProfileActivity to ()Activity.
                overridePendingTransition(R.anim.slide_down, R.anim.slide_vertical_null);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set OnClickActivity for editProfile Activity.
    private void setEditProfileActivity() {
        editProfileImageView = (ImageView) findViewById(R.id.image_view_edit_profile);
        editProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
