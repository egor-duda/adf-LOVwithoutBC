package test.lovwobc.view;

public class EmpDataRow {
    private String ename;
    private String rowId;

    EmpDataRow(String ename, String rowID) {
        this.ename = ename;
        this.rowId = rowID;
    }

    public String getEname() {
        return ename;
    }

    public String getRowId() {
        return rowId;
    }
}
