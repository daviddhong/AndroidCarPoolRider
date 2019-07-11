package android.carpoolrider.RequestRides;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RequestNewRideActivity extends AppCompatActivity {

    ImageView closeRequestNewRideAcitivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride);

        setCloseRequestNewRideAcitivity();
    }

    // EFFECTS: Set OnClickActivity for closeActivity.
    private void setCloseRequestNewRideAcitivity() {
        closeRequestNewRideAcitivity = (ImageView) findViewById(R.id.ic_close_activity_request_new_ride);
        closeRequestNewRideAcitivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
