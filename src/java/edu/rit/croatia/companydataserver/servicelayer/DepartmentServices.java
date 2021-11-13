package edu.rit.croatia.companydataserver.servicelayer;

import companydata.Department;
import edu.rit.croatia.companydataserver.businesslayer.DepartmentEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;

import javax.ws.rs.*;

/**
 * REST Web Service
 *
 * @author Kristina
 */
@Path("CompanyServices")
public class DepartmentServices {

    private DepartmentEntity company = null;
    private Gson gson = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompanyServices
     */
    public DepartmentServices() {
        gson = new Gson();
        company = new DepartmentEntity();
    }

    @GET
    @Path("departments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartments(@QueryParam("company") String companyName) {
        return Response.ok(company.getDepartments(companyName)).build();
    }

    @GET
    @Path("department")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartment(@QueryParam("company") String companyName, @QueryParam("id") String id) {
        return Response.ok(company.getDepartment(companyName, id)).build();
    }

    @POST
    @Path("department")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertDepartment(@FormParam("company") String c,
                                     @FormParam("dept_name") String dept_name,
                                     @FormParam("dept_no") String dept_no, 
                                     @FormParam("location") String location
                                    ) {                                 
        Department deptObject = new Department(c, dept_name, dept_no, location);
        if(company.insertDepartment(deptObject) == null){
            return Response.ok("{\"error:\": \"Failed to insert department.\"}").build();
        } else {
            return Response.ok("{\n" + " \"success\":" + gson.toJson(deptObject) + "\n}").build();
        }
    }

    @Path("department")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDepartment(String inJson) {
        String updateDepartment = company.updateDepartment(inJson);
        return Response.ok(updateDepartment).build();
    }

    @Path("department")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDepartment(@QueryParam("dept_id") int id, @QueryParam("company") String companyName) {
        if(company.deleteDepartment(companyName, id) == 0) {
            return Response.ok("{\"error:\": \"Failed to delete department with id: " + id +".\"}").build();
        } else {
            return Response.ok("{\n" + " \"success\": \"Department with id " + id + " deleted.\"}").build();
        }
    }
}