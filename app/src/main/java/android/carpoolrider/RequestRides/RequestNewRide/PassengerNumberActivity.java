package android.carpoolrider.RequestRides.RequestNewRide;

import android.carpoolrider.R;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

public class PassengerNumberActivity extends AppCompatActivity {

    ImageView mBackRequestImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_select_pn);

        // EFFECTS: Back request.
        setBackRequestPassengerNumber();

    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestPassengerNumber() {
        mBackRequestImageView = (ImageView) findViewById(R.id.ic_back_arrow_request_new_ride_pn);
        mBackRequestImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from Activity.this to new Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}
