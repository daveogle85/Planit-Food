package com.planitfood.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestFailedException extends Exception {

    private static Logger logger = LogManager.getLogger(RequestFailedException.class);

    public RequestFailedException(HttpServletRequest request, Exception exception) {
        super("Request" + request.getRequestURL() +
                ": failed -" + exception.getMessage());
        logger.error("Request" + request.getRequestURL() +
                ": failed -" + exception.getMessage());
    }
}
