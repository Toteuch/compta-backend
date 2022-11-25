package com.toteuch.compta.backend.exception;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ComptaException extends WebApplicationException {

    private final Status httpStatus;

    public ComptaException(@NotNull Status httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Status getHttpStatus() {
        return this.httpStatus;
    }
}
