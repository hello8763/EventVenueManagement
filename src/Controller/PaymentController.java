package Controller;

import DAO.PaymentDAO;
import DAO.PaymentDAOImp;
import Model.Payment;
import View.PaymentPage;

import javax.swing.*;
import java.util.List;

public class PaymentController {

    PaymentPage view;
    PaymentDAO dao;

    public PaymentController(PaymentPage view) {
        this.view = view;
        dao = new PaymentDAOImp();

        loadPayments();
        view.btnAdd.addActionListener(e -> addPayment());
        view.btnUpdate.addActionListener(e -> updatePayment());
        view.btnDelete.addActionListener(e -> deletePayment());
        view.btnSearch.addActionListener(e -> searchPayment());
    }

    public void addPayment() {
        Payment payment = new Payment();
        payment.setBookingId(Integer.parseInt(view.txtBookingId.getText()));
        payment.setAmount(Double.parseDouble(view.txtAmount.getText()));
        payment.setPaymentMethod(view.txtPaymentMethod.getText());
        payment.setTransactionRef(view.txtTransactionRef.getText());
        payment.setPaymentStatus(view.txtPaymentStatus.getText());
        if (dao.addPayment(payment)) {
            JOptionPane.showMessageDialog(view, "Payment Added", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPayments();
        }
    }

    public void updatePayment() {
        Payment payment = new Payment();
        payment.setPaymentId(Integer.parseInt(view.txtPaymentId.getText()));
        payment.setBookingId(Integer.parseInt(view.txtBookingId.getText()));
        payment.setAmount(Double.parseDouble(view.txtAmount.getText()));
        payment.setPaymentMethod(view.txtPaymentMethod.getText());
        payment.setTransactionRef(view.txtTransactionRef.getText());
        payment.setPaymentStatus(view.txtPaymentStatus.getText());
        if (dao.updatePayment(payment)) {
            JOptionPane.showMessageDialog(view, "Payment Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPayments();
        }
    }

    public void deletePayment() {
        int id = Integer.parseInt(view.txtPaymentId.getText());
        if (dao.deletePayment(id)) {
            JOptionPane.showMessageDialog(view, "Payment Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPayments();
        }
    }

    public void searchPayment() {
        int id = Integer.parseInt(view.txtPaymentId.getText());
        Payment payment = dao.searchPaymentById(id);
        if (payment != null) {
            view.txtBookingId.setText(String.valueOf(payment.getBookingId()));
            view.txtAmount.setText(String.valueOf(payment.getAmount()));
            view.txtPaymentMethod.setText(payment.getPaymentMethod());
            view.txtTransactionRef.setText(payment.getTransactionRef());
            view.txtPaymentStatus.setText(payment.getPaymentStatus());
        } else {
            JOptionPane.showMessageDialog(view, "Payment Not Found");
        }
    }

    public void loadPayments() {
        view.model.setRowCount(0);
        List<Payment> payments = dao.getAllPayments();
        for (Payment payment : payments) {
            Object[] row = {
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getTransactionRef(),
                payment.getPaymentStatus()
            };
            view.model.addRow(row);
        }
    }
}
