package test.lovwobc.view;

import java.math.BigDecimal;

import java.util.List;

import test.lovwobc.model.BeanLOV;
import test.lovwobc.model.SourceDataRow;

public class MainBean {
    public MainBean() {
        super();
    }
    
    private String empName;
    private BigDecimal empId;

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpName() {
        return empName;
    }

    public class EmpNameLOV extends BeanLOV {
        @Override
        public void setSelectedValue(SourceDataRow rowData) {
            empName = rowData.getValue();
            empId = (BigDecimal)rowData.getId();
        }
        @Override
        public List<String> getAttributes() {
            return EmpDataSource.getAttributes();
        }
        @Override
        public List<? extends SourceDataRow> getValues() {
            return EmpDataSource.getValues();
        }
    }
    
    private BeanLOV empNameLOV = new EmpNameLOV ();

    public BeanLOV getEmpNameLOV() {
        return empNameLOV;
    }
    
    private String deptName;
    private BigDecimal deptId;
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public class DeptNameLOV extends BeanLOV {
        @Override
        public void setSelectedValue(SourceDataRow rowData) {
            deptName = rowData.getValue();
            deptId = (BigDecimal)rowData.getId();
        }
        @Override
        public List<String> getAttributes() {
            return DeptDataSource.getAttributes();
        }
        @Override
        public List<? extends SourceDataRow> getValues() {
            return DeptDataSource.getValues();
        }
    }
    
    private DeptNameLOV deptNameLOV = new DeptNameLOV();
    
    public DeptNameLOV getDeptNameLOV () {
        return deptNameLOV;
    }

}
