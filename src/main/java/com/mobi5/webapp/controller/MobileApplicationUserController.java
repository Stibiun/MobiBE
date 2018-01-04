package com.mobi5.webapp.controller;

import com.mobi5.webapp.controller.utils.HeaderUtil;
import com.mobi5.webapp.model.MobileApplicationUser;
import com.mobi5.webapp.repository.MobileApplicationUserRepository;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;

/**
 * REST controller for managing MobileApplicationUser.
 */
@Path("/api/mobile-application-user")
public class MobileApplicationUserController {

    @Inject
    private Logger log;
    @Inject
    private MobileApplicationUserRepository mobileApplicationUserRepository;

    /**
     * POST : Create a new mobileApplicationUser.
     *
     * @param mobileApplicationUser the mobileApplicationUser to create
     * @return the Response with status 201 (Created) and with body the
     * new mobileApplicationUser, or with status 400 (Bad Request) if the mobileApplicationUser has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    public Response createMobileApplicationUser(MobileApplicationUser mobileApplicationUser) throws URISyntaxException {
        log.debug("REST request to save MobileApplicationUser : {}", mobileApplicationUser);
        mobileApplicationUserRepository.create(mobileApplicationUser);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/mobile-application-user/" + mobileApplicationUser.getId())),
                "mobileApplicationUser", mobileApplicationUser.getId().toString())
                .entity(mobileApplicationUser).build();
    }

    /**
     * PUT : Updates an existing mobileApplicationUser.
     *
     * @param mobileApplicationUser the mobileApplicationUser to update
     * @return the Response with status 200 (OK) and with body the updated mobileApplicationUser,
     * or with status 400 (Bad Request) if the mobileApplicationUser is not valid,
     * or with status 500 (Internal Server Error) if the mobileApplicationUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    public Response updateMobileApplicationUser(MobileApplicationUser mobileApplicationUser) throws URISyntaxException {
        log.debug("REST request to update MobileApplicationUser : {}", mobileApplicationUser);
        mobileApplicationUserRepository.edit(mobileApplicationUser);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), "mobileApplicationUser", mobileApplicationUser.getId().toString())
                .entity(mobileApplicationUser).build();
    }

    /**
     * GET : get all the mobileApplicationUsers.
     
     * @return the Response with status 200 (OK) and the list of mobileApplicationUsers in body
     
     */
    @GET
    public List<MobileApplicationUser> getAllMobileApplicationUsers() {
        log.debug("REST request to get all MobileApplicationUsers");
        List<MobileApplicationUser> mobileApplicationUsers = mobileApplicationUserRepository.findAll();
        return mobileApplicationUsers;
    }

    /**
     * GET /:id : get the "id" mobileApplicationUser.
     *
     * @param id the id of the mobileApplicationUser to retrieve
     * @return the Response with status 200 (OK) and with body the mobileApplicationUser, or with status 404 (Not Found)
     */
    @Path("/{id}")
    @GET
    public Response getMobileApplicationUser(@PathParam("id") Long id) {
        log.debug("REST request to get MobileApplicationUser : {}", id);
        MobileApplicationUser mobileApplicationUser = mobileApplicationUserRepository.find(id);
        return Optional.ofNullable(mobileApplicationUser)
                .map(result -> Response.status(Response.Status.OK).entity(mobileApplicationUser).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" mobileApplicationUser.
     * 
     * @param id the id of the mobileApplicationUser to delete
     * @return the Response with status 200 (OK)
     */
    @Path("/{id}")
    @DELETE
    public Response removeMobileApplicationUser(@PathParam("id") Long id) {
        log.debug("REST request to delete MobileApplicationUser : {}", id);
        mobileApplicationUserRepository.remove(mobileApplicationUserRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), "mobileApplicationUser", id.toString()).build();
    }
}
