package com.ragu.spar.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by nana on 12/20/14.
 */
public class SparExceptionMapper implements ExceptionMapper<SparException> {
    @Override
    public Response toResponse(SparException ex) {
        return Response.status(404)
                .entity(ex)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
