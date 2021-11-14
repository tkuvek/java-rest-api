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
        validator.isEmpty(departments);
        if(!validator.isSuccess()) return validator.getErrorMessages();
        return gson.toJson(departments);
    }

    /**
     * Gets a specific department
     * @param companyName
     * @param deptId
     * @return Json Department Object
     */

    public String getDepartment(String companyName, String deptId) {
        validator.departmentExists(companyName, Integer.parseInt(deptId));
        if(!validator.isSuccess())  {
            return validator.getErrorMessages();
        } else {
            Department department = dl.getDepartment(companyName, Integer.parseInt(deptId));
            return gson.toJson(department);
        }
    }
    
    public Department getDepartmentObject(String companyName, String deptId) {
        validator.departmentExists(companyName, Integer.parseInt(deptId));
        if(!validator.isSuccess()) {
            return null;
        } else {
            Department department = dl.getDepartment(companyName, Integer.parseInt(deptId));
            return department;
        }
    }
    
    /**
     * Create/Insert a new department
     * @param company
     * @param dept_name
     * @param dept_no
     * @param location
     * @return Department object
     */
    public String insertDepartment(String company, String dept_name, String dept_no, String location) {
            String response = null;
            Department department = new Department(company, dept_name, dept_no, location);
            validator.uniqueDeptNo(dept_no);
            if(dl.insertDepartment(department) == null){
                response = validator.getErrorMessages();
            } else {
            List<Department> allDepartment = dl.getAllDepartment(company);
                for (Department dept : allDepartment) {
                    if(dept.getDeptNo().equals(dept_no)){
                        Department departmentObject = this.getDepartmentObject(company, String.valueOf(dept.getId()));
                        response = "{\n" + " \"success\":" + gson.toJson(departmentObject) + "\n}";
                    }
                }
            }
        return response;
    }

    /**
     * Put/update of a specific Department
     * @param dept
     * @return Json string of the updated object
     */
    public String updateDepartment(String dept){
      String response;
      Department department = gson.fromJson(dept, Department.class);
      validator.departmentExists("tk9480", department.getId());
      validator.uniqueDeptNo(department.getDeptNo());

        if(dl.updateDepartment(department) == null) {
            response = validator.getErrorMessages();
        } else {
            response = "{\n" + " \"success\":" + gson.toJson(department) + "\n}";
        }
      return response;

   }

    /**
     * Deletes a department by dept_id
     * @param comp
     * @param dept_id
     * @return int of the deleted department
     */
    public String deleteDepartment(String comp, int dept_id){
      validator.departmentExists(comp, dept_id);
      
      if(!validator.isSuccess()) return validator.getErrorMessages();

        return "{\n" + " \"success\": \"Department with id " + dept_id + " deleted.\"}";
   }
}
