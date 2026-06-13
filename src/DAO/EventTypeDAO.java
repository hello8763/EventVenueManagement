package DAO;
import Model.EventType;
import java.util.List;

public interface EventTypeDAO {
    boolean addEventType(EventType et);
    boolean updateEventType(EventType et);
    boolean deleteEventType(int id);
    EventType searchEventTypeById(int id);
    List<EventType> getAllEventTypes();
}