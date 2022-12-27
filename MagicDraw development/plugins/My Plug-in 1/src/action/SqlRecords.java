package action;

import java.util.ArrayList;

public class SqlRecords {
    private static final ArrayList<SqlRecord> theRecords = new ArrayList<SqlRecord>();

    public SqlRecords() {

    }

    public static void add(SqlRecord sr) {

        theRecords.add(sr);
    }

    public static ArrayList<SqlRecord> getRecords() {
        return theRecords;
    }

    public static void printAll() {
        String outmsg;

        OutMsg.disp(" ************ Start of SqlRecord *************** ");
        for (SqlRecord sr : SqlRecords.getRecords()) {
            outmsg =    "Part= " + sr.getpName() +
                    "   PartID= " + sr.getpID() +
                    "   Max1= " + sr.getMax1() +
                    "   Min1= " + sr.getMin1();
            OutMsg.disp(outmsg);
        }
        OutMsg.disp(" ************* End of SqlRecord *************** ");

    }
}
