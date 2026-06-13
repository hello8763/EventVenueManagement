package Model;

public class Venue {

    private int venue_id;
    private String name;
    private String location;
    private int capacity;
    private double hourly_rate;
    private String status;

    public Venue() {}

    public Venue(int venue_id, String name, String location, int capacity, double hourly_rate, String status) {
        this.venue_id = venue_id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.hourly_rate = hourly_rate;
        this.status = status;
    }

    public int getVenueId() { return venue_id; }
    public void setVenueId(int venue_id) { this.venue_id = venue_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getHourlyRate() { return hourly_rate; }
    public void setHourlyRate(double hourly_rate) { this.hourly_rate = hourly_rate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
