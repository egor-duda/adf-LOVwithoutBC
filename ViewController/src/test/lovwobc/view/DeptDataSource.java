package test.lovwobc.view;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.List;

import test.lovwobc.model.AttributeDef;
import test.lovwobc.model.LabelledAttributeDef;
import test.lovwobc.model.SourceDataRow;

public class DeptDataSource {
    public static class Department implements SourceDataRow {
        private BigDecimal deptId;
        private String deptName;

        public Department(int deptId, String deptName) {
            super();
            this.deptId = new BigDecimal (deptId);
            this.deptName = deptName;
        }

        @Override
        public Object getId() {
            return deptId;
        }

        @Override
        public String getValue() {
            return deptName;
        }
        
        public String getDeptName () {
            return getValue();
        }
    }
    
    private static ArrayList<Department> departments = new ArrayList<Department> ();
    static {
        departments.add (new Department (1, "Accounting"));
        departments.add (new Department (2, "Administration"));
        departments.add (new Department (3, "IT"));
        departments.add (new Department (4, "IT Tech"));
        departments.add (new Department (5, "Operations"));
        departments.add (new Department (6, "Sales"));
        departments.add (new Department (7, "Support"));
    }
    private static final List<LabelledAttributeDef> departmentAttributes = new ArrayList <LabelledAttributeDef> ();
    static {
        departmentAttributes.add (new LabelledAttributeDef ("value", "Название отдела"));
    }
    
    public static List<? extends SourceDataRow> getValues () {
        return departments;
    }
    
    public static List <? extends AttributeDef> getAttributes () {
        return departmentAttributes;
    }
}
