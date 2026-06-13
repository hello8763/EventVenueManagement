package DAO;

import Model.Venue;
import java.util.List;

public interface VenueDAO {
    boolean addVenue(Venue venue);
    boolean updateVenue(Venue venue);
    boolean deleteVenue(int id);
    Venue searchVenueById(int id);
    List<Venue> getAllVenues();
}
