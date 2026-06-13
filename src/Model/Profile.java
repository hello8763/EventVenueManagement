package Model;

public class Profile {
    private int profileId;
    private int userId;
    private String fullName;
    private String phoneNumber;
    private String role;
    private String address;

    public Profile() {}

    public Profile(int profileId, int userId, String fullName, String phoneNumber, String role, String address) {
        this.profileId = profileId;
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.address = address;
    }

    // Getters and Setters
    public int getProfileId() { return profileId; }
    public void setProfileId(int profileId) { this.profileId = profileId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}