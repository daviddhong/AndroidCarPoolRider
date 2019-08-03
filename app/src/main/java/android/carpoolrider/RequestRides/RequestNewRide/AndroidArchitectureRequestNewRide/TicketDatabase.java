package android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TicketEntity.class, version = 1)
public abstract class TicketDatabase extends RoomDatabase {

    private static TicketDatabase instance;

    public abstract TicketDao ticketDao();

    public static synchronized TicketDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TicketDatabase.class, "ticket_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
