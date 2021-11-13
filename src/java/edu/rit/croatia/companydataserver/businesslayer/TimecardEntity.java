package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.*;
import java.util.List;

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
        Timecard timecard = dl.getTimecard(Integer.parseInt(timecard_id));
        if (timecard == null) {
            return "{\"error:\": \"No timecard found for ID " + timecard_id + ".\"}";
        }
        return gson.toJson(timecard);
    }

    /**
     * Get all timecards for an employee
     * @param emp_id
     * @return String Json List
     */
    public String getTimecards(String emp_id) {
        List<Timecard> timecards = dl.getAllTimecard(Integer.parseInt(emp_id));
        if (timecards.isEmpty()) {
            return "{\"error:\": \"No timecards found for employee " + emp_id + ".\"}";
        }
        return gson.toJson(timecards);
    }

    /**
     * Create/insert a timecard
     * @param inTC
     * @return String Json object of the created timecard
     */
    public String insertTimecard(Timecard inTC) {
        if (dl.insertTimecard(inTC) == null) {
            return "{\"error:\": \"No timecard is inserted.\"}";
        } else {
            return ("{ \n\"success\":" + gson.toJson(inTC) + "\n}");
        }
    }
    
    /**
     * Update/put a timecard
     * @param inJson
     * @return String Json object of the updated timecard
     */
    public String updateTimecard(String inJson) {
      Timecard timecard = gson.fromJson(inJson, Timecard.class);
      if(dl.updateTimecard(timecard) == null) {
          return "{\"error:\": \"Failed to update timecard.\"}";
      } else {
          return ("{ \n\"success\":" + inJson + "\n}");
      } 
    }

    /**
     * Delete a timecard by timecard_id
     * @param timecard_id
     * @return int of the timecard to be deleted
     */
    public int deleteTimecard(int timecard_id) {
        if(dl.deleteTimecard(timecard_id) == 0) {
            return 0;
        } else {
            return timecard_id;
        }
    }
}
