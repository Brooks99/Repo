package action;


/*
This is a class that represents a part record from the database.
 */
public class SqlRecord  {

    private String pName;
    private String pID;
    private String max1;
    private String min1;

    public SqlRecord(String p1, String p2, String max1, String min1) {

        super();
        this.pName = p1;
        this.pID = p2;
        this.max1 = max1;
        this.min1 = min1;

    }
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getMax1() {
        return max1;
    }

    public void setMax1(String max1) {
        this.max1 = max1;
    }

    public String getMin1() {
        return min1;
    }

    public void setMin1(String min1) {
        this.min1 = min1;
    }

}
