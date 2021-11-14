package edu.rit.croatia.companydataserver.businesslayer;

import companydata.DataLayer;
import companydata.Department;
import companydata.Employee;
import companydata.Timecard;
import java.util.List;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validator {
    
    public String errorMessage = "{\"errors:\":[";
    private DataLayer dl = null;
    private boolean isSuccess = true;
    
    public Validator() {
        try {
            this.dl = new DataLayer("kxmzgr");
        } catch (Exception ex) {
            System.out.println("Problem with query: " + ex.getMessage());
        } finally {
            dl.close();
        }
    }
    
    private void addError(String error) {
        this.errorMessage += "\"" + error + "\",";
        this.isSuccess = false;
    }

    public String getErrorMessages() {
        errorMessage = errorMessage.substring(0, errorMessage.length()-1);
        errorMessage += "]}";
        return errorMessage;    
    }
    
    public boolean isSuccess(){
        return this.isSuccess;
    }
    
    public void isEmpty(List<?> list) {
        if(list.isEmpty()) {
            addError("No records exist for current query.");
        }
    }
    
    public void departmentExists(String company, int dept_id) {
        Department department = dl.getDepartment(company, dept_id);
        if(department == null) {
            addError("Department with id " + dept_id + " in company " + company + " does not exist.");
        }
    }
    
    public void employeeExists(int id) {  
        Employee employee = dl.getEmployee(id);
        if (employee == null) {
            addError("Employee with id " + id + " does not exist.");
        }
    }
    
    public void timecardExists(int id) {
        Timecard tc = dl.getTimecard(id);
        if (tc == null) {
            addError("Timecard with id " + id + " does not exist.");
        }
    }
    
    public void managerExists(int mng_id) {
       Employee employee = dl.getEmployee(mng_id);
       if(employee == null && mng_id != 0) {
           addError("Employee with id " + mng_id + " does not exist and cannot be asserted as manager.");
       }
    }
    
    public void uniqueEmpNo(String emp_no) {
        List<Employee> allEmployee = dl.getAllEmployee("tk9480");
        for(Employee emp : allEmployee) {
            if(emp.getEmpNo().equals(emp_no)) {
                addError("Employee with employee number: " + emp_no + " already exists.");
            }
        }
    }
    
    public void uniqueDeptNo(String dept_no) {
        List<Department> allDepartment = dl.getAllDepartment("tk9480");
        for(Department dept : allDepartment) {
            if(dept.getDeptNo().equals(dept_no)){
                addError("Department with department number: " + dept_no + " already exists.");
            }
        }
    }
    
//    public Timestamp checkTimestamp(String timestamp) {
//        Timestamp ts = null;
//        try {
//            ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime());
//        } catch (ParseException ex) {
//            addError("Incorrect format, please match yyyy-MM-dd HH:mm:ss");
//            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ts;
//    }
}
