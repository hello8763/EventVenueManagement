package DAO;
import Model.BookingService;
import java.util.List;

public interface BookingServicesDAO {
    boolean addBookingService(BookingService bs);
    List<BookingService> getServicesByBookingId(int bookingId);
    boolean deleteBookingService(int bookingId, int serviceId);
}