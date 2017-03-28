package prg.training.addressbook;

public class ContactsData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String homeNumber;
    private final String phoneNumber;
    private final String email;
    private final String groupID;
    private final String day;
    private final String month;
    private final String year;

    public ContactsData(String firstname, String lastname, String address, String homeNumber, String phoneNumber, String email, String groupID, String day, String month, String year) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.homeNumber = homeNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.groupID = groupID;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
