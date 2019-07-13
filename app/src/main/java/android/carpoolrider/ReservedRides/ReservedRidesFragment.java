package android.carpoolrider.ReservedRides;

import android.carpoolrider.Profile.ProfileActivity;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReservedRidesFragment extends Fragment {

    View reservedRidesView;
    ImageView profileImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reservedRidesView = inflater.inflate(R.layout.fragment_reserved_rides, container, false);

        // EFFECTS: Call setProfileImageView.
        setProfileImageView();

        return reservedRidesView;
    }

    // EFFECTS: Set OnClickActivity for ProfileActivity.
    private void setProfileImageView() {
        profileImageView = (ImageView) reservedRidesView.findViewById(R.id.reserved_rides_profile);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);

                // EFFECTS: Animation to Profile Activity
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
            }
        });
    }
}
