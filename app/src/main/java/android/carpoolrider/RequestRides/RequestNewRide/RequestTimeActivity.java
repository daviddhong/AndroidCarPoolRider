package android.carpoolrider.RequestRides.RequestNewRide;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shawnlin.numberpicker.NumberPicker;

public class RequestTimeActivity extends AppCompatActivity {

    private NumberPicker hour;
    private NumberPicker minutes;
    private NumberPicker period;
    String hourString;
    String minutesString;
    String periodString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_time);
        initHourPicker();
        initMinutePicker();
        initPeriodPicker();
        initNext();
        initBack();
    }

    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.rl_back_button_post_new_carpool_time);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // EFFECTS: Initialize the hour number picker.
    private void initHourPicker() {
        hour = (NumberPicker) findViewById(R.id.hour_picker);
        String[] data = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        hour.setMinValue(1);
        hour.setMaxValue(data.length);
        hour.setDisplayedValues(data);
        hour.setFadingEdgeEnabled(false);

        if (hour.getValue() == 1) {
            hourString = "01";
        }

        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    hourString = "02";
                } else if (newVal == 3) {
                    hourString = "03";
                } else if (newVal == 4) {
                    hourString = "04";
                } else if (newVal == 5) {
                    hourString = "05";
                } else if (newVal == 6) {
                    hourString = "06";
                } else if (newVal == 7) {
                    hourString = "07";
                } else if (newVal == 8) {
                    hourString = "08";
                } else if (newVal == 9) {
                    hourString = "09";
                } else {
                    hour.setValue(newVal);
                    hourString = String.valueOf(hour.getValue());
                }
            }
        });;

    }

    // EFFECTS: Initialize the minute number picker.
    private void initMinutePicker() {
        minutes = (NumberPicker) findViewById(R.id.minute_picker);
        String[] data = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                "54", "55", "56", "57", "58", "59"};
        minutes.setMinValue(1);
        minutes.setMaxValue(data.length);
        minutes.setDisplayedValues(data);
        minutes.setFadingEdgeEnabled(false);

        if (minutes.getValue() == 1) {
            minutesString = "00";
        }

        minutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        minutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    minutesString = "01";
                } else if (newVal == 3) {
                    minutesString = "02";
                } else if (newVal == 4) {
                    minutesString = "03";
                } else if (newVal == 5) {
                    minutesString = "04";
                } else if (newVal == 6) {
                    minutesString = "05";
                } else if (newVal == 7) {
                    minutesString = "06";
                } else if (newVal == 8) {
                    minutesString = "07";
                } else if (newVal == 9) {
                    minutesString = "08";
                } else if (newVal == 10) {
                    minutesString = "09";
                } else {
                    minutes.setValue(newVal);
                    minutesString = String.valueOf(minutes.getValue() - 1);
                }
            }
        });
    }

    // EFFECTS: Initialize the period number picker.
    private void initPeriodPicker() {
        period = (NumberPicker) findViewById(R.id.am_pm_picker);
        String[] data = {"AM", "PM"};
        period.setMinValue(1);
        period.setMaxValue(data.length);
        period.setDisplayedValues(data);
        period.setFadingEdgeEnabled(false);

        if (period.getValue() == 1) {
            periodString = "AM";
        }

        period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        period.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    periodString = "PM";
                }
            }
        });
    }

    // EFFECTS: Initialize the next activity.
    private void initNext() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.next_carpool_time_post);
        relativeLayout.setOnClickListener(new View.OnClickListener() {

            Bundle bundle = getIntent().getExtras();
            String origin = bundle.getString("ORIGIN_LOCATION_STRING_KEY");
            String destination = bundle.getString("DESTINATION_LOCATION_STRING_KEY");
            String earnings = bundle.getString("EARNINGS_STRING_KEY");
            String seats = bundle.getString("SEATS_STRING");
            String month = bundle.getString("MONTH_STRING");
            String day = bundle.getString("DAY_STRING");
            String year = bundle.getString("YEAR_STRING");

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestTimeActivity.this, RequestConfirmActivity.class);

                intent.putExtra("ORIGIN_LOCATION_STRING_KEY", origin);
                intent.putExtra("DESTINATION_LOCATION_STRING_KEY", destination);
                intent.putExtra("EARNINGS_STRING_KEY", earnings);
                intent.putExtra("SEATS_STRING", seats);
                intent.putExtra("MONTH_STRING", month);
                intent.putExtra("DAY_STRING", day);
                intent.putExtra("YEAR_STRING", year);
                intent.putExtra("HOUR_STRING", hourString);
                intent.putExtra("MINUTES_STRING", minutesString);
                intent.putExtra("PERIOD_STRING", periodString);

                startActivity(intent);

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
