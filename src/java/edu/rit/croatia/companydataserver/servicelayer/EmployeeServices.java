package edu.rit.croatia.companydataserver.servicelayer;

import edu.rit.croatia.companydataserver.businesslayer.EmployeeEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import companydata.Employee;
import java.sql.Date;

import javax.ws.rs.*;

@Path("CompanyServices")
public class EmployeeServices {

    private EmployeeEntity company = null;
    private Gson gson = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompanyServices
     */
    public EmployeeServices() {
        gson = new Gson();
        company = new EmployeeEntity();
    }

    @GET
    @Path("employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@QueryParam("company") String companyName) {
        return Response.ok(company.getEmployees(companyName)).build();
    }
    
    @GET
    @Path("employee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@QueryParam("emp_id") int employeeId) {
        return Response.ok(company.getEmployee(employeeId)).build();
    }
    
    @POST
    @Path("employee")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(@FormParam("emp_name") String emp_name,
                                   @FormParam("emp_no") String emp_no,
                                   @FormParam("hire_date") Date hire_date, 
                                   @FormParam("job") String job,
                                   @FormParam("salary") double salary,
                                   @FormParam("dept_id") int dept_id,
                                   @FormParam("mng_id") int mng_id){
        Employee employeeObject = new Employee(emp_name, emp_no, hire_date, job, salary, dept_id, mng_id);
        // String inJson = Response.ok(deptObject.toString());
        company.insertEmployee(employeeObject);
        return Response.ok("{\n" + " \"success\":" + gson.toJson(employeeObject) + "\n}").build();
    }
    
    @Path("employee")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Employee employee) {
        String inJson = gson.toJson(employee, Employee.class);
        // DepartmentEntity dept = gson.fromJson(inJson, DepartmentEntity.class);
        company.updateEmployee(inJson);
        return Response.ok("Employee Updated: " + inJson).build();
    }
    
    @Path("employee")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@QueryParam("emp_id") int id) {
        // return Response.ok("id is: " + id.getClass()).build();
        company.deleteEmployee(id);
        // if delete DB record successful send ok, otherwise Repsonse.Status.NO_CONTENT
        // with no msg
        return Response.ok("Employee " + id + " deleted.").build();
    }

}
