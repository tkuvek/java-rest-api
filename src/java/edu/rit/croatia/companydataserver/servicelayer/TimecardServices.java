package edu.rit.croatia.companydataserver.servicelayer;

import companydata.Timecard;
import edu.rit.croatia.companydataserver.businesslayer.TimecardEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import companydata.Employee;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.*;

@Path("CompanyServices")
public class TimecardServices {

    private TimecardEntity company = null;
    private Gson gson = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompanyServices
     */
    
    public TimecardServices() {
        gson = new Gson();
        company = new TimecardEntity();
    }

    @GET
    @Path("timecard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimecard(@QueryParam("timecard_id") String timecardId) {
        return Response.ok(company.getTimecard(timecardId)).build();
    }

    @GET
    @Path("timecards")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimecards(@QueryParam("emp_id") String emp_id) {
        return Response.ok(company.getTimecards(emp_id)).build();
    }

    @POST
    @Path("timecard")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTimecard(
            @FormParam("start_time") Timestamp start_time,
            @FormParam("end_time") Timestamp end_time,
            @FormParam("emp_id") int emp_id
    ) {

        Timecard tcObject = new Timecard(start_time, end_time, emp_id);
        company.insertTimecard(tcObject);
        return Response.ok("{\n" + "\"success\":{\n" + "{\n" + "\"start_time\":" + tcObject.getStartTime() + "\n" + "\"end_time\":" + tcObject.getEndTime() + "\n" + "\"emp_id\":" + tcObject.getEmpId() + "\n}" + "\n}").build();
    }

    @Path("timecard")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTimecard(String inJson) {
        String updateTimecard = company.updateTimecard(inJson);
        return Response.ok("{\n" + "\"success\":" + updateTimecard + "\n}").build();
    }

    @Path("timecard")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTimecard(@QueryParam("timecard_id") int timecard_id) {
        if(company.deleteTimecard(timecard_id) == 0) {
            return Response.ok("{\"error:\": \"Failed to delete timecard with id: " + timecard_id + ".\"}").build();
        } else {
            return Response.ok("{\n" + " \"success\": \"Timecard " + timecard_id + " deleted.\"}").build();
        }
    }

}
