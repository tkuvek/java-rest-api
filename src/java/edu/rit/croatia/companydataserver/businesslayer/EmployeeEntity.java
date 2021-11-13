package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.DataLayer;
import companydata.Employee;
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
        if (employees.isEmpty()) {
            return "{\"error:\": \"No employees found for company " + companyName + ".\"}";
        }
        else {
            return gson.toJson(employees);
        }
    }
    
    /**
     * Get a specific employee by emp_id
     * @param employeeId
     * @return String Json object
     */
    public String getEmployee(int employeeId) {
        Employee employee = dl.getEmployee(employeeId);
        if (employee == null) {
            return "{\"error:\": \"No employee found with id: " + employeeId + ".\"}";
        } else {
           return gson.toJson(employee);
        }
    }

    /**
     * Create/insert an employee
     * @param employee
     * @return String Json object of the created employee
     */
    public String insertEmployee(Employee employee) {
        if(dl.insertEmployee(employee) == null){
            return "{\"error:\": \"Failed to insert employee.\"}";
        } else {
            return "{\n" + " \"success\":" + gson.toJson(employee) + "\n}";
        }
    }

    /**
     * Update/put an existing employee
     * @param inJson
     * @return String json object
     */
    public String updateEmployee(String inJson) {
      Employee employee = gson.fromJson(inJson, Employee.class);
      if(dl.updateEmployee(employee) == null) {
            return "{\"error:\": \"Failed to update employee.\"}";
      } else {
            return "{\n" + " \"success\":" + gson.toJson(employee) + "\n}";
      }
    }
    
    /**
     * Delete an employee by emp_id
     * @param id
     * @return int of the employee to be deleted
     */
    public int deleteEmployee(int id) {
      if(dl.deleteEmployee(id) == 0) {
         return 0;
      } else {
          return id;
      }
    }
    
}