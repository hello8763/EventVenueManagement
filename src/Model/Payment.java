package Model;

public class Payment {

    private int payment_id;
    private int booking_id;
    private double amount;
    private String payment_method;
    private String transaction_ref;
    private String payment_status;

    public Payment() {}

    public Payment(int payment_id, int booking_id, double amount, String payment_method,
                   String transaction_ref, String payment_status) {
        this.payment_id = payment_id;
        this.booking_id = booking_id;
        this.amount = amount;
        this.payment_method = payment_method;
        this.transaction_ref = transaction_ref;
        this.payment_status = payment_status;
    }

    public int getPaymentId() { return payment_id; }
    public void setPaymentId(int payment_id) { this.payment_id = payment_id; }

    public int getBookingId() { return booking_id; }
    public void setBookingId(int booking_id) { this.booking_id = booking_id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return payment_method; }
    public void setPaymentMethod(String payment_method) { this.payment_method = payment_method; }

    public String getTransactionRef() { return transaction_ref; }
    public void setTransactionRef(String transaction_ref) { this.transaction_ref = transaction_ref; }

    public String getPaymentStatus() { return payment_status; }
    public void setPaymentStatus(String payment_status) { this.payment_status = payment_status; }
}
