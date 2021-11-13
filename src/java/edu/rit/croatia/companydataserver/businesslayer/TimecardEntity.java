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

    public String getTimecard(String timecard_id) {
        Timecard timecard = dl.getTimecard(Integer.parseInt(timecard_id));
        if (timecard == null) {
            return "{\"error:\": \"No timecard found for ID " + timecard_id + ".\"}";
        }
        return gson.toJson(timecard);
    }

    public String getTimecards(String emp_id) {
        List<Timecard> timecards = dl.getAllTimecard(Integer.parseInt(emp_id));
        if (timecards.isEmpty()) {
            return "{\"error:\": \"No timecards found for employee " + emp_id + ".\"}";
        }
        return gson.toJson(timecards);
    }

    public Timecard insertTimecard(Timecard inTC) {
        if (dl.insertTimecard(inTC) == null) {
            return null;
        } else {
            return inTC;
        }
    }
    
    public String updateTimecard(String inJson) {
      Timecard timecard = gson.fromJson(inJson, Timecard.class);
      dl.updateTimecard(timecard);
      return inJson;
    }

    public int deleteTimecard(int timecard_id) {
        return dl.deleteTimecard(timecard_id);
    }
}
