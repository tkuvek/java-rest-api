package edu.rit.croatia.companydataserver.servicelayer;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(edu.rit.croatia.companydataserver.servicelayer.CompanyServices.class);
        resources.add(edu.rit.croatia.companydataserver.servicelayer.DepartmentServices.class);
        resources.add(edu.rit.croatia.companydataserver.servicelayer.EmployeeServices.class);
        resources.add(edu.rit.croatia.companydataserver.servicelayer.TimecardServices.class);
    }

}
