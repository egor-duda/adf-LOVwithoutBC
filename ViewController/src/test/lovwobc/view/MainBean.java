package test.lovwobc.view;

import java.util.Collections;
import java.util.List;

import test.lovwobc.model.BeanLOV;
import test.lovwobc.model.SourceDataRow;

public class MainBean {
    public MainBean() {
        super();
    }
    
    private BeanLOV empNameLOV = new BeanLOV () {
        @Override
        public List<String> getAttributes() {
            return EmpDataSource.getAttributes();
        }
        @Override
        public List<? extends SourceDataRow> getValues() {
            return EmpDataSource.getValues();
        }        
    };

    public BeanLOV getEmpNameLOV() {
        return empNameLOV;
    }
    
    private BeanLOV deptNameLOV = new BeanLOV() {
        @Override
        public List<String> getAttributes() {
            return DeptDataSource.getAttributes();
        }
        @Override
        public List<? extends SourceDataRow> getValues() {
            return DeptDataSource.getValues();
        }

        public void setId(Object id) {
            if (getId() != null && !getId().equals(id)) {
                departmentEmpNameLOV.reset();
            }
            super.setId(id);
        }
    };
    
    public BeanLOV getDeptNameLOV () {
        return deptNameLOV;
    }
    
    private BeanLOV departmentEmpNameLOV = new BeanLOV () {
        @Override public List<String> getAttributes() {
            return EmpDataSource.getAttributes();            
        }
        @Override public List<? extends SourceDataRow> getValues() {
            return EmpDataSource.getValues(deptNameLOV.getId());
        }
    };
        
    public BeanLOV getDepartmentEmpNameLOV () {
        return departmentEmpNameLOV;
    }
}
