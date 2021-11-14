package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.DataLayer;
import companydata.Employee;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeEntity {
    private DataLayer dl = null;
    public Gson gson = null;
    private Validator validator = null;

    public EmployeeEntity() {
        try {
            this.dl = new DataLayer("kxmzgr");
            gson = new Gson();
            validator = new Validator();
        } catch (Exception ex) {
            System.out.println("Problem with query: " + ex.getMessage());
        } finally {
            dl.close();
        }
    }

    /**
     * Gets all employees by companyName
     * @param companyName
     * @return String Json List
     */
    public String getEmployees(String companyName) {
        List<Employee> employees = dl.getAllEmployee(companyName);
        validator.isEmpty(employees);
        if(!validator.isSuccess()) return validator.getErrorMessages();
        
        return gson.toJson(employees);
    }
    
    public Employee getEmployeeObject(int employee_id) {
        validator.employeeExists(employee_id);
        if(!validator.isSuccess()) {
            return null;
        } else {
            Employee employee = dl.getEmployee(employee_id);
            return employee;
        }
    }
    
    /**
     * Get a specific employee by emp_id
     * @param employeeId
     * @return String Json object
     */
    public String getEmployee(int employeeId) {
        validator.employeeExists(employeeId);
        if(!validator.isSuccess()) {
            return validator.getErrorMessages();
        } else {
            Employee employee = dl.getEmployee(employeeId);
            return gson.toJson(employee);
        }
    }

    /**
     * Create/insert an employee
     * @param companyName
     * @param emp_name
     * @param emp_no
     * @param hire_date
     * @param job
     * @param salary
     * @param dept_id
     * @param mng_id
     * @return String Json object of the created employee
     * @throws java.text.ParseException
     */
    public String insertEmployee(String companyName, String emp_name, String emp_no, String hire_date, String job, double salary, int dept_id, int mng_id) throws ParseException {
        String response = null;
            Date hiringDate = new SimpleDateFormat("yyyy-MM-dd").parse(hire_date);
            java.sql.Date hiringDateSQL = new java.sql.Date(hiringDate.getTime());
            validator.departmentExists(companyName, dept_id);
            validator.managerExists(mng_id);
            validator.uniqueEmpNo(emp_no);
            validator.validateHireDate(hiringDateSQL);
            Employee employee = new Employee(emp_name, emp_no, hiringDateSQL, job, salary, dept_id, mng_id);
            if(dl.insertEmployee(employee) == null){
                response = validator.getErrorMessages();
            } else {
            List<Employee> allEmployee = dl.getAllEmployee(companyName);
                for (Employee emp : allEmployee) {
                    if(emp.getEmpNo().equals(emp_no)){
                        Employee employeeObject = this.getEmployeeObject(emp.getId());
                        response = "{\n" + " \"success\":" + gson.toJson(employeeObject) + "\n}";
                    }
                }
            }
        return response;
    }

    /**
     * Update/put an existing employee
     * @param inJson
     * @return String json object
     */
    public String updateEmployee(String inJson) {
      String response;
      Employee employee = gson.fromJson(inJson, Employee.class);
      validator.departmentExists("tk9480", employee.getDeptId());
      validator.employeeExists(employee.getId());
      validator.uniqueEmpNo(employee.getEmpNo());
      validator.managerExists(employee.getMngId());

        if(dl.updateEmployee(employee) == null) {
            response = validator.getErrorMessages();
        } else {
            response = "{\n" + " \"success\":" + gson.toJson(employee) + "\n}";
        }
      return response;
    }
    
    /**
     * Delete an employee by emp_id
     * @param id
     * @return int of the employee to be deleted
     */
    public String deleteEmployee(int id) {
        validator.employeeExists(id);
        
        if(!validator.isSuccess()) return validator.getErrorMessages();

        return "{\n" + " \"success\": \"Employee " + id + " deleted.\"}";
    }
    
}