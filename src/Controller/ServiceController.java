package Controller;

import DAO.ServiceDAO;
import DAO.ServiceDAOImp;
import Model.Service;
import View.ServicePage;

import javax.swing.*;
import java.util.List;

public class ServiceController {

    ServicePage view;
    ServiceDAO dao;

    public ServiceController(ServicePage view) {
        this.view = view;
        dao = new ServiceDAOImp();

        loadServices();
        view.btnAdd.addActionListener(e -> addService());
        view.btnUpdate.addActionListener(e -> updateService());
        view.btnDelete.addActionListener(e -> deleteService());
        view.btnSearch.addActionListener(e -> searchService());
    }

    public void addService() {
        Service service = new Service();
        service.setServiceName(view.txtServiceName.getText());
        service.setDescription(view.txtDescription.getText());
        service.setPricePerUnit(Double.parseDouble(view.txtPricePerUnit.getText()));
        service.setStatus(view.chkStatus.isSelected());
        if (dao.addService(service)) {
            JOptionPane.showMessageDialog(view, "Service Added", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadServices();
        }
    }

    public void updateService() {
        Service service = new Service();
        service.setServiceId(Integer.parseInt(view.txtServiceId.getText()));
        service.setServiceName(view.txtServiceName.getText());
        service.setDescription(view.txtDescription.getText());
        service.setPricePerUnit(Double.parseDouble(view.txtPricePerUnit.getText()));
        service.setStatus(view.chkStatus.isSelected());
        if (dao.updateService(service)) {
            JOptionPane.showMessageDialog(view, "Service Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadServices();
        }
    }

    public void deleteService() {
        int id = Integer.parseInt(view.txtServiceId.getText());
        if (dao.deleteService(id)) {
            JOptionPane.showMessageDialog(view, "Service Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadServices();
        }
    }

    public void searchService() {
        int id = Integer.parseInt(view.txtServiceId.getText());
        Service service = dao.searchServiceById(id);
        if (service != null) {
            view.txtServiceName.setText(service.getServiceName());
            view.txtDescription.setText(service.getDescription());
            view.txtPricePerUnit.setText(String.valueOf(service.getPricePerUnit()));
            view.chkStatus.setSelected(service.isStatus());
        } else {
            JOptionPane.showMessageDialog(view, "Service Not Found");
        }
    }

    public void loadServices() {
        view.model.setRowCount(0);
        List<Service> services = dao.getAllServices();
        for (Service service : services) {
            Object[] row = {
                service.getServiceId(),
                service.getServiceName(),
                service.getDescription(),
                service.getPricePerUnit(),
                service.isStatus()
            };
            view.model.addRow(row);
        }
    }
}
