package Controller;
import DAO.*;
import Model.*;
import View.BookingCreatePage;
import Util.UserSession;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingCreateController {
    private BookingCreatePage view;
    private VenueDAO venueDAO;
    private EventTypeDAO eventTypeDAO;
    private ServiceDAO serviceDAO;
    private BookingDAO bookingDAO;
    private BookingServicesDAO bookingServicesDAO;

    public BookingCreateController(BookingCreatePage view) {
        this.view = view;
        venueDAO = new VenueDAOImp();
        eventTypeDAO = new EventTypeDAOImp();
        serviceDAO = new ServiceDAOImp();
        bookingDAO = new BookingDAOImp();
        bookingServicesDAO = new BookingServicesDAOImp();
        loadEventTypes();
        loadServices();
        view.btnCalculate.addActionListener(e -> calculateTotal());
        view.btnConfirm.addActionListener(e -> confirmBooking());
    }

    private void loadEventTypes() {
        for (EventType et : eventTypeDAO.getAllEventTypes())
            view.cmbEventType.addItem(et);
    }

    private void loadServices() {
        List<Service> services = serviceDAO.getAllServices();
        view.listServices.setListData(services.toArray(new Service[0]));
    }


private void calculateTotal() {
    try {
        // 1. Get venue
        Venue venue = venueDAO.searchVenueById(view.getVenueId());
        if (venue == null) {
            JOptionPane.showMessageDialog(view, "Venue not found (ID: " + view.getVenueId() + ")");
            return;
        }

        // 2. Get selected event type
        EventType et = (EventType) view.cmbEventType.getSelectedItem();
        if (et == null) {
            JOptionPane.showMessageDialog(view, "Please select an event type");
            return;
        }

        // 3. Extract hour & minute from start and end time spinners
        Date startDateObj = (Date) view.spinnerStart.getValue();
        Date endDateObj = (Date) view.spinnerEnd.getValue();
        if (startDateObj == null || endDateObj == null) {
            JOptionPane.showMessageDialog(view, "Please set start and end times");
            return;
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(startDateObj);
        int startHour = cal.get(java.util.Calendar.HOUR_OF_DAY);
        int startMinute = cal.get(java.util.Calendar.MINUTE);
        double startInHours = startHour + startMinute / 60.0;

        cal.setTime(endDateObj);
        int endHour = cal.get(java.util.Calendar.HOUR_OF_DAY);
        int endMinute = cal.get(java.util.Calendar.MINUTE);
        double endInHours = endHour + endMinute / 60.0;

        // 4. Calculate duration (allow overnight: if end <= start, add 24 hours)
        double duration = endInHours - startInHours;
        if (duration <= 0) {
            duration += 24;
        }
        if (duration <= 0) {
            JOptionPane.showMessageDialog(view, "End time must be after start time (or next day)");
            return;
        }

        // 5. Compute costs
        double venueCost = venue.getHourlyRate() * duration;
        double servicesCost = 0.0;
        for (Service s : view.listServices.getSelectedValuesList()) {
            servicesCost += s.getPricePerUnit();
        }
        double total = venueCost + servicesCost;  // deposit optional: + et.getBaseDeposit()

        view.txtTotalPrice.setText(String.format("%.2f", total));
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(view, "Calculation error: " + ex.getMessage());
    }
}

private void confirmBooking() {
    try {
        // If total field is empty, calculate first
        if (view.txtTotalPrice.getText().trim().isEmpty()) {
            calculateTotal();
            if (view.txtTotalPrice.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please calculate total before confirming");
                return;
            }
        }

        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");

        Booking booking = new Booking();
        booking.setUserId(UserSession.getInstance().getUserId());
        booking.setVenueId(view.getVenueId());

        EventType et = (EventType) view.cmbEventType.getSelectedItem();
        booking.setTypeId(et.getTypeId());

        booking.setEventDate(dateFmt.format((Date) view.spinnerDate.getValue()));
        booking.setStartTime(timeFmt.format((Date) view.spinnerStart.getValue()));
        booking.setEndTime(timeFmt.format((Date) view.spinnerEnd.getValue()));
        booking.setTotalPrice(Double.parseDouble(view.txtTotalPrice.getText()));
        booking.setStatus("Confirmed");

        int bookingId = bookingDAO.addBooking(booking);
        if (bookingId > 0) {
            for (Service s : view.listServices.getSelectedValuesList()) {
                BookingService bs = new BookingService(bookingId, s.getServiceId(), 1,
                        s.getPricePerUnit(), s.getPricePerUnit());
                bookingServicesDAO.addBookingService(bs);
            }
            JOptionPane.showMessageDialog(view, "Booking created! ID: " + bookingId);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to create booking.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
    }
}
}