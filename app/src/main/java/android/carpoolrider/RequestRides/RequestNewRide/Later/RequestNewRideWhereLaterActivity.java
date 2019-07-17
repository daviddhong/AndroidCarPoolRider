package android.carpoolrider.RequestRides.RequestNewRide.Later;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RequestNewRideWhereLaterActivity extends AppCompatActivity {

    ImageView backRequestNewRideOriginActivity;
    RelativeLayout nextActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_where_later);

        // EFFECTS: Call setBackRequestNewRideOriginActivity.
        setBackRequestNewRideWhereActivity();

        // EFFECTS: Call setNextActivity.
        setNextActivityRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideWhereActivity() {
        backRequestNewRideOriginActivity = (ImageView) findViewById(R.id.ic_back_activity_request_new_ride_where_later);
        backRequestNewRideOriginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    // EFFECTS: Set OnClickActivity for nextActivity
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_where_next);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestNewRideWhereLaterActivity.this,
                        RequestNewRideWhenLaterActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}