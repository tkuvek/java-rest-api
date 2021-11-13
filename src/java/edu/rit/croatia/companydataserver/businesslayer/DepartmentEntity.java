package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.*;
import java.util.List;

/**
 *
 * @author Kristina
 */
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

    public String getDepartments(String companyName) {
        List<Department> departments = dl.getAllDepartment(companyName);
        if (departments.isEmpty()) {
            return "{\"error:\": \"No department found for company " + companyName + ".\"}";
        }
        return gson.toJson(departments);
    }

    /*
        Gets a specific department
    */
    public String getDepartment(String companyName, String deptId) {
        Department department = dl.getDepartment(companyName, Integer.parseInt(deptId));
        if (department == null) {
            return "{\"error:\": \"No department found for company " + companyName + ".\"}";
        } else {
            return gson.toJson(department);
        }
    }
    
    /*
      Create/Insert a specific department
    */
    public Department insertDepartment(Department dept) {
        if(dl.insertDepartment(dept) == null){
            return null;
        } else {
            return dept;
        }
    }

/*
    Updates a specific department
*/
  public String updateDepartment(String dept){
      Department department = gson.fromJson(dept, Department.class);
        if (dl.updateDepartment(department) == null) {
            return "{\"error:\": \"Unable to update department.\"}";
        } else {
            return "{\n" + " \"success\":" + gson.toJson(department) + "\n}";
        }
   }

/*
    Updates a specific department
*/
  public int deleteDepartment(String comp, int dept_id){
      
      if(dl.deleteDepartment(comp, dept_id) == 0) {
         return 0;
      } else {
          return dept_id;
      }
   }
    // @Override
    // public String toString() {
    //     // area = Math.PI * this.radius * this.radius;
    //     String str = "{\"id\":\"" + this.id + "\",\"radius\":\"" + this.radius + "\", \"area\":\"" + this.area + "\"}";
    //     return str;
    // }

}
