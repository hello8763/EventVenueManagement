package Controller;

import DAO.VenueDAO;
import DAO.VenueDAOImp;
import Model.Venue;
import View.VenuePage;

import javax.swing.*;
import java.util.List;

public class VenueController {

    VenuePage view;
    VenueDAO dao;

    public VenueController(VenuePage view) {
        this.view = view;
        dao = new VenueDAOImp();

        loadVenues();
        view.btnAdd.addActionListener(e -> addVenue());
        view.btnUpdate.addActionListener(e -> updateVenue());
        view.btnDelete.addActionListener(e -> deleteVenue());
        view.btnSearch.addActionListener(e -> searchVenue());
    }

    public void addVenue() {
        Venue venue = new Venue();
        venue.setName(view.txtName.getText());
        venue.setLocation(view.txtLocation.getText());
        venue.setCapacity(Integer.parseInt(view.txtCapacity.getText()));
        venue.setHourlyRate(Double.parseDouble(view.txtHourlyRate.getText()));
        venue.setStatus(view.txtStatus.getText());
        if (dao.addVenue(venue)) {
            JOptionPane.showMessageDialog(view, "Venue Added", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadVenues();
        }
    }

    public void updateVenue() {
        Venue venue = new Venue();
        venue.setVenueId(Integer.parseInt(view.txtVenueId.getText()));
        venue.setName(view.txtName.getText());
        venue.setLocation(view.txtLocation.getText());
        venue.setCapacity(Integer.parseInt(view.txtCapacity.getText()));
        venue.setHourlyRate(Double.parseDouble(view.txtHourlyRate.getText()));
        venue.setStatus(view.txtStatus.getText());
        if (dao.updateVenue(venue)) {
            JOptionPane.showMessageDialog(view, "Venue Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadVenues();
        }
    }

    public void deleteVenue() {
        int id = Integer.parseInt(view.txtVenueId.getText());
        if (dao.deleteVenue(id)) {
            JOptionPane.showMessageDialog(view, "Venue Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadVenues();
        }
    }

    public void searchVenue() {
        int id = Integer.parseInt(view.txtVenueId.getText());
        Venue venue = dao.searchVenueById(id);
        if (venue != null) {
            view.txtName.setText(venue.getName());
            view.txtLocation.setText(venue.getLocation());
            view.txtCapacity.setText(String.valueOf(venue.getCapacity()));
            view.txtHourlyRate.setText(String.valueOf(venue.getHourlyRate()));
            view.txtStatus.setText(venue.getStatus());
        } else {
            JOptionPane.showMessageDialog(view, "Venue Not Found");
        }
    }

    public void loadVenues() {
        view.model.setRowCount(0);
        List<Venue> venues = dao.getAllVenues();
        for (Venue venue : venues) {
            Object[] row = {
                venue.getVenueId(),
                venue.getName(),
                venue.getLocation(),
                venue.getCapacity(),
                venue.getHourlyRate(),
                venue.getStatus()
            };
            view.model.addRow(row);
        }
    }
}
