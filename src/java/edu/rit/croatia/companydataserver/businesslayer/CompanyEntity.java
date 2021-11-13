/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.rit.croatia.companydataserver.businesslayer;

import com.google.gson.Gson;
import companydata.DataLayer;

/**
 *
 * @author Kuvek
 */
public class CompanyEntity {
    private DataLayer dl = null;
    public Gson gson = null;
    private Validator validator = null;

    public CompanyEntity() {
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

    public int deleteCompany(String companyName) {
        if(dl.deleteCompany(companyName) == 0) {
            return 0;
        } else {
            return dl.deleteCompany(companyName);
        }
    }
}
