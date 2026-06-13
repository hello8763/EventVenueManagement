package Controller;
import DAO.EventTypeDAO;
import DAO.EventTypeDAOImp;
import Model.EventType;
import View.EventTypePage;
import javax.swing.*;
import java.util.List;

public class EventTypeController {
    private EventTypePage view;
    private EventTypeDAO dao;

    public EventTypeController(EventTypePage view) {
        this.view = view;
        dao = new EventTypeDAOImp();
        loadTable();
        view.btnAdd.addActionListener(e -> add());
        view.btnUpdate.addActionListener(e -> update());
        view.btnDelete.addActionListener(e -> delete());
        view.btnSearch.addActionListener(e -> search());
    }

    private void loadTable() {
        view.model.setRowCount(0);
        for (EventType et : dao.getAllEventTypes()) {
            view.model.addRow(new Object[]{et.getTypeId(), et.getName(), et.getDescription(), et.getBaseDeposit()});
        }
    }

    private void add() {
        try {
            EventType et = new EventType();
            et.setName(view.txtName.getText());
            et.setDescription(view.txtDesc.getText());
            et.setBaseDeposit(Double.parseDouble(view.txtDeposit.getText()));
            if (dao.addEventType(et)) { loadTable(); JOptionPane.showMessageDialog(view, "Added"); }
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Error: "+ex.getMessage()); }
    }

    private void update() {
        try {
            EventType et = new EventType();
            et.setTypeId(Integer.parseInt(view.txtId.getText()));
            et.setName(view.txtName.getText());
            et.setDescription(view.txtDesc.getText());
            et.setBaseDeposit(Double.parseDouble(view.txtDeposit.getText()));
            if (dao.updateEventType(et)) { loadTable(); JOptionPane.showMessageDialog(view, "Updated"); }
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Error: "+ex.getMessage()); }
    }

    private void delete() {
        try {
            int id = Integer.parseInt(view.txtId.getText());
            if (dao.deleteEventType(id)) { loadTable(); JOptionPane.showMessageDialog(view, "Deleted"); }
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Error: "+ex.getMessage()); }
    }

    private void search() {
        try {
            int id = Integer.parseInt(view.txtId.getText());
            EventType et = dao.searchEventTypeById(id);
            if (et != null) {
                view.txtName.setText(et.getName());
                view.txtDesc.setText(et.getDescription());
                view.txtDeposit.setText(String.valueOf(et.getBaseDeposit()));
            } else JOptionPane.showMessageDialog(view, "Not found");
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Invalid ID"); }
    }
}