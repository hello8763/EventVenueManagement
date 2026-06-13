package Model;

public class EventType {

    private int type_id;
    private String name;
    private String description;
    private double base_deposit;

    public EventType() {}

    public EventType(int type_id, String name, String description, double base_deposit) {
        this.type_id = type_id;
        this.name = name;
        this.description = description;
        this.base_deposit = base_deposit;
    }
@Override
public String toString() {
    return name;
}
    public int getTypeId() { return type_id; }
    public void setTypeId(int type_id) { this.type_id = type_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBaseDeposit() { return base_deposit; }
    public void setBaseDeposit(double base_deposit) { this.base_deposit = base_deposit; }
}
