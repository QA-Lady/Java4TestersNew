package prg.training.addressbook.utils.dataModel;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity//makes class GroupData to be maped to DB
@Table(name = "group_list")
public class GroupData /*implements Comparable<GroupData> */ {

    @Expose
    @Column(name = "group_name")
    private String groupName;
    @XStreamOmitField
    @Id//primary key
    @Column(name = "group_id")
    private int groupID = Integer.MAX_VALUE;
    @Expose
    @Column(name = "group_header")
    @Type(type = "text")
    private String header;
    @Expose
    @Column(name = "group_footer")
    @Type(type = "text")
    private String footer;

    @ManyToMany(mappedBy = "groups") //will take groups in paired Class Contact Data and will take everything from it
    /*@JoinTable(name = "address_in_groups", joinColumns = @JoinColumn(name = "id")//the first is the column that refers to column for group
            , inverseJoinColumns = @JoinColumn(name = "group_id"))//this is the column that refers to column for contact*/
    private Set<ContactsData> contacts = new HashSet<ContactsData>();


    public String getGroupName() {
        return groupName;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public int getGroupID() {
        return groupID;
    }

    public Set<ContactsData> getContacts() {
        return new Contacts(contacts);
    }

    public GroupData withGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public GroupData withGroupID(int groupID) {
        this.groupID = groupID;
        return this;
    }

    public GroupData withHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupData withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "groupName='" + groupName + '\'' +
                ", groupID=" + groupID +
                ", header='" + header + '\'' +
                ", footer='" + footer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupData groupData = (GroupData) o;

        if (groupID != groupData.groupID) return false;
        if (groupName != null ? !groupName.equals(groupData.groupName) : groupData.groupName != null) return false;
        if (header != null ? !header.equals(groupData.header) : groupData.header != null) return false;
        return footer != null ? footer.equals(groupData.footer) : groupData.footer == null;
    }

    @Override
    public int hashCode() {
        int result = groupName != null ? groupName.hashCode() : 0;
        result = 31 * result + groupID;
        result = 31 * result + (header != null ? header.hashCode() : 0);
        result = 31 * result + (footer != null ? footer.hashCode() : 0);
        return result;
    }


}
