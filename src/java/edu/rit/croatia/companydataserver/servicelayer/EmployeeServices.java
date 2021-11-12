package edu.rit.croatia.companydataserver.servicelayer;

import companydata.Department;
import edu.rit.croatia.companydataserver.businesslayer.EmployeeEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;

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

}
