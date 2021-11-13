package edu.rit.croatia.companydataserver.servicelayer;

import edu.rit.croatia.companydataserver.businesslayer.CompanyEntity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.core.*;

import com.google.gson.Gson;

import javax.ws.rs.*;

@Path("CompanyServices")
public class CompanyServices {
    private CompanyEntity company = null;    
    private Gson gson = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompanyServices
     */
    public CompanyServices() {
        gson = new Gson();
        company = new CompanyEntity();        
    }
    
    /**
     * Delete all information for a specific company name
     * Removes all departments, employees and timecards linked to the company
     * @param companyName
     * @return Response
     */
    @Path("company")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCompany(@QueryParam("company") String companyName) {
        if(company.deleteCompany(companyName) == 0) {
            return Response.ok("{\n" + " \"success\": \"Company " + companyName + " information deleted.\"}").build();
        } else {
            return Response.ok("{\"error:\": \"Failed to delete company with name: " + companyName +".\"}").build();
        }
    }
    
}
