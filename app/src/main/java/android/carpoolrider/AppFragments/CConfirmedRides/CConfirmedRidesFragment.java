package android.carpoolrider.AppFragments.CConfirmedRides;

import android.carpoolrider.AppFragments.CConfirmedRides.content.IndividualConfirmedTicketRiderDriverActivity;
import android.carpoolrider.AppFragments.BRequestRides.content.RequestDriverRequestTicket;
import android.carpoolrider.AppFragments.ESettings.content.Profile.ProfileActivity;
import android.carpoolrider.R;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CConfirmedRidesFragment extends Fragment {

    private View reservedRidesView;
    private RecyclerView DriverRecyclerView;
    private DatabaseReference ConfirmedTicketsRef, RiderTicketsRef, DriverTicketsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reservedRidesView = inflater.inflate(R.layout.app_cconfirmedrides_fragment_confirmed_rides, container, false);
        initializeFields();
//        goToMyProfileFromProfileImageView();
        return reservedRidesView;
    }

    private void initializeFields() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserID = mAuth.getCurrentUser().getUid();
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");
        DriverTicketsRef = FirebaseDatabase.getInstance().getReference().child("DriverTickets");

        ConfirmedTicketsRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedMatch").child(currentUserID);
        DriverRecyclerView = (RecyclerView) reservedRidesView.findViewById(R.id.confirmed_rides_driver_rides_available_recycler_view);
        DriverRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Display the list of confirmed/matched rides with FireBase recycler
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<RequestDriverRequestTicket> options
                = new FirebaseRecyclerOptions.Builder<RequestDriverRequestTicket>()
                .setQuery(ConfirmedTicketsRef, RequestDriverRequestTicket.class)
                .build();
        FirebaseRecyclerAdapter<RequestDriverRequestTicket, driverTicketHolder> adapter
                = new FirebaseRecyclerAdapter<RequestDriverRequestTicket, driverTicketHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull driverTicketHolder driverticketholder,
                                            int i, @NonNull RequestDriverRequestTicket driverReqTickets) {

                String usersIDS = getRef(i).getKey();
                RiderTicketsRef.child(usersIDS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            final String ticketTo = dataSnapshot.child("To").getValue().toString();
                            final String ticketFrom = dataSnapshot.child("From").getValue().toString();
                            final String ticketDate = dataSnapshot.child("Date").getValue().toString();
                            final String ticketTime = dataSnapshot.child("Time").getValue().toString();
                            final String ticketPrice = dataSnapshot.child("Price").getValue().toString();
                            final String ticketNumberOfSeats = dataSnapshot.child("NumberOfSeats").getValue().toString();
                            driverticketholder.riderTo.setText(ticketTo);
                            driverticketholder.riderFrom.setText(ticketFrom);
                            driverticketholder.riderDate.setText(ticketDate);
                            driverticketholder.riderTime.setText(ticketTime);
                            driverticketholder.riderPrice.setText(ticketPrice);
                            driverticketholder.riderNumberOfSeats.setText(ticketNumberOfSeats);
                            driverticketholder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    final String usersIDS = getRef(i).getKey();
                                    Intent intent = new Intent(getActivity(), IndividualConfirmedTicketRiderDriverActivity.class);
                                    intent.putExtra("clicked_user_id", usersIDS);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            DriverTicketsRef.child(usersIDS).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        final String ticketTo = dataSnapshot.child("To").getValue().toString();
                                        final String ticketFrom = dataSnapshot.child("From").getValue().toString();
                                        final String ticketDate = dataSnapshot.child("Date").getValue().toString();
                                        final String ticketTime = dataSnapshot.child("Time").getValue().toString();
                                        final String ticketPrice = dataSnapshot.child("Price").getValue().toString();
                                        final String ticketNumberOfSeats = dataSnapshot.child("NumberOfSeats").getValue().toString();

                                        driverticketholder.riderTo.setText(ticketTo);
                                        driverticketholder.riderFrom.setText(ticketFrom);
                                        driverticketholder.riderDate.setText(ticketDate);
                                        driverticketholder.riderTime.setText(ticketTime);
                                        driverticketholder.riderPrice.setText(ticketPrice);
                                        driverticketholder.riderNumberOfSeats.setText(ticketNumberOfSeats);
                                        driverticketholder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
//                                            final String usersIDS = getRef(i).getKey();
                                                Intent intent = new Intent(getActivity(), IndividualConfirmedTicketRiderDriverActivity.class);
                                                intent.putExtra("clicked_user_id", usersIDS);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

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
            public driverTicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_cconfirmedrides_layout_confirmed_ride_ticket_entity, parent, false);
                return new driverTicketHolder(view);
            }
        };
        DriverRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class driverTicketHolder extends RecyclerView.ViewHolder {
        TextView riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice;

        private driverTicketHolder(@NonNull View itemView) {
            super(itemView);
            riderFrom = itemView.findViewById(R.id.cc_text_origin);
            riderTo = itemView.findViewById(R.id.cc_text_destination);
            riderDate = itemView.findViewById(R.id.cc_text_date);
            riderTime = itemView.findViewById(R.id.cc_text_time);
            riderNumberOfSeats = itemView.findViewById(R.id.cc_text_passenger_number);
            riderPrice = itemView.findViewById(R.id.cc_text_earnings_entity);
        }
    }
//
//    private void goToMyProfileFromProfileImageView() {
//        ImageView profileImageView = (ImageView) reservedRidesView.findViewById(R.id.reserved_rides_profile);
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
}
