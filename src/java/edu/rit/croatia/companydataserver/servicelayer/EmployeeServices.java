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
     * Creates a new instance of EmployeeServices
     */
    public EmployeeServices() {
        gson = new Gson();
        company = new EmployeeEntity();
    }

    /**
     * GET ALL EMPLOYEES
     * @param companyName
     * @return Response
     */
    @GET
    @Path("employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@QueryParam("company") String companyName) {
        return Response.ok(company.getEmployees(companyName)).build();
    }
    
    /**
     * GET AN EMPLOYEE
     * @param employeeId
     * @return Response
     */
    @GET
    @Path("employee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@QueryParam("emp_id") int employeeId) {
        return Response.ok(company.getEmployee(employeeId)).build();
    }
    
    /**
     * CREATE EMPLOYEE
     * @param emp_name
     * @param emp_no
     * @param hire_date
     * @param job
     * @param salary
     * @param dept_id
     * @param mng_id
     * @return Response
     */
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
        String insertEmployee = company.insertEmployee(employeeObject);
        return Response.ok(insertEmployee).build();
    }
    
    /**
     * UPDATE EMPLOYEE
     * @param inJson
     * @return Response
     */
    @Path("employee")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(String inJson) {
        String updateEmployee = company.updateEmployee(inJson);
        return Response.ok(updateEmployee).build();
    }
    
    /**
     * DELETE EMPLOYEE
     * @param id
     * @return Response
     */
    @Path("employee")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@QueryParam("emp_id") int id) {
        if(company.deleteEmployee(id) == 0) {
            return Response.ok("{\"error:\": \"Failed to delete employee with id: " + id +".\"}").build();
        } else {
            return Response.ok("{\n" + " \"success\": \"Employee " + id + " deleted.\"}").build();
        }
    }
    
}
