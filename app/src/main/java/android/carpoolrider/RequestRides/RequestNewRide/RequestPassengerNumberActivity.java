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

import com.shawnlin.numberpicker.NumberPicker;

public class RequestPassengerNumberActivity extends AppCompatActivity {

    NumberPicker seats;
    String seatsString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_select_pn);

        // EFFECTS: Back request.
        initBack();

    }

    // EFFECTS: Initialize the number picker for the number of seats.
    private void initSeatNumberPicker() {
        seats = (NumberPicker) findViewById(R.id.pn_picker);
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        seats.setMinValue(1);
        seats.setMaxValue(data.length);
        seats.setDisplayedValues(data);
        seats.setFadingEdgeEnabled(false);

        seatsString = String.valueOf(seats.getValue());

    }

    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.relative_layout_header_select_pn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // EFFECTS: Initialize the next activity.
    private void initNext() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.next_carpool_seat_post);
        relativeLayout.setOnClickListener(new View.OnClickListener() {

            Bundle bundle = getIntent().getExtras();
            String origin = bundle.getString("ORIGIN_LOCATION_STRING_KEY");
            String destination = bundle.getString("DESTINATION_LOCATION_STRING_KEY");
            String earnings = bundle.getString("EARNINGS_STRING_KEY");

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestPassengerNumberActivity.this, RequestDateActivity.class);

                intent.putExtra("ORIGIN_LOCATION_STRING_KEY", origin);
                intent.putExtra("DESTINATION_LOCATION_STRING_KEY", destination);
                intent.putExtra("EARNINGS_STRING_KEY", earnings);
                intent.putExtra("SEATS_STRING", seatsString);

                startActivity(intent);

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
