package android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide;

import android.carpoolrider.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder> {

    private List<TicketEntity> ticketEntities = new ArrayList<>();

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_request_ride_ticket_entity, parent, false);
        return new TicketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        TicketEntity currentTicket = ticketEntities.get(position);
        holder.textViewOrigin.setText(currentTicket.getOrigin());
        holder.textViewDestination.setText(currentTicket.getDestination());
        holder.textViewDate.setText(currentTicket.getDate());
        holder.textViewTime.setText(currentTicket.getTime());
        holder.textViewPassengerNumber.setText(currentTicket.getPassengerNumbers());
    }

    @Override
    public int getItemCount() {
        return ticketEntities.size();
    }

    public void setTicketEntities(List<TicketEntity> ticketEntities)  {
        this.ticketEntities = ticketEntities;
        notifyDataSetChanged();
    }

    class TicketHolder extends RecyclerView.ViewHolder {

        private TextView textViewOrigin;

        private TextView textViewDestination;

        private TextView textViewDate;

        private TextView textViewTime;

        private TextView textViewPassengerNumber;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrigin = itemView.findViewById(R.id.text_origin);
            textViewDestination = itemView.findViewById(R.id.text_destination);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewTime = itemView.findViewById(R.id.text_time);
            textViewPassengerNumber = itemView.findViewById(R.id.text_passenger_number);
        }
    }
 }
