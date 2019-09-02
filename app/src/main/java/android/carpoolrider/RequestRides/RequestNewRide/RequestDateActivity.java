package android.carpoolrider.RequestRides.RequestNewRide;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

public class RequestDateActivity extends AppCompatActivity {

    RelativeLayout backRequestNewRideWhenActivity;

    TextView editDateTextView;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    TextView editTimeTextView;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;

    RelativeLayout nextActivityRelativeLayout;

    private NumberPicker month;
    private NumberPicker day;
    private NumberPicker year;
    String monthString;
    String dayString;
    String yearString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_date);

        // EFFECTS: Call setBackRequestNewRideWhenActivity.
        setBackRequestNewRideWhenActivity();

        initMonthNumberPicker();
        initDayNumberPicker();
        initYearNumberPicker();

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

    // EFFECTS: Initialize the month number picker.
    private void initMonthNumberPicker() {
        month = (NumberPicker) findViewById(R.id.month_picker);
        String[] data = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sept", "Oct", "Nov", "Dec"};
        month.setMinValue(1);
        month.setMaxValue(data.length);
        month.setDisplayedValues(data);
        month.setFadingEdgeEnabled(false);

        monthString = String.valueOf(month.getValue());
    }

    // EFFECTS: Initialize the day number picker.
    private void initDayNumberPicker() {
        day = (NumberPicker) findViewById(R.id.day_picker);
        String[] data = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31"};
        day.setMinValue(1);
        day.setMaxValue(data.length);
        day.setDisplayedValues(data);
        day.setFadingEdgeEnabled(false);

        dayString = String.valueOf(day.getValue());
    }

    // EFFECTS: Initialize the year number picker.
    private void initYearNumberPicker() {
        year = (NumberPicker) findViewById(R.id.year_picker);
        Calendar yearCalendar = Calendar.getInstance();
        int yearInt = yearCalendar.get(Calendar.YEAR);
        int yearIntTwo = yearCalendar.get(Calendar.YEAR) + 1;
        String yearString = String.valueOf(yearInt);
        String yearStringTwo = String.valueOf(yearIntTwo);
        String[] data = {yearString, yearStringTwo};
        year.setMinValue(1);
        year.setMaxValue(data.length);
        year.setDisplayedValues(data);
        year.setFadingEdgeEnabled(false);

        yearString = String.valueOf(year.getValue());
    }

    // EFFECTS: Set OnClickActivity for nextActivity.
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.next_carpool_date_request);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestDateActivity.this,
                        RequestTimeActivity.class);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
