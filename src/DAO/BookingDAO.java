package DAO;
import Model.Booking;
import java.util.List;

public interface BookingDAO {
    int addBooking(Booking booking);
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int id);
    Booking searchBookingById(int id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUserId(int userId);
    
    // NEW: Checks if the venue is free during the requested times
    boolean isVenueAvailable(int venueId, String date, String startTime, String endTime);
}