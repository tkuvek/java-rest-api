package edu.rit.croatia.companydataserver.servicelayer;

import companydata.Timecard;
import edu.rit.croatia.companydataserver.businesslayer.TimecardEntity;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import edu.rit.croatia.companydataserver.businesslayer.Validator;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.ws.rs.*;

@Path("CompanyServices")
public class TimecardServices {

    private TimecardEntity company = null;
    private Gson gson = null;
    Validator validator = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TimecardServices
     */
    
    public TimecardServices() {
        gson = new Gson();
        company = new TimecardEntity();
        validator = new Validator();
    }

    /**
     * GET A TIMECARD BY TIMECARD_ID
     * @param timecardId
     * @return Response
     */
    @GET
    @Path("timecard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimecard(@QueryParam("timecard_id") String timecardId) {
        return Response.ok(company.getTimecard(timecardId)).build();
    }

    /**
     * GET ALL TIMECARDS FOR A SPECIFIC EMPLOYEE
     * @param emp_id
     * @return Response
     */
    @GET
    @Path("timecards")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimecards(@QueryParam("emp_id") int emp_id) {
        return Response.ok(company.getTimecards(emp_id)).build();
    }

    /**
     * CREATE/INSERT A TIMECARD
     * @param start_time
     * @param end_time
     * @param emp_id
     * @return Response
     * @throws java.text.ParseException
     */
    @POST
    @Path("timecard")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTimecard(
            @FormParam("start_time") Timestamp start_time,
            @FormParam("end_time") Timestamp end_time,
            @FormParam("emp_id") int emp_id
    ) throws ParseException {
        Timecard tcObject = new Timecard(start_time, end_time, emp_id);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = df.format(start_time);
        String endStr = df.format(end_time);
        String insertTimecard = company.insertTimecard(startStr, endStr, emp_id);
        return Response.ok(insertTimecard).build();
    }

    /**
     * UPDATE A TIMECARD
     * @param inJson
     * @return Response
     */
    @Path("timecard")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTimecard(String inJson) {
        String updateTimecard = company.updateTimecard(inJson);
        return Response.ok(updateTimecard).build();
    }

    /**
     * DELETE A TIMECARD BY TIMECARD_ID
     * @param timecard_id
     * @return Response
     */
    @Path("timecard")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTimecard(@QueryParam("timecard_id") int timecard_id) {
        String deleteTimecard = company.deleteTimecard(timecard_id);
        return Response.ok(deleteTimecard).build();
    }

}
