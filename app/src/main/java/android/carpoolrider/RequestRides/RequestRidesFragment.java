package android.carpoolrider.RequestRides;

import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RequestRidesFragment extends Fragment {

    View requestRidesView;
    RelativeLayout requestNewRideRelativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestRidesView = inflater.inflate(R.layout.fragment_request_rides, container, false);

        // EFFECTS: Call setRequestNewRideRelativeLayout.
        setRequestNewRideRelativeLayout();

        return requestRidesView;
    }

    // EFFECTS: Set OnClickActivity for RequestNewRideActivity.
    private void setRequestNewRideRelativeLayout() {
        requestNewRideRelativeLayout = (RelativeLayout) requestRidesView.findViewById(R.id.relative_layout_request_new_ride);
        requestNewRideRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RequestNewRideActivity.class);
                startActivity(intent);
            }
        });
    }

}
