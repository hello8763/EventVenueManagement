package DAO;

import Databases.mysqlDBConnection;
import Model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImp implements BookingDAO {


@Override
public int addBooking(Booking booking) {
    String sql = "INSERT INTO bookings(user_id, venue_id, type_id, event_date, start_time, end_time, total_price, status) VALUES(?,?,?,?,?,?,?,?)";
    try (Connection con = mysqlDBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, booking.getUserId());
        ps.setInt(2, booking.getVenueId());
        ps.setInt(3, booking.getTypeId());
        ps.setString(4, booking.getEventDate());
        ps.setString(5, booking.getStartTime());
        ps.setString(6, booking.getEndTime());
        ps.setDouble(7, booking.getTotalPrice());
        ps.setString(8, booking.getStatus());
        int affected = ps.executeUpdate();
        if (affected == 0) return -1;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
    } catch (SQLException e) { e.printStackTrace(); }
    return -1;
}

@Override
public List<Booking> getBookingsByUserId(int userId) {
    List<Booking> list = new ArrayList<>();
    String sql = "SELECT * FROM bookings WHERE user_id=?";
    try (Connection con = mysqlDBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Booking b = new Booking();
            b.setBookingId(rs.getInt("booking_id"));
            b.setUserId(rs.getInt("user_id"));
            b.setVenueId(rs.getInt("venue_id"));
            b.setTypeId(rs.getInt("type_id"));
            b.setEventDate(rs.getString("event_date"));
            b.setStartTime(rs.getString("start_time"));
            b.setEndTime(rs.getString("end_time"));
            b.setTotalPrice(rs.getDouble("total_price"));
            b.setStatus(rs.getString("status"));
            list.add(b);
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}

    @Override
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET user_id=?, venue_id=?, type_id=?, event_date=?, start_time=?, end_time=?, total_price=?, status=? WHERE booking_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getVenueId());
            ps.setInt(3, booking.getTypeId());
            ps.setString(4, booking.getEventDate());
            ps.setString(5, booking.getStartTime());
            ps.setString(6, booking.getEndTime());
            ps.setDouble(7, booking.getTotalPrice());
            ps.setString(8, booking.getStatus());
            ps.setInt(9, booking.getBookingId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE booking_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Booking searchBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE booking_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setVenueId(rs.getInt("venue_id"));
                booking.setTypeId(rs.getInt("type_id"));
                booking.setEventDate(rs.getString("event_date"));
                booking.setStartTime(rs.getString("start_time"));
                booking.setEndTime(rs.getString("end_time"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                booking.setStatus(rs.getString("status"));
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setVenueId(rs.getInt("venue_id"));
                booking.setTypeId(rs.getInt("type_id"));
                booking.setEventDate(rs.getString("event_date"));
                booking.setStartTime(rs.getString("start_time"));
                booking.setEndTime(rs.getString("end_time"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                booking.setStatus(rs.getString("status"));
                list.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // ... (Keep all your existing methods in BookingDAOImp) ...

    @Override
    public boolean isVenueAvailable(int venueId, String date, String startTime, String endTime) {
        // We only care if overlapping bookings are Confirmed or Paid. (Cancelled ones don't block the venue).
        String sql = "SELECT COUNT(*) FROM bookings WHERE venue_id = ? AND event_date = ? AND status IN ('Confirmed', 'Paid') AND start_time < ? AND end_time > ?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, venueId);
            ps.setString(2, date);
            ps.setString(3, endTime);   // New End Time
            ps.setString(4, startTime); // New Start Time
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // If count is 0, there are no overlaps. The venue is available!
                return rs.getInt(1) == 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Fail safe: if database errors, assume unavailable
    }
} // End of class

