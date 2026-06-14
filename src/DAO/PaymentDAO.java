package DAO;

import Model.Payment;
import java.util.List;

public interface PaymentDAO {
    boolean addPayment(Payment payment);
    boolean updatePayment(Payment payment);
    boolean deletePayment(int id);
    Payment searchPaymentById(int id);
    List<Payment> getAllPayments();
    // NEW: Fetch only payments belonging to a specific customer
    List<Payment> getPaymentsByUserId(int userId);
}