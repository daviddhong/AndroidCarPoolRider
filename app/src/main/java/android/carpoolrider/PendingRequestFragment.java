package android.carpoolrider;

import android.app.AlertDialog;
import android.carpoolrider.RequestRides.RequestDriverRequestTicket;
import android.carpoolrider.Settings.ProfileActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class PendingRequestFragment extends Fragment {

    View rateDriversView;
    ImageView profileImageView;

    private RecyclerView receivedFriendRequest, sentFriendRequest;
    private DatabaseReference DriverRequestingRiderRef, RiderTicketsRef, UsersRef, ConfirmedCarpoolFriends;
    private FirebaseAuth mAuth;
    private String currentUserID, uniquekey, type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rateDriversView = inflater.inflate(R.layout.fragment_pending_requests, container, false);

        // EFFECTS: Call setProfileImageView.
        setProfileImageView();

        //initialize fields
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        DriverRequestingRiderRef = FirebaseDatabase.getInstance().getReference().child("DriverRequestingRider");
        ConfirmedCarpoolFriends = FirebaseDatabase.getInstance().getReference().child("Friends");
        RiderTicketsRef = FirebaseDatabase.getInstance().getReference().child("RiderTickets");

        //initialize recycler view for received
        receivedFriendRequest = (RecyclerView) rateDriversView.findViewById(R.id.driver_offering_rides_ticekts_recycler_view);
        receivedFriendRequest.setLayoutManager(new LinearLayoutManager(getContext()));

        return rateDriversView;
    }

    // EFFECTS: Set OnClickActivity for ProfileActivity.
    private void setProfileImageView() {
        profileImageView = (ImageView) rateDriversView.findViewById(R.id.rate_drivers_profile);
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


    @Override
    public void onStart() {
        super.onStart();

        uniquekey = DriverRequestingRiderRef.child(currentUserID).getKey();

        Query receiveriderQuery = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("DriverRequestingRider")
                //todo needs to be the key...
//                .child("qolKEsqcDdMv4Udl9fhSHfrWLFz2")
//                .child(uniquekey)
                .orderByChild("requeststatus")
                .equalTo("sent");


        FirebaseRecyclerOptions<RequestDriverRequestTicket> options2 =
                new FirebaseRecyclerOptions.Builder<RequestDriverRequestTicket>()
                        .setQuery(receiveriderQuery, RequestDriverRequestTicket.class)
                        .build();

        FirebaseRecyclerAdapter<RequestDriverRequestTicket, RequestsViewHolder> adapter1
                = new FirebaseRecyclerAdapter<RequestDriverRequestTicket, RequestsViewHolder>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder,
                                            int i, @NonNull RequestDriverRequestTicket riderReqTickets) {

                //get all friend request list and then get their information from Users node
                final String list_user_id = getRef(i).getKey();
                DatabaseReference getTypeRef = getRef(i).child("requeststatus").getRef();
                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            String type = dataSnapshot.getValue().toString();

                            if (type.equals("received")) {
                                UsersRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                        holder.riderTo.setText(riderReqTickets.getticketto());
                                        holder.riderFrom.setText(riderReqTickets.getticketfrom());
                                        holder.riderDate.setText(riderReqTickets.getticketdate());
                                        holder.riderTime.setText(riderReqTickets.gettickettime());
                                        holder.riderPrice.setText(riderReqTickets.getticketprice());
                                        holder.riderNumberOfSeats.setText(riderReqTickets.getticketnumberofseats());

                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                CharSequence options[] = new CharSequence[]
                                                        {
                                                                "Accept",
                                                                "Cancel"
                                                        };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("requestUserName" + "  Chat Request");

                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if (i == 0) {
                                                            ConfirmedCarpoolFriends.child(currentUserID).child(list_user_id).child("Friends")
                                                                    .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        ConfirmedCarpoolFriends.child(list_user_id).child(currentUserID).child("Friends")
                                                                                .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    DriverRequestingRiderRef.child(currentUserID).child(list_user_id)
                                                                                            .removeValue()
                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        DriverRequestingRiderRef.child(list_user_id).child(currentUserID)
                                                                                                                .removeValue()
                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                        if (task.isSuccessful()) {
                                                                                                                            Toast.makeText(getContext(), "New Contact Saved", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                    }
                                                                                                                });
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        if (i == 1) {
                                                            DriverRequestingRiderRef.child(currentUserID).child(list_user_id)
                                                                    .removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                DriverRequestingRiderRef.child(list_user_id).child(currentUserID)
                                                                                        .removeValue()
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Toast.makeText(getContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                                builder.show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            // change to delete!! todo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            if (type.equals("sent")) {
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
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_my_request_ride_ticket_entity, viewGroup, false);
                RequestsViewHolder holder = new RequestsViewHolder(view);
                return holder;
            }
        };

        receivedFriendRequest.setAdapter(adapter1);
        adapter1.startListening();
    }


    public static class RequestsViewHolder extends RecyclerView.ViewHolder {
        TextView riderTo, riderFrom, riderDate, riderTime, riderNumberOfSeats, riderPrice;

        public RequestsViewHolder(@NonNull View itemView) {
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
