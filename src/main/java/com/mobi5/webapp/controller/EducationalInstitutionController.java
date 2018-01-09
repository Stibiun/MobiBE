package com.mobi5.webapp.controller;

import com.mobi5.webapp.controller.utils.HeaderUtil;
import com.mobi5.webapp.controller.utils.TokenUtil;
import com.mobi5.webapp.model.EducationalInstitution;
import com.mobi5.webapp.repository.EducationalInstitutionRepository;
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
 * REST controller for managing EducationalInstitution.
 */
@Path("/api/v1/educational-institution")
public class EducationalInstitutionController {

    @Inject
    private Logger log;
    @Inject
    private EducationalInstitutionRepository educationalInstitutionRepository;
    @Inject
    private TokenUtil tokenUtil;

    /**
     * POST : Create a new educationalInstitution.
     *
     * @param authorization the authorization token
     * @param educationalInstitution the educationalInstitution to create
     * @return the Response with status 201 (Created) and with body the
     * new educationalInstitution, or with status 400 (Bad Request) if the educationalInstitution has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    public Response createEducationalInstitution(@HeaderParam("Authorization") String authorization, EducationalInstitution educationalInstitution) throws URISyntaxException {
        log.debug("REST request to save EducationalInstitution : {}", educationalInstitution);
        educationalInstitutionRepository.create(educationalInstitution);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/educational-institution/" + educationalInstitution.getId())),
                "educationalInstitution", educationalInstitution.getId().toString())
                .entity(educationalInstitution).build();
    }

    /**
     * PUT : Updates an existing educationalInstitution.
     *
     * @param authorization the authorization token
     * @param educationalInstitution the educationalInstitution to update
     * @return the Response with status 200 (OK) and with body the updated educationalInstitution,
     * or with status 400 (Bad Request) if the educationalInstitution is not valid,
     * or with status 500 (Internal Server Error) if the educationalInstitution couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    public Response updateEducationalInstitution(@HeaderParam("Authorization") String authorization, EducationalInstitution educationalInstitution) throws URISyntaxException {
        log.debug("REST request to update EducationalInstitution : {}", educationalInstitution);
        educationalInstitutionRepository.edit(educationalInstitution);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), "educationalInstitution", educationalInstitution.getId().toString())
                .entity(educationalInstitution).build();
    }

    /**
     * GET : get all the educationalInstitutions.
     *
     * @param authorization the authorization token
     * @return the Response with status 200 (OK) and the list of educationalInstitutions in body
     
     */
    @GET
    public List<EducationalInstitution> getAllEducationalInstitutions(@HeaderParam("Authorization") String authorization) {
        log.debug("REST request to get all EducationalInstitutions");
        List<EducationalInstitution> educationalInstitutions = educationalInstitutionRepository.findAll();
        return educationalInstitutions;
    }

    /**
     * GET /:id : get the "id" educationalInstitution.
     *
     * @param authorization the authorization token
     * @param id the id of the educationalInstitution to retrieve
     * @return the Response with status 200 (OK) and with body the educationalInstitution, or with status 404 (Not Found)
     */
    @Path("/{id}")
    @GET
    public Response getEducationalInstitution(@HeaderParam("Authorization") String authorization, @PathParam("id") Long id) {
        log.debug("REST request to get EducationalInstitution : {}", id);
        EducationalInstitution educationalInstitution = educationalInstitutionRepository.find(id);
        return Optional.ofNullable(educationalInstitution)
                .map(result -> Response.status(Response.Status.OK).entity(educationalInstitution).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" educationalInstitution.
     * 
     * @param authorization the authorization token
     * @param id the id of the educationalInstitution to delete
     * @return the Response with status 200 (OK)
     */
    @Path("/{id}")
    @DELETE
    public Response removeEducationalInstitution(@HeaderParam("Authorization") String authorization, @PathParam("id") Long id) {
        log.debug("REST request to delete EducationalInstitution : {}", id);
        educationalInstitutionRepository.remove(educationalInstitutionRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), "educationalInstitution", id.toString()).build();
    }
}
