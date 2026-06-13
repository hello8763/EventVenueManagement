package DAO;

import Model.Service;
import java.util.List;

public interface ServiceDAO {
    boolean addService(Service service);
    boolean updateService(Service service);
    boolean deleteService(int id);
    Service searchServiceById(int id);
    List<Service> getAllServices();
}
