package android.carpoolrider.RequestRides.RequestNewRide.Later.PassengerNumber;

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

public class SelectPassengerNumberActivity extends AppCompatActivity {

    ImageView seatOneImageView;
    ImageView seatTwoImageView;
    ImageView seatThreeImageView;
    ImageView seatFourImageView;
    ImageView seatFiveImageView;
    ImageView seatSixImageView;
    ImageView seatSevenImageView;
    ImageView seatEightImageView;
    ImageView seatNineImageView;
    ImageView seatTenImageView;
    ImageView seatElevenImageView;

    TextView numberOfPassengersTextView;

    private int counter = 0;

    RelativeLayout doneImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_new_ride_select_pn);

        // EFFECTS: Call seatSelectorHelper.
        seatSelectorHelper();

        // EFFECTS: Call doneRelativeLayout.
        doneRelativeLayout();
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
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatOneImageView = (ImageView) findViewById(R.id.seat_one);
        seatOneImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatOneImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                // EFFECTS: The counter can increment by one max and decrease by one max. When the
                // the counter is decreased by one, the color of the ImageView is also changed to
                // its default grey color.
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatOneImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                // EFFECTS: Sets the counter value to the TextView that show total passenger number.
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: UnSelect seatOne.
    private void unSelectSeatOne() {
    }

    // EFFECTS: Select seatTwo.
    private void selectSeatTwo() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatTwoImageView = (ImageView) findViewById(R.id.seat_two);
        seatTwoImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatTwoImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatTwoImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatThree.
    private void selectSeatThree() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatThreeImageView = (ImageView) findViewById(R.id.seat_three);
        seatThreeImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatThreeImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatThreeImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatFour.
    private void selectSeatFour() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatFourImageView = (ImageView) findViewById(R.id.seat_four);
        seatFourImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatFourImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatFourImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatFive.
    private void selectSeatFive() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatFiveImageView = (ImageView) findViewById(R.id.seat_five);
        seatFiveImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatFiveImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatFiveImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatSix.
    private void selectSeatSix() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatSixImageView = (ImageView) findViewById(R.id.seat_six);
        seatSixImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatSixImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatSixImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatSeven.
    private void selectSeatSeven() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatSevenImageView = (ImageView) findViewById(R.id.seat_seven);
        seatSevenImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatSevenImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatSevenImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatEight.
    private void selectSeatEight() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatEightImageView = (ImageView) findViewById(R.id.seat_eight);
        seatEightImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatEightImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatEightImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatNine.
    private void selectSeatNine() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatNineImageView = (ImageView) findViewById(R.id.seat_nine);
        seatNineImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatNineImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatNineImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatTen.
    private void selectSeatTen() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatTenImageView = (ImageView) findViewById(R.id.seat_ten);
        seatTenImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatTenImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatTenImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // EFFECTS: Select seatEleven.
    private void selectSeatEleven() {
        numberOfPassengersTextView = (TextView) findViewById(R.id.text_view_total_seat_count);
        seatElevenImageView = (ImageView) findViewById(R.id.seat_eleven);
        seatElevenImageView.setOnClickListener(new View.OnClickListener() {
            boolean clicked;

            @Override
            public void onClick(View v) {
                ImageViewCompat.setImageTintList(seatElevenImageView,
                        ColorStateList.valueOf(Color.parseColor("#ff9900")));
                if (clicked) {
                    counter--;
                    ImageViewCompat.setImageTintList(seatElevenImageView,
                            ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    clicked = false;
                } else {
                    counter++;
                    clicked = true;
                }
                numberOfPassengersTextView.setText(Integer.toString(counter));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: doneRelativeLayout OnClickActivity when finished selecting the number of passengers. The
    // TextView representing the number of seats selected will be passed to
    // LaterPassengerNumberActivity, LaterConfirmActivity, and ConfirmEditPassengerNumberActivity.
    private void doneRelativeLayout() {
        doneImageView = (RelativeLayout) findViewById(R.id.relative_layout_done_select_pn);
        doneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPassengerNumberActivity.this,
                        LaterPassengerNumberActivity.class);
                // EFFECTS: Function to pass the value of the counter. The first value of .putExtra
                // is the key and the second value is the object.
                intent.putExtra("passenger_number", numberOfPassengersTextView.getText().toString());
                startActivity(intent);
                // EFFECTS: Animation from Activity.this to Activity.class.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
