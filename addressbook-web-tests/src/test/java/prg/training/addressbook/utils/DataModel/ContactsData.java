package prg.training.addressbook.utils.DataModel;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.File;

public class ContactsData {
    @XStreamOmitField
    private int contactID = Integer.MAX_VALUE;
    @Expose
    private String firstname;
    @Expose
    private String lastname;
    @Expose
    private String address;
    @Expose
    private String homeNumber;
    @Expose
    private String mobileNumber;
    @Expose
    private String workNumber;
    private String allPhones;
    @Expose
    private String email;
    @Expose
    private String group;
    @Expose
    private String day;
    @Expose
    private String month;
    @Expose
    private String year;

    public File getPhoto() {
        return photo;
    }

    public ContactsData withPhoto(File photo) {
        this.photo = photo;
        return this;
    }

    private File photo;


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

    public String getAllPhones() {
        return allPhones;
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

    public ContactsData withAllphones(String allPhones) {
        this.allPhones = allPhones;
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
