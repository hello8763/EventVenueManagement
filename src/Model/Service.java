package Model;

public class Service {

    private int service_id;
    private String service_name;
    private String description;
    private double price_per_unit;
    private boolean status;

    public Service() {}

    public Service(int service_id, String service_name, String description,
                   double price_per_unit, boolean status) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.description = description;
        this.price_per_unit = price_per_unit;
        this.status = status;
    }


    public int getServiceId() { return service_id; }
    public void setServiceId(int service_id) { this.service_id = service_id; }

    public String getServiceName() { return service_name; }
    public void setServiceName(String service_name) { this.service_name = service_name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPricePerUnit() { return price_per_unit; }
    public void setPricePerUnit(double price_per_unit) { this.price_per_unit = price_per_unit; }
@Override
public String toString() {
    return service_name;
}
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
