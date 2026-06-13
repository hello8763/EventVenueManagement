package View;
import Model.EventType;
import Model.Service;
import javax.swing.*;
import java.awt.*;

public class BookingCreatePage extends JFrame {
    public JComboBox<EventType> cmbEventType;
    public JSpinner spinnerDate, spinnerStart, spinnerEnd;
    public JList<Service> listServices;
    public JTextField txtTotalPrice;
    public JButton btnCalculate, btnConfirm;
    private int venueId;

    public BookingCreatePage(int venueId) {
        super("Create Booking");
        this.venueId = venueId;
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Venue ID:")); 
        add(new JLabel(String.valueOf(venueId)));

        add(new JLabel("Event Type:")); 
        cmbEventType = new JComboBox<>(); 
        add(cmbEventType);

        add(new JLabel("Event Date (YYYY-MM-DD):")); 
        spinnerDate = new JSpinner(new SpinnerDateModel()); 
        add(spinnerDate);

        add(new JLabel("Start Time (HH:MM):")); 
        spinnerStart = new JSpinner(new SpinnerDateModel()); 
        add(spinnerStart);

        add(new JLabel("End Time (HH:MM):")); 
        spinnerEnd = new JSpinner(new SpinnerDateModel()); 
        add(spinnerEnd);

        add(new JLabel("Services (Ctrl+Click for multiple):")); 
        listServices = new JList<>();
        // Enable multi‑selection
        listServices.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listServices.setVisibleRowCount(5);
        add(new JScrollPane(listServices));

        add(new JLabel("Total Price:")); 
        txtTotalPrice = new JTextField(); 
        txtTotalPrice.setEditable(false); 
        add(txtTotalPrice);

        btnCalculate = new JButton("Calculate"); 
        btnConfirm = new JButton("Confirm");
        add(btnCalculate); 
        add(btnConfirm);

        // Date/time formats
        ((JSpinner.DateEditor) spinnerDate.getEditor()).getFormat().applyPattern("yyyy-MM-dd");
        ((JSpinner.DateEditor) spinnerStart.getEditor()).getFormat().applyPattern("HH:mm");
        ((JSpinner.DateEditor) spinnerEnd.getEditor()).getFormat().applyPattern("HH:mm");
    }

    public int getVenueId() { 
        return venueId; 
    }
}