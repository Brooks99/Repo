package action;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
The purpose of this class is to form the database query and to execute the query.
- Uses a place to hold records (SqlRecords)

*/

public class PartQuery {

    //SqlRecords theRecords = new SqlRecords();

    int cnt = 1;

    public PartQuery() {
    }

    public void viewResult(Connection con) throws SQLException {

        String query1 = "SELECT " +
                "MaximumValue, " +
                "MinimumValue, " +
                "ParameterID " +
                "FROM " +
                "part.TechnologyParameter " +
                "WHERE (TechnologyID < 20) AND (MaximumValue > 0)";


        String query2 = "SELECT " +
                "P.PartID, " +
                "T.TechnologyCode, " +
                "T.TechnologyName, " +
                "P.PartNumber, " +
                "P.IndustryPartNumber, " +
                "P.ISSDBPN, " +
                "ML.MaturityLevelName, " +
                "P.StatusCode " +
                "FROM [part].[Part] P " +
                "JOIN dropdown.MaturityLevel ML ON ML.MaturityLevelID = P.MaturityLevelID " +
                "JOIN dropdown.Technology T ON T.TechnologyID = P.TechnologyID " +
                "AND P.PartID IN (100014, 100015) ";

        Statement stmt;
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet rs;
        try {
            rs = stmt.executeQuery(query1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //var cnt = 0;

        while (rs.next()) {
            //OutMsg.disp(rs.toString() + " --- "  + rs.getString(cnt));
            String dummyType = "non volatile memory part";
            String parameterID = String.valueOf(rs.getInt("ParameterID"));
            String maximumValue_1 = String.valueOf(rs.getFloat("MaximumValue"));
            String minimumValue_1 = String.valueOf(rs.getFloat("MinimumValue"));
            //Create a record from the database
            SqlRecord sqlRec = new SqlRecord(dummyType + "_" + cnt , parameterID,maximumValue_1, minimumValue_1);
            // Add record to the group of records
            SqlRecords.add(sqlRec);
            ++cnt;
        }
        //SqlRecords.printAll();
    }
}
