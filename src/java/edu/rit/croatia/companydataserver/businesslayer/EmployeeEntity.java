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

    public String getEmployees(String companyName) {
        List<Employee> departments = dl.getAllEmployee(companyName);
        if (departments.isEmpty()) {
            return "{\"error:\": \"No employees found for company " + companyName + ".\"}";
        }
        return gson.toJson(departments);    
    }
    
    public String getEmployee(int employeeId) {
        Employee employee = dl.getEmployee(employeeId);
        if (employee == null) {
            return "{\"error:\": \"No employee found with id " + employeeId + ".\"}";
        }
        return gson.toJson(employee);
    }

    public String insertEmployee(Employee employee) {
        if(dl.insertEmployee(employee) == null){
            return "{\"error:\": \"Failed to insert employee.\"}";
        } else {
            return gson.toJson(employee.toString());
        }
    }

    public String updateEmployee(String inJson) {
      Employee employee = gson.fromJson(inJson, Employee.class);
      dl.updateEmployee(employee);
      return gson.toJson(employee);
    }
    
    public int deleteEmployee(int id) {
        return dl.deleteEmployee(id);
    }
    
}