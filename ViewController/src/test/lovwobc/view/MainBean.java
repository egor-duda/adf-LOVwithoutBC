package test.lovwobc.view;

import java.math.BigDecimal;

import java.util.List;

import test.lovwobc.model.BeanLOV;
import test.lovwobc.model.AttributeDef;
import test.lovwobc.model.SourceDataRow;

public class MainBean {
    public MainBean() {
        super();
        deptNameLOV.findById (new BigDecimal(1));
        empNameLOV.findById (new BigDecimal(4));
    }
    
    private BeanLOV empNameLOV = new BeanLOV () {
        @Override
        protected List<? extends AttributeDef> getAttributes() {
            return EmpDataSource.getAttributesNameOnly();
        }
        @Override
        protected List<? extends SourceDataRow> getValues() {
            return EmpDataSource.getValues();
        }        
    };

    public BeanLOV getEmpNameLOV() {
        return empNameLOV;
    }
    
    private BeanLOV deptNameLOV = new BeanLOV() {
        @Override
        protected List<? extends AttributeDef> getAttributes() {
            return DeptDataSource.getAttributes();
        }
        @Override 
        protected List<? extends SourceDataRow> getValues() {
            return DeptDataSource.getValues();
        }
        
        @Override 
        protected int getMRUSize () { return 3; }
        
        @Override
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
        @Override
        protected List<? extends AttributeDef> getAttributes() {
            return EmpDataSource.getAttributes();            
        }
        @Override
        protected List<? extends SourceDataRow> getValues() {
            return EmpDataSource.getValues(deptNameLOV.getId());
        }
    };
        
    public BeanLOV getDepartmentEmpNameLOV () {
        return departmentEmpNameLOV;
    }
}
