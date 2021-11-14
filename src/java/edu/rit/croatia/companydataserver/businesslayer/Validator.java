package edu.rit.croatia.companydataserver.businesslayer;

import companydata.DataLayer;
import companydata.Department;
import companydata.Employee;
import companydata.Timecard;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Calendar;
import java.util.Date;

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
    
    public void validateHireDate(Date hire_date) {
        Calendar hireDate = Calendar.getInstance();
        hireDate.setTime(hire_date);
        
        int hiringDay = hireDate.get(Calendar.DAY_OF_WEEK);
        if(hiringDay < 2 || hiringDay > 6) addError("Hire date must be between Monday and Friday.");
        
        LocalDateTime now = LocalDateTime.now();
        if(hire_date.after(java.util.Date.from(now.toInstant(ZoneOffset.UTC))))
            addError("Hire date must be in the past.");
    }
    
    
    public void validateTimecardConditions(Timestamp startDate, Timestamp endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minusDays(7);
        
        if(now.until(then, DAYS) > 7) 
            addError("Start time must be within the past 7 days.");
        
        start.setTimeInMillis(startDate.getTime());
        end.setTimeInMillis(endDate.getTime());
        
        if(endDate.before(startDate))
            addError("The end date needs to be after start date.");
       
        int startDateDay = start.get(Calendar.DAY_OF_WEEK);
        int endDateDay = end.get(Calendar.DAY_OF_WEEK);
        if(startDateDay != endDateDay) 
            addError("Start and end date must be on the same day of the week.");
        
        if((startDateDay < 2 || startDateDay > 6) && (endDateDay < 2 || endDateDay > 6))
            addError("Start and end date must be during the workweek (Monday through Friday)");
        
        int startDateHour = start.get(Calendar.HOUR_OF_DAY);
        int endDateHour = end.get(Calendar.HOUR_OF_DAY);
        
        if ((endDateHour - startDateHour) < 1)
            addError("Start and end date must be at least 1 hour apart.");
    }
}
