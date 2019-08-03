package android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TicketDao {

    @Insert
    void insert(TicketEntity ticketEntity);

    @Update
    void update(TicketEntity ticketEntity);

    @Delete
    void delete(TicketEntity ticketEntity);

    @Query("SELECT * FROM ticket_table")
    LiveData<List<TicketEntity>> getAllTicketEntities();
}
