package DAO;
import Databases.mysqlDBConnection;
import Model.BookingService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingServicesDAOImp implements BookingServicesDAO {
    @Override
    public boolean addBookingService(BookingService bs) {
        String sql = "INSERT INTO booking_services (booking_id, service_id, quantity, price_per_unit, line_total) VALUES (?,?,?,?,?)";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bs.getBookingId());
            ps.setInt(2, bs.getServiceId());
            ps.setInt(3, bs.getQuantity());
            ps.setDouble(4, bs.getPricePerUnit());
            ps.setDouble(5, bs.getLineTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public List<BookingService> getServicesByBookingId(int bookingId) {
        List<BookingService> list = new ArrayList<>();
        String sql = "SELECT * FROM booking_services WHERE booking_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookingService bs = new BookingService();
                bs.setBookingId(rs.getInt("booking_id"));
                bs.setServiceId(rs.getInt("service_id"));
                bs.setQuantity(rs.getInt("quantity"));
                bs.setPricePerUnit(rs.getDouble("price_per_unit"));
                bs.setLineTotal(rs.getDouble("line_total"));
                list.add(bs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean deleteBookingService(int bookingId, int serviceId) {
        String sql = "DELETE FROM booking_services WHERE booking_id=? AND service_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, serviceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}