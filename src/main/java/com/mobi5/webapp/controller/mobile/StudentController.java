package com.mobi5.webapp.controller.mobile;

import com.mobi5.webapp.controller.utils.HeaderUtil;
import com.mobi5.webapp.controller.utils.TokenUtil;
import com.mobi5.webapp.model.MobileApplicationUser;
import com.mobi5.webapp.model.Student;
import javax.inject.Inject;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;

/**
 * REST controller for managing Student.
 */
@Path("/api/mobile/v1/student")
public class StudentController {

    @Inject
    private Logger log;
    @Inject
    private TokenUtil tokenUtil;

    @GET
    public Response getAllStudentsFromUser(@HeaderParam("Authorization") String authorization) {
        log.debug("REST request to get all Students||Authorization:{}", authorization);
        MobileApplicationUser mobileApplicationUser = tokenUtil.getMobileApplicationUserFromToken(authorization);
        if (mobileApplicationUser == null) {
            return tokenUtil.getUnauthorizedResponse();
        }
        List<Student> students = mobileApplicationUser.getStudents();
        return HeaderUtil.createSuccessResponse(students);
    }
}
