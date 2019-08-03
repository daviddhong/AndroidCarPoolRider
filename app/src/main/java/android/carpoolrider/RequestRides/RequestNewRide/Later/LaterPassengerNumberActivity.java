package android.carpoolrider.RequestRides.RequestNewRide.Later;

import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.Later.Confirm.LaterConfirmActivity;
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

public class LaterPassengerNumberActivity extends AppCompatActivity {

    ImageView mBackRequestImageView;

    ImageView mSeatOneImageView;
    ImageView mSeatTwoImageView;
    ImageView mSeatThreeImageView;
    ImageView mSeatFourImageView;
    ImageView mSeatFiveImageView;
    ImageView mSeatSixImageView;
    ImageView mSeatSevenImageView;
    ImageView mSeatEightImageView;
    ImageView mSeatNineImageView;
    ImageView mSeatTenImageView;
    ImageView mSeatElevenImageView;

    TextView mNumberOfPassengersTextView;

    private int counter = 0;

    RelativeLayout mDoneImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_select_pn);

        // EFFECTS: Back request.
        setBackRequestPassengerNumber();

        // EFFECTS: Call seatSelectorHelper.
        seatSelectorHelper();

        // EFFECTS: Call doneRelativeLayout.
        doneRelativeLayout();
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

    // EFFECTS: Helper for SeatSelector.
    private void seatSelectorHelper() {
        // EFFECTS: Call selectSeats 1..11.
        selectSeatOne();
        selectSeatTwo();
        selectSeatThree();
        selectSeatFour();
        selectSeatFive();
        selectSeatSix();
        selectSeatSeven();
        selectSeatEight();
        selectSeatNine();
        selectSeatTen();
        selectSeatEleven();
    }

    // EFFECTS: Select seatOne.
    private void selectSeatOne() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatOneImageView = (ImageView) findViewById(R.id.seat_one);
        mSeatOneImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatOneImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                // EFFECTS: The counter can increment by one max and decrease by one max. When the
                // the counter is decreased by one, the color of the ImageView is also changed to
                // its default grey color.
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatOneImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                // EFFECTS: Sets the counter value to the TextView that show total passenger number.
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatTwo.
    private void selectSeatTwo() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatTwoImageView = (ImageView) findViewById(R.id.seat_two);
        mSeatTwoImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatTwoImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatTwoImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatThree.
    private void selectSeatThree() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatThreeImageView = (ImageView) findViewById(R.id.seat_three);
        mSeatThreeImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatThreeImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatThreeImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatFour.
    private void selectSeatFour() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatFourImageView = (ImageView) findViewById(R.id.seat_four);
        mSeatFourImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatFourImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatFourImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatFive.
    private void selectSeatFive() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatFiveImageView = (ImageView) findViewById(R.id.seat_five);
        mSeatFiveImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatFiveImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatFiveImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatSix.
    private void selectSeatSix() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatSixImageView = (ImageView) findViewById(R.id.seat_six);
        mSeatSixImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatSixImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatSixImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatSeven.
    private void selectSeatSeven() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatSevenImageView = (ImageView) findViewById(R.id.seat_seven);
        mSeatSevenImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatSevenImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatSevenImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatEight.
    private void selectSeatEight() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatEightImageView = (ImageView) findViewById(R.id.seat_eight);
        mSeatEightImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatEightImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatEightImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatNine.
    private void selectSeatNine() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatNineImageView = (ImageView) findViewById(R.id.seat_nine);
        mSeatNineImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatNineImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatNineImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatTen.
    private void selectSeatTen() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatTenImageView = (ImageView) findViewById(R.id.seat_ten);
        mSeatTenImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatTenImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatTenImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatEleven.
    private void selectSeatEleven() {
        mNumberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        mSeatElevenImageView = (ImageView) findViewById(R.id.seat_eleven);
        mSeatElevenImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(mSeatElevenImageView,
                        ColorStateList.valueOf(Color.parseColor("#4264fb")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(mSeatElevenImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                mNumberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: doneRelativeLayout OnClickActivity when finished selecting the number of passengers. The
    // TextView representing the number of seats selected will be passed to
    // LaterPassengerNumberActivity, LaterConfirmActivity, and ConfirmPassengerNumberActivity.
    private void doneRelativeLayout() {
        mDoneImageView = (RelativeLayout) findViewById(R.id.relative_layout_done_select_pn);
        mDoneImageView.setOnClickListener(new View.OnClickListener() {

            // EFFECTS: Data retrieved from LaterWhenActivity for date and time of the carpool.
            Bundle bundle = getIntent().getExtras();
            String date = bundle.getString("DATE");
            String time = bundle.getString("TIME");
            String origin = bundle.getString("ORIGIN");
            String destination = bundle.getString("DESTINATION");

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterPassengerNumberActivity.this,
                        LaterConfirmActivity.class);
                // EFFECTS: Function to pass the value of the counter. The first value of .putExtra
                // is the key and the second value is the object.
                intent.putExtra("PASSENGER_NUMBER_SELECTED", mNumberOfPassengersTextView.getText().toString());

                // EFFECTS: Send the date of the carpool value.
                intent.putExtra("DATE_VALUE", date);
                // EFFECTS: Send the time of the carpool value.
                intent.putExtra("TIME_VALUE", time);

                intent.putExtra("ORIGIN", origin);
                intent.putExtra("DESTINATION", destination);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
