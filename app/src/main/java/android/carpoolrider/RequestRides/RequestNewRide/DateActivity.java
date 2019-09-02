package android.carpoolrider.RequestRides.RequestNewRide;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    RelativeLayout backRequestNewRideWhenActivity;

    TextView editDateTextView;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    TextView editTimeTextView;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;

    RelativeLayout nextActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_date);

        // EFFECTS: Call setBackRequestNewRideWhenActivity.
        setBackRequestNewRideWhenActivity();

        // EFFECTS: Call setNextActivity.
        setNextActivityRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideWhenActivity() {
        backRequestNewRideWhenActivity = (RelativeLayout) findViewById(R.id.rl_back_button_request_new_carpool_date);
        backRequestNewRideWhenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    // EFFECTS: Set OnClickActivity for nextActivity.
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.next_carpool_date_request);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateActivity.this,
                        TimeActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
