package android.carpoolrider.RequestRides;

import android.carpoolrider.Profile.ProfileActivity;
import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide.TicketAdapter;
import android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide.TicketEntity;
import android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide.TicketViewModel;
import android.carpoolrider.RequestRides.RequestNewRide.Later.Confirm.LaterConfirmActivity;
import android.carpoolrider.RequestRides.RequestNewRide.RequestNewRideActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebStorage;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RequestRidesFragment extends Fragment {

    View requestRidesView;
    ImageView profileImageView;
    RelativeLayout requestNewRideRelativeLayout;

    private TicketViewModel ticketViewModel;

    public static final int ADD_TICKET_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestRidesView = inflater.inflate(R.layout.fragment_request_rides, container, false);

        RecyclerView recyclerView = (RecyclerView)  requestRidesView.findViewById(R.id.rides_requested_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TicketAdapter ticketAdapter = new TicketAdapter();
        recyclerView.setAdapter(ticketAdapter);

        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel.class); // **** try getActivity() if this doesn't work
        ticketViewModel.getAllTicketEntities().observe(this, new Observer<List<TicketEntity>>() {

            @Override
            public void onChanged(List<TicketEntity> ticketEntities) {
                ticketAdapter.setTicketEntities(ticketEntities);
            }
        });

        // EFFECTS: Call setProfileImageView.
        setProfileImageView();

        // EFFECTS: Call setRequestNewRideRelativeLayout.
        setRequestNewRideRelativeLayout();

        return requestRidesView;
    }

    // EFFECTS: Set OnClickActivity for ProfileActivity.
    private void setProfileImageView() {
        profileImageView = (ImageView) requestRidesView.findViewById(R.id.request_rides_profile);
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

    // EFFECTS: Set OnClickActivity for LaterOriginActivity.
    private void setRequestNewRideRelativeLayout() {
        requestNewRideRelativeLayout = (RelativeLayout) requestRidesView.findViewById(R.id.relative_layout_request_new_ride);
        requestNewRideRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RequestNewRideActivity.class);
                startActivityForResult(intent, ADD_TICKET_REQUEST);

                // EFFECTS: Animation to Profile Activity
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
            }
        });
    }
}
