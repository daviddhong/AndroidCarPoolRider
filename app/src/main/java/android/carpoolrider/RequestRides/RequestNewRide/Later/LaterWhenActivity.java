package android.carpoolrider.RequestRides.RequestNewRide.Later;

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

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class LaterWhenActivity extends AppCompatActivity {

    ImageView backRequestNewRideWhenActivity;

    TextView editDateTextView;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    TextView editTimeTextView;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;

    RelativeLayout nextActivityRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_when_later);

        // EFFECTS: Call setBackRequestNewRideWhenActivity.
        setBackRequestNewRideWhenActivity();

        // EFFECTS: Call setEditDate.
        setEditDate();

        // EFFECTS: Call setEditTime.
        setEditTime();

        // EFFECTS: Call setNextActivity.
        setNextActivityRelativeLayout();
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideWhenActivity() {
        backRequestNewRideWhenActivity = (ImageView) findViewById(R.id.ic_back_arrow_request_new_ride_when);
        backRequestNewRideWhenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    // EFFECTS: Set DatePicker.
    private void setEditDate() {
        editDateTextView = (TextView) findViewById(R.id.edit_text_date);
        editDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dateCalendar = Calendar.getInstance();
                int year = dateCalendar.get(Calendar.YEAR);
                int month = dateCalendar.get(Calendar.MONTH);
                int dayOfMonth = dateCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(LaterWhenActivity.this,
                                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                                onDateSetListener,
                                year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // EFFECTS: Convert int month to String monthText.
                String monthText = new DateFormatSymbols().getShortMonths()[month];

                String date = monthText + " " + dayOfMonth + ", " + year;
                editDateTextView.setText(date);
            }
        };
    }

    // EFFECTS: Set TimePicker.
    private void setEditTime() {
        editTimeTextView = (TextView) findViewById(R.id.edit_text_time);
        editTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar timeCalendar = Calendar.getInstance();
                int hour = timeCalendar.get(Calendar.HOUR);
                int minutes = timeCalendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(LaterWhenActivity.this,
                                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                                onTimeSetListener,
                                hour, minutes, false);
                timePickerDialog.show();
            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String period; // EFFECTS: Create empty string to express AM or PM later.
                int hour = hourOfDay;
                // EFFECTS: Convert 24 hour clock to 12 hour clock.
                if (hourOfDay < 12) {
                    period = "AM";
                } else {
                    period = "PM";
                    if (hourOfDay > 12) {
                        hour = hourOfDay - 12;
                    }
                }
                String time = hour + ":" + minute + " " + period;
                editTimeTextView.setText(time);
            }
        };
    }

    // EFFECTS: Set OnClickActivity for nextActivity.
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_when_next);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterWhenActivity.this,
                        LaterPassengerNumberActivity.class);
                intent.putExtra("DATE", editDateTextView.getText());
                intent.putExtra("TIME", editTimeTextView.getText());
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
