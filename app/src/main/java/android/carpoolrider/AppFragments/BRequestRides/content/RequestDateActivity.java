package android.carpoolrider.AppFragments.BRequestRides.content;

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
        setContentView(R.layout.app_brequestrides_content_activity_request_date);

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

        if (month.getValue() == 1) {
            monthString = "Jan";
        }

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    monthString = "Feb";
                } else if (newVal == 3) {
                    monthString = "Mar";
                } else if (newVal == 4) {
                    monthString = "Apr";
                } else if (newVal == 5) {
                    monthString = "May";
                } else if (newVal == 6) {
                    monthString = "Jun";
                } else if (newVal == 7) {
                    monthString = "Jul";
                } else if (newVal == 8) {
                    monthString = "Aug";
                } else if (newVal == 9) {
                    monthString = "Sept";
                } else if (newVal == 10) {
                    monthString = "Oct";
                } else if (newVal == 11) {
                    monthString = "Nov";
                } else if (newVal == 12) {
                    monthString = "Dec";
                }
            }
        });
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

        if (day.getValue() == 1) {
            dayString = "01";
        }

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        day.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    dayString = "02";
                } else if (newVal == 3) {
                    dayString = "03";
                } else if (newVal == 4) {
                    dayString = "04";
                } else if (newVal == 5) {
                    dayString = "05";
                } else if (newVal == 6) {
                    dayString = "06";
                } else if (newVal == 7) {
                    dayString = "07";
                } else if (newVal == 8) {
                    dayString = "08";
                } else if (newVal == 9) {
                    dayString = "09";
                } else {
                    day.setValue(newVal);
                    dayString = String.valueOf(day.getValue());
                }
            }
        });
    }

    // EFFECTS: Initialize the year number picker.
    private void initYearNumberPicker() {
        year = (NumberPicker) findViewById(R.id.year_picker);
        Calendar yearCalendar = Calendar.getInstance();
        int yearInt = yearCalendar.get(Calendar.YEAR);
        int yearIntTwo = yearCalendar.get(Calendar.YEAR) + 1;
        String yearStringOne = String.valueOf(yearInt);
        String yearStringTwo = String.valueOf(yearIntTwo);
        String[] data = {yearStringOne, yearStringTwo};
        year.setMinValue(1);
        year.setMaxValue(data.length);
        year.setDisplayedValues(data);
        year.setFadingEdgeEnabled(false);

        if (year.getValue() == 1) {
            yearString = yearStringOne;
        }

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 2) {
                    yearString = yearStringTwo;
                }
            }
        });
    }

    // EFFECTS: Set OnClickActivity for nextActivity.
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.next_carpool_date_request);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            Bundle bundle = getIntent().getExtras();
            String origin = bundle.getString("ORIGIN_LOCATION_STRING_KEY");
            String destination = bundle.getString("DESTINATION_LOCATION_STRING_KEY");
            String earnings = bundle.getString("EARNINGS_STRING_KEY");
            String seats = bundle.getString("SEATS_STRING");

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestDateActivity.this,
                        RequestTimeActivity.class);

                intent.putExtra("ORIGIN_LOCATION_STRING_KEY", origin);
                intent.putExtra("DESTINATION_LOCATION_STRING_KEY", destination);
                intent.putExtra("EARNINGS_STRING_KEY", earnings);
                intent.putExtra("SEATS_STRING", seats);
                intent.putExtra("MONTH_STRING", monthString);
                intent.putExtra("DAY_STRING", dayString);
                intent.putExtra("YEAR_STRING", yearString);

                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
