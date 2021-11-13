package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.*;
import java.util.List;

public class DepartmentEntity {

    private DataLayer dl = null;
    public Gson gson = null;
    private Validator validator = null;

    public DepartmentEntity() {
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
     * Gets all departments
     * @param companyName
     * @return String Json List
     */
    public String getDepartments(String companyName) {
        List<Department> departments = dl.getAllDepartment(companyName);
        if (departments.isEmpty()) {
            return "{\"error:\": \"No department found for company " + companyName + ".\"}";
        }
        return gson.toJson(departments);
    }

    /*
        
    */

    /**
     * Gets a specific department
     * @param companyName
     * @param deptId
     * @return Json Department Object
     */

    public String getDepartment(String companyName, String deptId) {
        Department department = dl.getDepartment(companyName, Integer.parseInt(deptId));
        if (department == null) {
            return "{\"error:\": \"No department found for company " + companyName + ".\"}";
        } else {
            return gson.toJson(department);
        }
    }
    
    /**
     * Create/Insert a new department
     * @param dept
     * @return Department object
     */

    public Department insertDepartment(Department dept) {
        if(dl.insertDepartment(dept) == null){
            return null;
        } else {
            return dept;
        }
    }

    /**
     * Put/update of a specific Department
     * @param dept
     * @return Json string of the updated object
     */
    public String updateDepartment(String dept){
      Department department = gson.fromJson(dept, Department.class);
        if (dl.updateDepartment(department) == null) {
            return "{\"error:\": \"Unable to update department.\"}";
        } else {
            return "{\n" + " \"success\":" + gson.toJson(department) + "\n}";
        }
   }

    /**
     * Deletes a department by dept_id
     * @param comp
     * @param dept_id
     * @return int of the deleted department
     */
    public int deleteDepartment(String comp, int dept_id){
      
      if(dl.deleteDepartment(comp, dept_id) == 0) {
         return 0;
      } else {
          return dept_id;
      }
   }
}
