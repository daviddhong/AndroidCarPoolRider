package android.carpoolrider;

import android.carpoolrider.PendingRequests.AcceptRequestActivity;
import android.carpoolrider.RequestRides.RequestRiderRequestTicket;
import android.carpoolrider.RidesAvailable.IndividualDriverRequestActivity;
import android.carpoolrider.Settings.ProfileActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PendingRequestFragment extends Fragment {
    private View rateDriversView;
    private RecyclerView receivedFriendRequest;
    private DatabaseReference DriverTicketsRef;
    private String currentUserID, clicked_user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rateDriversView = inflater.inflate(R.layout.fragment_pending_requests, container, false);
        initializeFields();
        goToMyProfileByProfileImageView();
        acceptOrDeclineReceivedCarpoolRequests();
        return rateDriversView;
    }

    private void initializeFields() {
        //initialize fields
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");
        //initialize recycler view for received
        receivedFriendRequest = (RecyclerView) rateDriversView.findViewById(R.id.driver_offering_rides_ticekts_recycler_view);
        receivedFriendRequest.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // EFFECTS: Set OnClickActivity for ProfileActivity.
    private void goToMyProfileByProfileImageView() {
        ImageView profileImageView = (ImageView) rateDriversView.findViewById(R.id.rate_drivers_profile);
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

    // EFFECTS: Initialize the post new carpool activity.
    private void acceptOrDeclineReceivedCarpoolRequests() {
        RelativeLayout gotRequestRelativeLay = (RelativeLayout) rateDriversView.findViewById(R.id.gotRequestlayoutbutton);
        gotRequestRelativeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AcceptRequestActivity.class);
                startActivity(intent);
                // EFFECTS: Animation to Profile Activity
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
            }
        });
    }


    // Display the list of all my sent carpool requests with FireBase recycler
    @Override
    public void onStart() {
        super.onStart();
        Query mySentRequestQuery = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("RiderRequestingDriver")
                .child(currentUserID)
                .orderByChild("requeststatus")
                .equalTo("sent");

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<RequestRiderRequestTicket>()
                        .setQuery(mySentRequestQuery, RequestRiderRequestTicket.class)
                        .build();
        final FirebaseRecyclerAdapter<RequestRiderRequestTicket, riderTicketHolder> adapter
                = new FirebaseRecyclerAdapter<RequestRiderRequestTicket, riderTicketHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull riderTicketHolder holder,
                                            int i, @NonNull RequestRiderRequestTicket riderReqTickets) {
                //get all friend request list and then get their information from FireBase Users node to tickets
                final String list_user_id = getRef(i).getKey();
                DatabaseReference getTypeRef = getRef(i).child("requeststatus").getRef();
                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String type = dataSnapshot.getValue().toString();
                            if (type.equals("sent")) {
                                DriverTicketsRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            final String ticketTo = dataSnapshot.child("To").getValue().toString();
                                            final String ticketFrom = dataSnapshot.child("From").getValue().toString();
                                            final String ticketDate = dataSnapshot.child("Date").getValue().toString();
                                            final String ticketTime = dataSnapshot.child("Time").getValue().toString();
                                            final String ticketPrice = dataSnapshot.child("Price").getValue().toString();
                                            final String ticketNumberOfSeats = dataSnapshot.child("NumberOfSeats").getValue().toString();
                                            holder.riderTo.setText(ticketTo);
                                            holder.riderFrom.setText(ticketFrom);
                                            holder.riderDate.setText(ticketDate);
                                            holder.riderTime.setText(ticketTime);
                                            holder.riderPrice.setText(ticketPrice);
                                            holder.riderNumberOfSeats.setText(ticketNumberOfSeats);
                                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    clicked_user_id = getRef(i).getKey();
                                                    Intent intent = new Intent(getActivity(), IndividualDriverRequestActivity.class);
                                                    intent.putExtra("clicked_user_id", clicked_user_id);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                            if (type.equals("received")) {
                                holder.itemView.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @NonNull
            @Override
            public riderTicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_request_ride_ticket_entity, parent, false);
                riderTicketHolder viewHolder = new riderTicketHolder(view);
                return viewHolder;
            }
        };
        receivedFriendRequest.setAdapter(adapter);
        adapter.startListening();
    }

    public static class riderTicketHolder extends RecyclerView.ViewHolder {
        TextView riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice;

        public riderTicketHolder(@NonNull View itemView) {
            super(itemView);
            riderFrom = itemView.findViewById(R.id.myr_text_origin);
            riderTo = itemView.findViewById(R.id.myr_text_destination);
            riderDate = itemView.findViewById(R.id.myr_text_date);
            riderTime = itemView.findViewById(R.id.myr_text_time);
            riderNumberOfSeats = itemView.findViewById(R.id.myr_text_passenger_number);
            riderPrice = itemView.findViewById(R.id.myr_text_earnings_entity);
        }
    }
}
