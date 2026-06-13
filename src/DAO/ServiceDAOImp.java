package DAO;

import Databases.mysqlDBConnection;
import Model.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImp implements ServiceDAO {

    @Override
    public boolean addService(Service service) {
        String sql = "INSERT INTO services(service_name, description, price_per_unit, status) VALUES(?,?,?,?)";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getPricePerUnit());
            ps.setBoolean(4, service.isStatus());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateService(Service service) {
        String sql = "UPDATE services SET service_name=?, description=?, price_per_unit=?, status=? WHERE service_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getPricePerUnit());
            ps.setBoolean(4, service.isStatus());
            ps.setInt(5, service.getServiceId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteService(int id) {
        String sql = "DELETE FROM services WHERE service_id=?";
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
    public Service searchServiceById(int id) {
        String sql = "SELECT * FROM services WHERE service_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("service_id"));
                service.setServiceName(rs.getString("service_name"));
                service.setDescription(rs.getString("description"));
                service.setPricePerUnit(rs.getDouble("price_per_unit"));
                service.setStatus(rs.getBoolean("status"));
                return service;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("service_id"));
                service.setServiceName(rs.getString("service_name"));
                service.setDescription(rs.getString("description"));
                service.setPricePerUnit(rs.getDouble("price_per_unit"));
                service.setStatus(rs.getBoolean("status"));
                list.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
