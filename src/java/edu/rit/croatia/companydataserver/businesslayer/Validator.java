package edu.rit.croatia.companydataserver.businesslayer;

import companydata.DataLayer;
import companydata.Department;
import companydata.Employee;
import companydata.Timecard;
import java.util.List;

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
}
