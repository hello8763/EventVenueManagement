package Model;

public class Booking {

    private int booking_id;
    private int user_id;
    private int venue_id;
    private int type_id;
    private String event_date;
    private String start_time;
    private String end_time;
    private double total_price;
    private String status;

    public Booking() {}

    public Booking(int booking_id, int user_id, int venue_id, int type_id,
                   String event_date, String start_time, String end_time,
                   double total_price, String status) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.venue_id = venue_id;
        this.type_id = type_id;
        this.event_date = event_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.total_price = total_price;
        this.status = status;
    }

    public int getBookingId() { return booking_id; }
    public void setBookingId(int booking_id) { this.booking_id = booking_id; }

    public int getUserId() { return user_id; }
    public void setUserId(int user_id) { this.user_id = user_id; }

    public int getVenueId() { return venue_id; }
    public void setVenueId(int venue_id) { this.venue_id = venue_id; }

    public int getTypeId() { return type_id; }
    public void setTypeId(int type_id) { this.type_id = type_id; }

    public String getEventDate() { return event_date; }
    public void setEventDate(String event_date) { this.event_date = event_date; }

    public String getStartTime() { return start_time; }
    public void setStartTime(String start_time) { this.start_time = start_time; }

    public String getEndTime() { return end_time; }
    public void setEndTime(String end_time) { this.end_time = end_time; }

    public double getTotalPrice() { return total_price; }
    public void setTotalPrice(double total_price) { this.total_price = total_price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
