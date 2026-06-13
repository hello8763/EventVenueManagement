package Controller;
import DAO.VenueDAO;
import DAO.VenueDAOImp;
import Model.Venue;
import View.CustomerVenueBrowsePage;
import View.BookingCreatePage;
import javax.swing.*;
import java.util.List;

public class CustomerVenueController {
    private CustomerVenueBrowsePage view;
    private VenueDAO dao;

    public CustomerVenueController(CustomerVenueBrowsePage view) {
        this.view = view;
        dao = new VenueDAOImp();
        loadVenues();
        view.btnSelect.addActionListener(e -> selectVenue());
    }

    private void loadVenues() {
        view.model.setRowCount(0);
        for (Venue v : dao.getAllVenues()) {
            view.model.addRow(new Object[]{v.getVenueId(), v.getName(), v.getLocation(), v.getCapacity(), v.getHourlyRate(), v.getStatus()});
        }
    }

    private void selectVenue() {
        int row = view.table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(view, "Select a venue"); return; }
        int venueId = (int) view.model.getValueAt(row, 0);
        view.dispose();
        BookingCreatePage createPage = new BookingCreatePage(venueId);
        new BookingCreateController(createPage);
        createPage.setVisible(true);
    }
}