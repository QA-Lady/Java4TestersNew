package prg.training.addressbook.utils.DataModel;

public class ContactsData {
    private int contactID = Integer.MAX_VALUE;
    private String firstname;
    private String lastname;
    private String address;
    private String homeNumber;
    private String mobileNumber;
    private String workNumber;
    private String email;
    private String group;
    private String day;
    private String month;
    private String year;


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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
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

    public int getContactID() {
        return contactID;
    }

    public ContactsData withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ContactsData withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ContactsData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactsData withHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
        return this;
    }

    public ContactsData withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public ContactsData withWorkNumber(String workNumber) {
        this.workNumber = workNumber;
        return this;
    }

    public ContactsData withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactsData withGroup(String group) {
        this.group = group;
        return this;
    }

    public ContactsData withDay(String day) {
        this.day = day;
        return this;
    }

    public ContactsData withMonth(String month) {
        this.month = month;
        return this;
    }

    public ContactsData withYear(String year) {
        this.year = year;
        return this;
    }

    public ContactsData withContactID(int contactID) {
        this.contactID = contactID;
        return this;
    }

    @Override
    public String toString() {
        return "ContactsData{" +
                "contactID=" + contactID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactsData that = (ContactsData) o;

        if (contactID != that.contactID) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = contactID;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
}
