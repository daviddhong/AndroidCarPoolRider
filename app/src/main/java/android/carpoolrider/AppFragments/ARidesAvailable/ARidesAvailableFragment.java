package android.carpoolrider.AppFragments.ARidesAvailable;

import android.carpoolrider.AppFragments.BRequestRides.content.RequestDriverRequestTicket;
import android.carpoolrider.AppFragments.ARidesAvailable.content.IndividualDriverRequestActivity;
import android.carpoolrider.AppFragments.SendRequestFragment.SendRequestFragment;
import android.carpoolrider.AppFragments.ESettings.content.Profile.ProfileActivity;
import android.carpoolrider.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.firebase.database.Query;

public class ARidesAvailableFragment extends Fragment {
    private View availableRidesView;
    private RecyclerView DriverRecyclerView;
    private DatabaseReference DriverTicketsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        availableRidesView = inflater.inflate(R.layout.app_aridesavailable_fragment_available_rides, container, false);
        initializeFields();
//        acceptOrDeclineReceivedCarpoolRequests();
        return availableRidesView;
    }

    private void initializeFields() {
        // initialize FireBase
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");
        // initialize recyclerView
        DriverRecyclerView = (RecyclerView) availableRidesView.findViewById(R.id.driver_rides_available_recycler_view);
        DriverRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

//    // EFFECTS: Initialize the post new carpool activity.
//    private void acceptOrDeclineReceivedCarpoolRequests() {
//        RelativeLayout gotRequestRelativeLay = (RelativeLayout) availableRidesView.findViewById(R.id.gotRequestlayoutbutton);
//        gotRequestRelativeLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SendRequestFragment.class);
//                startActivity(intent);
//                // EFFECTS: Animation to Profile Activity
//                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
//            }
//        });
//    }


    // Display the list of all driver tickets with FireBase recycler
    @Override
    public void onStart() {
        super.onStart();

// todo query just the tickets that are not yet requested
        Query rreceiveriderQuery = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("DriverTickets")
                .orderByChild("status")
                .equalTo("0");


        FirebaseRecyclerOptions<RequestDriverRequestTicket> options
                = new FirebaseRecyclerOptions.Builder<RequestDriverRequestTicket>()
                .setQuery(rreceiveriderQuery, RequestDriverRequestTicket.class)
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
                        String clickedUserKeyID = getRef(i).getKey();
                        Intent intent = new Intent(getActivity(), IndividualDriverRequestActivity.class);
                        intent.putExtra("clicked_user_id", clickedUserKeyID);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public driverTicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_aridesavailable_layout_posted_ride_ticket_entity, parent, false);
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


}
