package edu.rit.croatia.companydataserver.servicelayer;

import edu.rit.croatia.companydataserver.businesslayer.DepartmentEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;

import javax.ws.rs.*;

@Path("CompanyServices")
public class DepartmentServices {

    private DepartmentEntity company = null;
    private Gson gson = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DepartmentServices
     */
    public DepartmentServices() {
        gson = new Gson();
        company = new DepartmentEntity();
    }

    /**
     * GET ALL DEPARTMENTS FOR COMPANY
     * @param companyName
     * @return Response
     */
    @GET
    @Path("departments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartments(@QueryParam("company") String companyName) {
        return Response.ok(company.getDepartments(companyName)).build();
    }

    /**
     * GET A SINGLE DEPARTMENT BY COMPANY AND ID
     * @param companyName
     * @param id
     * @return Response
     */
    @GET
    @Path("department")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartment(@QueryParam("company") String companyName, @QueryParam("id") String id) {
        return Response.ok(company.getDepartment(companyName, id)).build();
    }

    /**
     * CREATE/INSERT A DEPARTMENT
     * @param c
     * @param dept_name
     * @param dept_no
     * @param location
     * @return Response
     */
    @POST
    @Path("department")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertDepartment(@FormParam("company") String c,
                                     @FormParam("dept_name") String dept_name,
                                     @FormParam("dept_no") String dept_no, 
                                     @FormParam("location") String location
                                    ) {                                 
        String insertDepartment = company.insertDepartment(c, dept_name, dept_no, location);
        return Response.ok(insertDepartment).build();
    }

    /**
     * UPDATE A DEPARTMENT
     * @param inJson
     * @return
     */
    @Path("department")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDepartment(String inJson) {
        String updateDepartment = company.updateDepartment(inJson);
        return Response.ok(updateDepartment).build();
    }

    /**
     * DELETE A DEPARTMENT BY dept_id and company
     * @param id
     * @param companyName
     * @return Response
     */
    @Path("department")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDepartment(@QueryParam("dept_id") int id, @QueryParam("company") String companyName) {
        String deleteDepartment = company.deleteDepartment(companyName, id);
        return Response.ok(deleteDepartment).build();
    }
}