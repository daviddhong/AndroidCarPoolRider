package android.carpoolrider.RequestRides.RequestNewRide;

public class RequestRiderRequestTicket {

    public String From, To;


    public RequestRiderRequestTicket() {
    }
    public RequestRiderRequestTicket(String tickfrom, String ticketto) {
        this.From = tickfrom;
        this.To = ticketto;
    }


    public String getticketfrom() {
        return From;
    }
    public void setticketfrom(String ticketfrom) {
        this.From = ticketfrom;
    }



    public String getticketto() {
        return To;
    }
    public void setticketto(String ticketto) {
        this.To = ticketto;
    }


}
