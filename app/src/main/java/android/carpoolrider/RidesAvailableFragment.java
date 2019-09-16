package android.carpoolrider;

import android.carpoolrider.RequestRides.RequestDriverRequestTicket;
import android.carpoolrider.RidesAvailable.IndividualDriverRequestActivity;
import android.carpoolrider.Settings.ProfileActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RidesAvailableFragment extends Fragment {
    private View availableRidesView;
    private RecyclerView DriverRecyclerView;
    private DatabaseReference DriverTicketsRef;
    private String usersIDS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        availableRidesView = inflater.inflate(R.layout.fragment_available_rides, container, false);
        initializeFields();
        goToMyProfileByProfileImageView();
        return availableRidesView;
    }

    private void initializeFields() {
        // initialize FireBase
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");
        // initialize recyclerView
        DriverRecyclerView = (RecyclerView) availableRidesView.findViewById(R.id.driver_rides_available_recycler_view);
        DriverRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Display the list of all driver tickets with FireBase recycler
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<RequestDriverRequestTicket> options
                = new FirebaseRecyclerOptions.Builder<RequestDriverRequestTicket>()
                .setQuery(DriverTicketsRef, RequestDriverRequestTicket.class)
                .build();
        FirebaseRecyclerAdapter<RequestDriverRequestTicket, driverTicketHolder> adapter
                = new FirebaseRecyclerAdapter<RequestDriverRequestTicket, driverTicketHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull driverTicketHolder driverticketholder,
                                            int i, @NonNull RequestDriverRequestTicket driverReqTickets) {
                driverticketholder.riderTo.setText(driverReqTickets.getticketto());
                driverticketholder.riderFrom.setText(driverReqTickets.getticketfrom());
                driverticketholder.riderDate.setText(driverReqTickets.getticketdate());
                driverticketholder.riderTime.setText(driverReqTickets.gettickettime());
                driverticketholder.riderPrice.setText(driverReqTickets.getticketprice());
                driverticketholder.riderNumberOfSeats.setText(driverReqTickets.getticketnumberofseats());
                driverticketholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usersIDS = getRef(i).getKey();
                        Intent intent = new Intent(getActivity(), IndividualDriverRequestActivity.class);
                        intent.putExtra("clicked_user_id", usersIDS);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public driverTicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_posted_ride_ticket_entity, parent, false);
                driverTicketHolder viewHolder = new driverTicketHolder(view);
                return viewHolder;
            }
        };
        DriverRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class driverTicketHolder extends RecyclerView.ViewHolder {
        TextView riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice;

        public driverTicketHolder(@NonNull View itemView) {
            super(itemView);
            riderFrom = itemView.findViewById(R.id.driver_text_origin_post);
            riderTo = itemView.findViewById(R.id.driver_text_destination_post);
            riderDate = itemView.findViewById(R.id.driver_text_date_post);
            riderTime = itemView.findViewById(R.id.driver_text_time_post);
            riderNumberOfSeats = itemView.findViewById(R.id.driver_text_passenger_number_post);
            riderPrice = itemView.findViewById(R.id.driver_text_earnings_entity_post);
        }
    }

    private void goToMyProfileByProfileImageView() {
        ImageView profileImageView = (ImageView) availableRidesView.findViewById(R.id.available_rides_profile);
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
