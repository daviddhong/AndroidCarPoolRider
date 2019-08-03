package android.carpoolrider.RequestRides.RequestNewRide;

import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.Later.LaterOriginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RequestNewRideActivity extends AppCompatActivity {

    ImageView closeRequestNewRideActivity;
    RelativeLayout requestForLaterRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride);

        // EFFECTS: Call setCloseRequestNewRideActivity.
        setCloseRequestNewRideActivity();

        // EFFECTS: Call setRequestForLaterRelativeLayout.
        setRequestForLaterRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for closeActivity.
    private void setCloseRequestNewRideActivity() {
        closeRequestNewRideActivity = (ImageView) findViewById(R.id.ic_back_activity_request_new_ride_where_later);
        closeRequestNewRideActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from RequestNewRideActivity to ()Activity.
                overridePendingTransition(R.anim.slide_down, R.anim.slide_vertical_null);
            }
        });
    }

    // EFFECTS: SetOnClickActivity for LaterImageView.
    private void setRequestForLaterRelativeLayout() {
        requestForLaterRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_request_for_later);
        requestForLaterRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestNewRideActivity.this, LaterOriginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
