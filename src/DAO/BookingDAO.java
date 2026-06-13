package DAO;
import Model.Booking;
import java.util.List;

public interface BookingDAO {
    int addBooking(Booking booking);          // returns generated ID
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int id);
    Booking searchBookingById(int id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUserId(int userId);  // NEW for customer
}