package test.lovwobc.view;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import test.lovwobc.model.SourceDataRow;

public class EmpDataSource {
    public static class Employee implements SourceDataRow {
        private BigDecimal empId;
        private String empName;

        public Employee(int empId, String empName) {
            super();
            this.empId = new BigDecimal (empId);
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
    }
    
    private static ArrayList<Employee> employees = new ArrayList<Employee> ();
    static {
        employees.add (new Employee (1, "Abdul"));
        employees.add (new Employee (2, "Adam"));
        employees.add (new Employee (3, "Ben"));
        employees.add (new Employee (4, "Carol"));
        employees.add (new Employee (5, "Cathy"));
        employees.add (new Employee (6, "Denis"));
    }
    private static final List<String> employeeRowAttributes = Arrays.asList( new String [] { "empName" });
    
    public static List<? extends SourceDataRow> getValues () {
        return employees;
    }
    
    public static List <String> getAttributes () {
        return employeeRowAttributes;
    }
}
