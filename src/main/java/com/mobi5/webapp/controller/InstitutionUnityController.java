package com.mobi5.webapp.controller;

import com.mobi5.webapp.controller.utils.HeaderUtil;
import com.mobi5.webapp.controller.utils.TokenUtil;
import com.mobi5.webapp.model.InstitutionUnity;
import com.mobi5.webapp.repository.InstitutionUnityRepository;
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
 * REST controller for managing InstitutionUnity.
 */
@Path("/api/v1/institution-unity")
public class InstitutionUnityController {

    @Inject
    private Logger log;
    @Inject
    private InstitutionUnityRepository institutionUnityRepository;
    @Inject
    private TokenUtil tokenUtil;

    /**
     * POST : Create a new institutionUnity.
     *
     * @param authorization the authorization token
     * @param institutionUnity the institutionUnity to create
     * @return the Response with status 201 (Created) and with body the
     * new institutionUnity, or with status 400 (Bad Request) if the institutionUnity has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    public Response createInstitutionUnity(@HeaderParam("Authorization") String authorization, InstitutionUnity institutionUnity) throws URISyntaxException {
        log.debug("REST request to save InstitutionUnity : {}", institutionUnity);
        institutionUnityRepository.create(institutionUnity);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/institution-unity/" + institutionUnity.getId())),
                "institutionUnity", institutionUnity.getId().toString())
                .entity(institutionUnity).build();
    }

    /**
     * PUT : Updates an existing institutionUnity.
     *
     * @param authorization the authorization token
     * @param institutionUnity the institutionUnity to update
     * @return the Response with status 200 (OK) and with body the updated institutionUnity,
     * or with status 400 (Bad Request) if the institutionUnity is not valid,
     * or with status 500 (Internal Server Error) if the institutionUnity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    public Response updateInstitutionUnity(@HeaderParam("Authorization") String authorization, InstitutionUnity institutionUnity) throws URISyntaxException {
        log.debug("REST request to update InstitutionUnity : {}", institutionUnity);
        institutionUnityRepository.edit(institutionUnity);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), "institutionUnity", institutionUnity.getId().toString())
                .entity(institutionUnity).build();
    }

    /**
     * GET : get all the institutionUnities.
     * @param authorization the authorization token
     * @return the Response with status 200 (OK) and the list of institutionUnities in body
     
     */
    @GET
    public List<InstitutionUnity> getAllInstitutionUnities(@HeaderParam("Authorization") String authorization) {
        log.debug("REST request to get all InstitutionUnities");
        List<InstitutionUnity> institutionUnities = institutionUnityRepository.findAll();
        return institutionUnities;
    }

    /**
     * GET /:id : get the "id" institutionUnity.
     *
     * @param authorization the authorization token
     * @param id the id of the institutionUnity to retrieve
     * @return the Response with status 200 (OK) and with body the institutionUnity, or with status 404 (Not Found)
     */
    @Path("/{id}")
    @GET
    public Response getInstitutionUnity(@HeaderParam("Authorization") String authorization, @PathParam("id") Long id) {
        log.debug("REST request to get InstitutionUnity : {}", id);
        InstitutionUnity institutionUnity = institutionUnityRepository.find(id);
        return Optional.ofNullable(institutionUnity)
                .map(result -> Response.status(Response.Status.OK).entity(institutionUnity).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" institutionUnity.
     * 
     * @param authorization the authorization token
     * @param id the id of the institutionUnity to delete
     * @return the Response with status 200 (OK)
     */
    @Path("/{id}")
    @DELETE
    public Response removeInstitutionUnity(@HeaderParam("Authorization") String authorization, @PathParam("id") Long id) {
        log.debug("REST request to delete InstitutionUnity : {}", id);
        institutionUnityRepository.remove(institutionUnityRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), "institutionUnity", id.toString()).build();
    }
}
