package prg.training.addressbook.tests.dbTests;

import org.testng.annotations.Test;
import prg.training.addressbook.utils.dataModel.GroupData;
import prg.training.addressbook.utils.dataModel.Groups;

import java.sql.*;

/**
 * Created by QA Lady on 5/18/2017.
 */
public class DbConnectionTest {

    @Test
    public void testDbConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?serverTimezone=UTC&user=root&password=");

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");

            Groups groups = new Groups();

            while (rs.next()) {
                groups.add(
                        new GroupData().withGroupID(rs.getInt("group_id")).withGroupName(rs.getString("group_name")).withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer"))
                );
            }

            rs.close();
            st.close();
            conn.close();

            System.out.println(groups);

            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}

