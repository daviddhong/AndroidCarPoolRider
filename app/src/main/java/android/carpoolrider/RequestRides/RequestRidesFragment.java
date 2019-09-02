package android.carpoolrider.RequestRides;

import android.carpoolrider.Profile.ProfileActivity;
import android.carpoolrider.R;
import android.carpoolrider.RequestRides.RequestNewRide.RouteActivity;
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
import com.google.firebase.database.ValueEventListener;

public class RequestRidesFragment extends Fragment {

    private View requestRidesView;
    private ImageView profileImageView;
    private RelativeLayout requestNewRideRelativeLayout;
//    private TicketViewModel ticketViewModel;

    private RecyclerView FriendRecyclerView;
    private DatabaseReference RiderTicketsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    public static final int ADD_TICKET_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestRidesView = inflater.inflate(R.layout.fragment_request_rides, container, false);

        displaysFriendsListbyRecyclerView();

        // EFFECTS: Call setProfileImageView.
        setProfileImageView();

        // EFFECTS: Call setRequestNewRideRelativeLayout.
        setRequestNewRideRelativeLayout();

        return requestRidesView;
    }

    //use recycler view and friend list adapter to display list of friends
    private void displaysFriendsListbyRecyclerView() {
        FriendRecyclerView = (RecyclerView) requestRidesView.findViewById(R.id.rides_requested_recycler_view);
        FriendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets").child(currentUserID);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }


    // create firebase recycler
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<RiderRequestTicket>()
                .setQuery(RiderTicketsRef, RiderRequestTicket.class)
                .build();

        final FirebaseRecyclerAdapter<RiderRequestTicket, riderTicketHolder> adapter
                = new FirebaseRecyclerAdapter<RiderRequestTicket, riderTicketHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull riderTicketHolder riderticketholder, int i, @NonNull RiderRequestTicket riderReqTickets) {
                String usersIDS = getRef(i).getKey();
                UsersRef.child(usersIDS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            final String riderTo = dataSnapshot.child("To").getValue().toString();
                            final String riderFrom = dataSnapshot.child("From").getValue().toString();
                            riderticketholder.riderTo.setText(riderTo);
                            riderticketholder.riderFrom.setText(riderFrom);
                            riderticketholder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // does nothing when clicked yet (probably want to delete it)
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_request_ride_ticket_entity, parent, false);
                riderTicketHolder viewHolder = new riderTicketHolder(view);
                return viewHolder;
            }
        };
        FriendRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    public static class riderTicketHolder extends RecyclerView.ViewHolder {
        TextView riderTo, riderFrom;
        public riderTicketHolder(@NonNull View itemView) {
            super(itemView);
            riderFrom = itemView.findViewById(R.id.text_origin);
            riderTo = itemView.findViewById(R.id.text_destination);
        }
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

    // EFFECTS: Set OnClickActivity for RouteActivity.
    private void setRequestNewRideRelativeLayout() {
        requestNewRideRelativeLayout = (RelativeLayout) requestRidesView.findViewById(R.id.relative_layout_request_new_ride);
        requestNewRideRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                startActivityForResult(intent, ADD_TICKET_REQUEST);

                // EFFECTS: Animation to Profile Activity
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_vertical_null);
            }
        });
    }
}
