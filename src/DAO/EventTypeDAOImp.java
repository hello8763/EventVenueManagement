package DAO;
import Databases.mysqlDBConnection;
import Model.EventType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventTypeDAOImp implements EventTypeDAO {
    @Override
    public boolean addEventType(EventType et) {
        String sql = "INSERT INTO event_types (name, description, base_deposit) VALUES (?,?,?)";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, et.getName());
            ps.setString(2, et.getDescription());
            ps.setDouble(3, et.getBaseDeposit());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean updateEventType(EventType et) {
        String sql = "UPDATE event_types SET name=?, description=?, base_deposit=? WHERE type_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, et.getName());
            ps.setString(2, et.getDescription());
            ps.setDouble(3, et.getBaseDeposit());
            ps.setInt(4, et.getTypeId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean deleteEventType(int id) {
        String sql = "DELETE FROM event_types WHERE type_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public EventType searchEventTypeById(int id) {
        String sql = "SELECT * FROM event_types WHERE type_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                EventType et = new EventType();
                et.setTypeId(rs.getInt("type_id"));
                et.setName(rs.getString("name"));
                et.setDescription(rs.getString("description"));
                et.setBaseDeposit(rs.getDouble("base_deposit"));
                return et;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<EventType> getAllEventTypes() {
        List<EventType> list = new ArrayList<>();
        String sql = "SELECT * FROM event_types";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EventType et = new EventType();
                et.setTypeId(rs.getInt("type_id"));
                et.setName(rs.getString("name"));
                et.setDescription(rs.getString("description"));
                et.setBaseDeposit(rs.getDouble("base_deposit"));
                list.add(et);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}