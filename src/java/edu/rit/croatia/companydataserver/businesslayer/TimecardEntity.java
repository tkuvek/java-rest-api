package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Timestamp;
import java.text.ParseException;

public class TimecardEntity {

    private DataLayer dl = null;
    public Gson gson = null;
    private Validator validator = null;

    public TimecardEntity() {
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
     * Get a specific timecard
     * @param timecard_id
     * @return String json object
     */
    public String getTimecard(String timecard_id) {
        validator.timecardExists(Integer.parseInt(timecard_id));
        if(!validator.isSuccess())  {
            return validator.getErrorMessages();
        } else {
        Timecard timecard = dl.getTimecard(Integer.parseInt(timecard_id));
        return gson.toJson(timecard);
        }
    }
    
    public Timecard getTimecardObject(String timecard_id) {
        validator.timecardExists(Integer.parseInt(timecard_id));
        if(!validator.isSuccess()) {
            return null;
        } else {
            Timecard tc = dl.getTimecard(Integer.parseInt(timecard_id));
            return tc;
        }
    }

    /**
     * Get all timecards for an employee
     * @param emp_id
     * @return String Json List
     */
    public String getTimecards(int emp_id) {
        validator.employeeExists(emp_id);
        List<Timecard> timecards = dl.getAllTimecard(emp_id);
        validator.isEmpty(timecards);
        if(!validator.isSuccess()) return validator.getErrorMessages();
        return gson.toJson(timecards);
    }

    /**
     * Create/insert a timecard
     * @param start_time
     * @param end_time
     * @param emp_id
     * @return String Json object of the created timecard
     * @throws java.text.ParseException
     */
    public String insertTimecard(String start_time, String end_time, int emp_id) throws ParseException {
        String response = null;
        Timestamp start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start_time).getTime());
        Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end_time).getTime());
        
        validator.validateTimecardConditions(start, end);
        validator.employeeExists(emp_id);

   
        //Timecard timecard = new Timecard(start, end, emp_id);
        
        if(!validator.isSuccess()) {
            response = validator.getErrorMessages();
        } else {
            List<Timecard> allTimecard = dl.getAllTimecard(emp_id);
                for (Timecard tc : allTimecard) {
                    if(tc.getEmpId() == emp_id){
                        Timecard timecardObject = this.getTimecardObject(String.valueOf(tc.getId()));
                        timecardObject.setStartTime(start);
                        timecardObject.setEndTime(end);
                        response = "{\n" + " \"success\":" + gson.toJson(timecardObject) + "\n}";
                    }
                }
        }
        return response;
    }
    
    /**
     * Update/put a timecard
     * @param inJson
     * @return String Json object of the updated timecard
     */
    public String updateTimecard(String inJson) {
      Timecard timecard = gson.fromJson(inJson, Timecard.class);
      
      validator.timecardExists(timecard.getId());
      validator.validateTimecardConditions(timecard.getStartTime(), timecard.getEndTime());
      validator.employeeExists(timecard.getEmpId());

      if(!validator.isSuccess()) return validator.getErrorMessages();

       return ("{ \n\"success\":" + gson.toJson(dl.updateTimecard(timecard)) + "\n}");
    }

    /**
     * Delete a timecard by timecard_id
     * @param timecard_id
     * @return int of the timecard to be deleted
     */
    public String deleteTimecard(int timecard_id) {
      String response;
      validator.timecardExists(timecard_id);
      if(dl.deleteTimecard(timecard_id) == 0) {
         response = validator.getErrorMessages();
      } else {
        response = "{\n" + " \"success\": \"Timecard " + timecard_id + " deleted.\"}";
      }
      return response;
    }
}
