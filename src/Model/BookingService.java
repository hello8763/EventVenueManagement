package Model;

public class BookingService {
    private int bookingId;
    private int serviceId;
    private int quantity;
    private double pricePerUnit;  // stored at time of booking
    private double lineTotal;

    public BookingService() {}

    public BookingService(int bookingId, int serviceId, int quantity, double pricePerUnit, double lineTotal) {
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.lineTotal = lineTotal;
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public double getLineTotal() { return lineTotal; }
    public void setLineTotal(double lineTotal) { this.lineTotal = lineTotal; }
}   