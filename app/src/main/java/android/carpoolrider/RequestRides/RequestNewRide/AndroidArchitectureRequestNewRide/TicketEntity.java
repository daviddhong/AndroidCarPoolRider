package android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ticket_table")
public class TicketEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String origin;

    private String destination;

    private String passengerNumbers;

    private String date;

    private String time;

    public TicketEntity(String origin, String destination, String passengerNumbers, String date, String time) {
        this.origin = origin;
        this.destination = destination;
        this.passengerNumbers = passengerNumbers;
        this.date = date;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getPassengerNumbers() {
        return passengerNumbers;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
