package prg.training.addressbook.utils.dataModel;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "addressbook")
public class ContactsData {
    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int contactID = Integer.MAX_VALUE;
    @Expose
    @Column(name = "firstname")
    private String firstname;
    @Expose
    @Column(name = "lastname")
    private String lastname;
    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address;
    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String homeNumber;
    @Expose
    @Column(name = "mobile")
    @Type(type = "text")
    private String mobileNumber;
    @Expose
    @Column(name = "work")
    @Type(type = "text")
    private String workNumber;
    @Transient //skip this and do not try to extract from DB
    private String allPhones;
    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String email;
    //    @Expose
    @ManyToMany(fetch = FetchType.EAGER)//it is lazy by default we changed to eager to load all info from DB
    @JoinTable(name = "address_in_groups", joinColumns = @JoinColumn(name = "id")//this is the column that refers to column for contact
            , inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupData> groups = new HashSet<GroupData>();
    @Expose
//    @Transient //skip this and do not try to extract from DB
    @Column(name = "bday", columnDefinition = "TINYINT")
    private Integer day;
    @Expose
    @Column(name = "bmonth", length = 65535)
    private String month;
    @Expose
    @Column(name = "byear", length = 65535)
    private String year;

    @Column(name = "photo")
    @Type(type = "text")
    private String photo;



    public File getPhoto() {
        return new File(photo);
    }

    public ContactsData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
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

    public Groups getGroups() {
        return new Groups(groups);
    }

    public String getDay() {
        return String.valueOf(day);
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

    public void withGroups(Set<GroupData> groups) {
        this.groups = groups;
    }

    public ContactsData inGroup(GroupData group) {
        //addin group that was passed as a argument to groups where this contact belongs
        groups.add(group);
        //assign this group that pas passed as argument to a field group to be use in getGroup()
        return this;

    }
    public ContactsData withDay(String day) {
        this.day = Integer.parseInt(day);
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
                ", address='" + address + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", workNumber='" + workNumber + '\'' +
                ", allPhones='" + allPhones + '\'' +
                ", email='" + email + '\'' +
                ", day=" + day +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactsData that = (ContactsData) o;

        if (contactID != that.contactID) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (homeNumber != null ? !homeNumber.equals(that.homeNumber) : that.homeNumber != null) return false;
        if (mobileNumber != null ? !mobileNumber.equals(that.mobileNumber) : that.mobileNumber != null) return false;
        if (workNumber != null ? !workNumber.equals(that.workNumber) : that.workNumber != null) return false;
        if (allPhones != null ? !allPhones.equals(that.allPhones) : that.allPhones != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        return year != null ? year.equals(that.year) : that.year == null;
    }

    @Override
    public int hashCode() {
        int result = contactID;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (homeNumber != null ? homeNumber.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (workNumber != null ? workNumber.hashCode() : 0);
        result = 31 * result + (allPhones != null ? allPhones.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }

    public ContactsData withGroup(GroupData group) {
        //addin group that was passed as a argument to groups where this contact belongs
        groups.add(group);
        return this;

    }
}
