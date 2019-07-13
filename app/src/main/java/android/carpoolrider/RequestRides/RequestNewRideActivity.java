package android.carpoolrider.RequestRides;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RequestNewRideActivity extends AppCompatActivity {

    ImageView closeRequestNewRideActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride);

        setCloseRequestNewRideAcitivity();
    }

    // EFFECTS: Set OnClickActivity for closeActivity.
    private void setCloseRequestNewRideAcitivity() {
        closeRequestNewRideActivity = (ImageView) findViewById(R.id.ic_close_activity_request_new_ride);
        closeRequestNewRideActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ProfileActivity to ()Activity.
                overridePendingTransition(R.anim.slide_down, R.anim.slide_vertical_null);
            }
        });
    }

}
