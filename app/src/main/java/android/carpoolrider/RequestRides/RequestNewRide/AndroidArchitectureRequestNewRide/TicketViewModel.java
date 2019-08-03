package android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TicketViewModel extends AndroidViewModel {

    private TicketRepository ticketRepository;
    private LiveData<List<TicketEntity>> allTicketEntities;

    public TicketViewModel(@NonNull Application application) {
        super(application);
        ticketRepository = new TicketRepository(application);
        allTicketEntities = ticketRepository.getAllTicketEntities();
    }

    public void insert(TicketEntity ticketEntity) {
        ticketRepository.insert(ticketEntity);
    }

    public void update(TicketEntity ticketEntity) {
        ticketRepository.update(ticketEntity);
    }

    public void delete(TicketEntity ticketEntity) {
        ticketRepository.delete(ticketEntity);
    }

    public LiveData<List<TicketEntity>> getAllTicketEntities() {
        return allTicketEntities;
    }
}
