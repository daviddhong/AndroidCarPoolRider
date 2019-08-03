package android.carpoolrider.RequestRides.RequestNewRide.AndroidArchitectureRequestNewRide;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TicketRepository {
    private TicketDao ticketDao;
    private LiveData<List<TicketEntity>> allTicketEntities;

    public TicketRepository(Application application) {
        TicketDatabase ticketDatabase = TicketDatabase.getInstance(application);
        ticketDao = ticketDatabase.ticketDao();
        allTicketEntities = ticketDao.getAllTicketEntities();
    }

    public void insert(TicketEntity ticketEntity) {
        new InsertTicketEntityAsyncTask(ticketDao).execute(ticketEntity);
    }

    public void update(TicketEntity ticketEntity) {
        new UpdateTicketEntityAsyncTask(ticketDao).execute(ticketEntity);
    }

    public void delete(TicketEntity ticketEntity) {
        new DeleteTicketEntityAsyncTask(ticketDao).execute(ticketEntity);
    }

    public LiveData<List<TicketEntity>> getAllTicketEntities() {
        return allTicketEntities;
    }

    private static class InsertTicketEntityAsyncTask extends AsyncTask<TicketEntity, Void, Void> {

        private TicketDao ticketDao;

        private InsertTicketEntityAsyncTask(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(TicketEntity... ticketEntities) {
            ticketDao.insert(ticketEntities[0]);
            return null;
        }
    }

    private static class UpdateTicketEntityAsyncTask extends AsyncTask<TicketEntity, Void, Void> {

        private TicketDao ticketDao;

        private UpdateTicketEntityAsyncTask(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(TicketEntity... ticketEntities) {
            ticketDao.update(ticketEntities[0]);
            return null;
        }
    }

    private static class DeleteTicketEntityAsyncTask extends AsyncTask<TicketEntity, Void, Void> {

        private TicketDao ticketDao;

        private DeleteTicketEntityAsyncTask(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(TicketEntity... ticketEntities) {
            ticketDao.delete(ticketEntities[0]);
            return null;
        }
    }
}
