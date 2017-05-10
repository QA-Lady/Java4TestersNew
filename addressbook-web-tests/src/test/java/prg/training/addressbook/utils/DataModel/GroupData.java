package prg.training.addressbook.utils.DataModel;

public class GroupData /*implements Comparable<GroupData> */ {

    private String groupName;
    private int groupID;
    private String header;
    private String footer;

    public GroupData(String groupName, int groupID, String header, String footer) {
        this.groupName = groupName;
        this.groupID = groupID;
        this.header = header;
        this.footer = footer;
    }

    public GroupData(String groupName, String header, String footer) {
        this.groupName = groupName;
        //to ensure that new group will always be sorted last by id
        this.groupID = Integer.MAX_VALUE;

        this.header = header;
        this.footer = footer;
    }

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

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "groupName='" + groupName + '\'' +
                ", groupID='" + groupID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupData groupData = (GroupData) o;

        return groupName != null ? groupName.equals(groupData.groupName) : groupData.groupName == null;
    }

    @Override
    public int hashCode() {
        return groupName != null ? groupName.hashCode() : 0;
    }


//    @Override
//    public int compareTo(GroupData other) {
//        if (groupName != null) return groupName.compareTo(other.getGroupName());
//        else return -1;
//    }
}
