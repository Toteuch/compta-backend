package com.toteuch.compta.backend.exception;

import com.toteuch.compta.backend.endpoint.ErrorDTO;
import com.toteuch.compta.backend.utils.cdi.qualifier.French;
import com.toteuch.compta.backend.utils.i18n.Labels;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Locale;

@Provider
public class ComptaExceptionHandler implements ExceptionMapper<Throwable> {

    private static final String TECHNICAL_ERROR_MSG = "exception.technical";

    @Inject
    Labels labelsEn;

    @Inject
    @French
    Labels labelsFr;

    @Inject
    Logger log;

    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(Throwable exception) {
        Status httpStatus = Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof ComptaException) httpStatus = ((ComptaException) exception).getHttpStatus();
        if (exception.getClass() == NotSupportedException.class) httpStatus = Status.BAD_REQUEST;
        if (exception.getClass() == NotAllowedException.class) httpStatus = Status.METHOD_NOT_ALLOWED;
        log.error(exception.getClass().getName());
        log.error(exception.getMessage(), exception);
        List<Locale> acceptedLanguage = headers.getAcceptableLanguages();
        Labels labelsToUse = labelsEn;
        for (Locale locale : acceptedLanguage) {
            if (locale.getLanguage().startsWith(new Locale("en").getLanguage())) {
                labelsToUse = labelsEn;
                break;
            }
            if (locale.getLanguage().startsWith(new Locale("fr").getLanguage())) {
                labelsToUse = labelsFr;
                break;
            }
        }
        String translatedMessage = labelsToUse.getString(TECHNICAL_ERROR_MSG);
        return Response.status(httpStatus).entity(new ErrorDTO(translatedMessage)).build();
    }
}
