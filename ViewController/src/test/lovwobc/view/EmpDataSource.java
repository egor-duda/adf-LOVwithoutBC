package test.lovwobc.view;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.List;

import test.lovwobc.model.AttributeDef;
import test.lovwobc.model.LabelledAttributeDef;
import test.lovwobc.model.SourceDataRow;

public class EmpDataSource {
    public static class Employee implements SourceDataRow {
        private BigDecimal empId;
        private BigDecimal deptId;
        private String empName;

        public Employee(int empId, String empName, int deptId) {
            super();
            this.empId = new BigDecimal (empId);
            this.deptId = new BigDecimal (deptId);
            this.empName = empName;
        }

        @Override
        public Object getId() {
            return empId;
        }

        @Override
        public String getValue() {
            return empName;
        }
        
        public String getEmpName () {
            return empName;
        }

        public BigDecimal getDeptId() {
            return deptId;
        }
    }
    
    private static ArrayList<Employee> employees = new ArrayList<Employee> ();
    static {
        employees.add (new Employee (1, "Abdul", 2));
        employees.add (new Employee (2, "Adam", 1));
        employees.add (new Employee (3, "Ben", 2));
        employees.add (new Employee (4, "Carol", 1));
        employees.add (new Employee (5, "Cathy", 1));
        employees.add (new Employee (6, "Denis", 3));
    }
    private static final List<LabelledAttributeDef> employeeAttributes = new ArrayList <LabelledAttributeDef> ();
    private static final List<LabelledAttributeDef> employeeAttributesNameOnly = new ArrayList <LabelledAttributeDef> ();
    static {
        employeeAttributes.add (new LabelledAttributeDef ("value", "Имя сотрудника"));
        employeeAttributes.add (new LabelledAttributeDef ("deptId", "Номер Отдела"));
        
        employeeAttributesNameOnly.add (new LabelledAttributeDef ("value", "Имя сотрудника"));
    }
    
    public static List<? extends SourceDataRow> getValues () {
        return employees;
    }
    
    static List<? extends SourceDataRow> getValues(Object deptId) {
        ArrayList<Employee> retVal = new ArrayList<Employee> ();
        for (Employee employee: employees) {
            if (employee.getDeptId().equals(deptId)) {
                retVal.add(employee);
            }
        }
        return retVal;
    }    
    
    public static List <? extends AttributeDef> getAttributes () {
        return employeeAttributes;
    }
    
    public static List <? extends AttributeDef> getAttributesNameOnly () {
        return employeeAttributesNameOnly;
    }    
}
