package com.mobi5.webapp;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mobi5.webapp.controller.EducationalInstitutionController.class);
        resources.add(com.mobi5.webapp.controller.InstitutionUnityController.class);
        resources.add(com.mobi5.webapp.controller.MobileApplicationUserController.class);
        resources.add(com.mobi5.webapp.controller.StudentController.class);
        resources.add(com.mobi5.webapp.controller.mobile.StudentController.class);
        resources.add(com.mobi5.webapp.interceptor.GZipInterceptor.class);
    }
}
