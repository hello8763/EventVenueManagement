package DAO;

import Databases.mysqlDBConnection;
import Model.Venue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDAOImp implements VenueDAO {

    @Override
    public boolean addVenue(Venue venue) {
        String sql = "INSERT INTO venues(name, location, capacity, hourly_rate, status) VALUES(?,?,?,?,?)";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, venue.getName());
            ps.setString(2, venue.getLocation());
            ps.setInt(3, venue.getCapacity());
            ps.setDouble(4, venue.getHourlyRate());
            ps.setString(5, venue.getStatus());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateVenue(Venue venue) {
        String sql = "UPDATE venues SET name=?, location=?, capacity=?, hourly_rate=?, status=? WHERE venue_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, venue.getName());
            ps.setString(2, venue.getLocation());
            ps.setInt(3, venue.getCapacity());
            ps.setDouble(4, venue.getHourlyRate());
            ps.setString(5, venue.getStatus());
            ps.setInt(6, venue.getVenueId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteVenue(int id) {
        String sql = "DELETE FROM venues WHERE venue_id=?";
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
    public Venue searchVenueById(int id) {
        String sql = "SELECT * FROM venues WHERE venue_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Venue venue = new Venue();
                venue.setVenueId(rs.getInt("venue_id"));
                venue.setName(rs.getString("name"));
                venue.setLocation(rs.getString("location"));
                venue.setCapacity(rs.getInt("capacity"));
                venue.setHourlyRate(rs.getDouble("hourly_rate"));
                venue.setStatus(rs.getString("status"));
                return venue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Venue> getAllVenues() {
        List<Venue> list = new ArrayList<>();
        String sql = "SELECT * FROM venues";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venue venue = new Venue();
                venue.setVenueId(rs.getInt("venue_id"));
                venue.setName(rs.getString("name"));
                venue.setLocation(rs.getString("location"));
                venue.setCapacity(rs.getInt("capacity"));
                venue.setHourlyRate(rs.getDouble("hourly_rate"));
                venue.setStatus(rs.getString("status"));
                list.add(venue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
