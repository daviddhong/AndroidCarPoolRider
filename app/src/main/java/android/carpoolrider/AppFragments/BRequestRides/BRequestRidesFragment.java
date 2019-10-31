package android.carpoolrider.AppFragments.BRequestRides;

import android.carpoolrider.AppFragments.BRequestRides.content.RequestRiderRequestTicket;
import android.carpoolrider.AppFragments.BRequestRides.content.RequestRouteActivity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BRequestRidesFragment extends Fragment {

    private View requestRidesView;
    private RecyclerView FriendRecyclerView;
    private DatabaseReference RiderTicketsRef;
    private String currentUserID;
    private static final int ADD_TICKET_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestRidesView = inflater.inflate(R.layout.app_brequestrides_fragment_request_rides, container, false);
        initializeFields();
//        goToMyProfileByProfileImageView();
        postNewRideRequestTicket();
        return requestRidesView;
    }

    private void initializeFields() {
        //initialize RecyclerView
        FriendRecyclerView = (RecyclerView) requestRidesView.findViewById(R.id.rides_requested_recycler_view);
        FriendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //initialize FireBase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");
    }

    // Display the list of my ride request postings with FireBase recycler
    @Override
    public void onStart() {
        super.onStart();

        String UidToString = FirebaseAuth.getInstance().getUid().toString();
        String status_uid = "0" + UidToString;

        Query receiveriderQuery = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("RiderTickets")
                .orderByChild("status_uid")
                .equalTo(status_uid);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<RequestRiderRequestTicket>()
                .setQuery(receiveriderQuery, RequestRiderRequestTicket.class)
                .build();

        final FirebaseRecyclerAdapter<RequestRiderRequestTicket, riderTicketHolder> adapter
                = new FirebaseRecyclerAdapter<RequestRiderRequestTicket, riderTicketHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull riderTicketHolder riderticketholder,
                                            int i, @NonNull RequestRiderRequestTicket riderReqTickets) {
                String ticketID = getRef(i).getKey();
                RiderTicketsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            riderticketholder.riderTo.setText(riderReqTickets.getticketto());
                            riderticketholder.riderFrom.setText(riderReqTickets.getticketfrom());
                            riderticketholder.riderDate.setText(riderReqTickets.getticketdate());
                            riderticketholder.riderTime.setText(riderReqTickets.gettickettime());
                            riderticketholder.riderPrice.setText(riderReqTickets.getticketprice());
                            riderticketholder.riderNumberOfSeats.setText(riderReqTickets.getticketnumberofseats());

                            riderticketholder.deleteTicketButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // does nothing when clicked yet (probably want to delete it)
                                    RiderTicketsRef.child(ticketID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getContext(), "Ticket Deleted", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @NonNull
            @Override
            public riderTicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_brequestrides_layout_request_ride_ticket_entity, parent, false);
                riderTicketHolder viewHolder = new riderTicketHolder(view);
                return viewHolder;
            }
        };
        FriendRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class riderTicketHolder extends RecyclerView.ViewHolder {
        TextView riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice;
        RelativeLayout deleteTicketButton;

        public riderTicketHolder(@NonNull View itemView) {
            super(itemView);
            riderFrom = itemView.findViewById(R.id.text_origin);
            riderTo = itemView.findViewById(R.id.text_destination);
            riderDate = itemView.findViewById(R.id.text_date);
            riderTime = itemView.findViewById(R.id.text_time);
            riderNumberOfSeats = itemView.findViewById(R.id.text_passenger_number);
            riderPrice = itemView.findViewById(R.id.text_earnings_entity);
            deleteTicketButton = itemView.findViewById(R.id.delete_my_ticket_information_entity_request);
        }
    }

//    private void goToMyProfileByProfileImageView() {
//        ImageView profileImageView = (ImageView) requestRidesView.findViewById(R.id.request_rides_profile);
//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(intent);
//                // EFFECTS: Animation to Profile Activity
//                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
//            }
//        });
//    }

    private void postNewRideRequestTicket() {
        RelativeLayout requestNewRideRelativeLayout = (RelativeLayout) requestRidesView.findViewById(R.id.relative_layout_request_new_ride);
        requestNewRideRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RequestRouteActivity.class);
                startActivityForResult(intent, ADD_TICKET_REQUEST);

                // EFFECTS: Animation to Profile Activity
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
            }
        });
    }
}
