package com.mobi5.webapp.controller.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HeaderUtil {

    public static Response.ResponseBuilder createAlert(Response.ResponseBuilder rb, String message, String param) {
        return rb.header("X-sampleGradleApp-alert", message).header("X-sampleGradleApp-params", param);
    }

    public static Response.ResponseBuilder createEntityCreationAlert(Response.ResponseBuilder rb, String entityName, String param) {
        return createAlert(rb, "sampleGradleApp." + entityName + ".created", param);
    }

    public static Response.ResponseBuilder createEntityUpdateAlert(Response.ResponseBuilder rb, String entityName, String param) {
        return createAlert(rb, "sampleGradleApp." + entityName + ".updated", param);
    }

    public static Response.ResponseBuilder createEntityDeletionAlert(Response.ResponseBuilder rb, String entityName, String param) {
        return createAlert(rb, "sampleGradleApp." + entityName + ".deleted", param);
    }

    public static Response createSuccessResponse(Object data) {
        return Response.ok(data, MediaType.APPLICATION_JSON).build();
    }
}
