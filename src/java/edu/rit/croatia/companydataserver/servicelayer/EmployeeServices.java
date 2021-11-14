package edu.rit.croatia.companydataserver.servicelayer;

import edu.rit.croatia.companydataserver.businesslayer.EmployeeEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
     * @param companyName
     * @param emp_name
     * @param emp_no
     * @param hire_date
     * @param job
     * @param salary
     * @param dept_id
     * @param mng_id
     * @return Response
     * @throws java.text.ParseException
     */
    @POST
    @Path("employee")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(@FormParam("company") String companyName,
                                   @FormParam("emp_name") String emp_name,
                                   @FormParam("emp_no") String emp_no,
                                   @FormParam("hire_date") Date hire_date, 
                                   @FormParam("job") String job,
                                   @FormParam("salary") double salary,
                                   @FormParam("dept_id") int dept_id,
                                   @FormParam("mng_id") int mng_id) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(hire_date);
        String insertEmployee = company.insertEmployee(companyName, emp_name, emp_no, dateStr, job, salary, dept_id, mng_id);
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
        return Response.ok(company.deleteEmployee(id)).build();
    }
    
}
