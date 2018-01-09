package com.mobi5.webapp.controller;

import com.mobi5.webapp.controller.utils.HeaderUtil;
import com.mobi5.webapp.controller.utils.TokenUtil;
import com.mobi5.webapp.model.Student;
import com.mobi5.webapp.repository.StudentRepository;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;

/**
 * REST controller for managing Student.
 */
@Path("/api/v1/student")
public class StudentController {

    @Inject
    private Logger log;
    @Inject
    private StudentRepository studentRepository;
    @Inject
    private TokenUtil tokenUtil;

    /**
     * POST : Create a new student.
     *
     * @param authorization the authorization token
     * @param student the student to create
     * @return the Response with status 201 (Created) and with body the
     * new student, or with status 400 (Bad Request) if the student has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    public Response createStudent(@HeaderParam("Authorization") String authorization, Student student) throws URISyntaxException {
        log.debug("REST request to save Student : {}", student);
        studentRepository.create(student);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/student/" + student.getId())),
                "student", student.getId().toString())
                .entity(student).build();
    }

    /**
     * PUT : Updates an existing student.
     *
     * @param authorization the authorization token
     * @param student the student to update
     * @return the Response with status 200 (OK) and with body the updated student,
     * or with status 400 (Bad Request) if the student is not valid,
     * or with status 500 (Internal Server Error) if the student couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    public Response updateStudent(@HeaderParam("Authorization") String authorization, Student student) throws URISyntaxException {
        log.debug("REST request to update Student : {}", student);
        studentRepository.edit(student);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), "student", student.getId().toString())
                .entity(student).build();
    }

    /**
     * GET : get all the students.
     * @param authorization the authorization token
     * @return the Response with status 200 (OK) and the list of students in body
     
     */
    @GET
    public List<Student> getAllStudents(@HeaderParam("Authorization") String authorization) {
        log.debug("REST request to get all Students");
        List<Student> students = studentRepository.findAll();
        return students;
    }

    /**
     * GET /:id : get the "id" student.
     *
     * @param authorization the authorization token
     * @param id the id of the student to retrieve
     * @return the Response with status 200 (OK) and with body the student, or with status 404 (Not Found)
     */
    @Path("/{id}")
    @GET
    public Response getStudent(@HeaderParam("Authorization") String authorization, @PathParam("id") Long id) {
        log.debug("REST request to get Student : {}", id);
        Student student = studentRepository.find(id);
        return Optional.ofNullable(student)
                .map(result -> Response.status(Response.Status.OK).entity(student).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" student.
     * 
     * @param authorization the authorization token
     * @param id the id of the student to delete
     * @return the Response with status 200 (OK)
     */
    @Path("/{id}")
    @DELETE
    public Response removeStudent(@HeaderParam("Authorization") String authorization, @PathParam("id") Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentRepository.remove(studentRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), "student", id.toString()).build();
    }
}
