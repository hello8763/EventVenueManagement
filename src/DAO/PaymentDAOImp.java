package DAO;

import Databases.mysqlDBConnection;
import Model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImp implements PaymentDAO {

    @Override
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payments(booking_id, amount, payment_method, transaction_ref, payment_status) VALUES(?,?,?,?,?)";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getTransactionRef());
            ps.setString(5, payment.getPaymentStatus());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET booking_id=?, amount=?, payment_method=?, transaction_ref=?, payment_status=? WHERE payment_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getTransactionRef());
            ps.setString(5, payment.getPaymentStatus());
            ps.setInt(6, payment.getPaymentId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePayment(int id) {
        String sql = "DELETE FROM payments WHERE payment_id=?";
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
    public Payment searchPaymentById(int id) {
        String sql = "SELECT * FROM payments WHERE payment_id=?";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setTransactionRef(rs.getString("transaction_ref"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setTransactionRef(rs.getString("transaction_ref"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                list.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
