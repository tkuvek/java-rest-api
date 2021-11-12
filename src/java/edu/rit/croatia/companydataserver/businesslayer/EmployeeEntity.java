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
        return gson.toJson(departments);    }
    
}