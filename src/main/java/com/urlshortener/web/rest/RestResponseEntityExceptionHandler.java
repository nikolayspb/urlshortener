package com.urlshortener.web.rest;

import com.urlshortener.service.exceptions.AccountDuplicateException;
import com.urlshortener.service.exceptions.ShortUrlNotFoundException;
import com.urlshortener.service.exceptions.TargetUrlDuplicateException;
import com.urlshortener.web.rest.dto.AccountCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.io.IOException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShortUrlNotFoundException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void notFound(HttpServletRequest req, ShortUrlNotFoundException e) {
        log.error("Exception at request " + req.getRequestURL(), e);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)

    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public void conflict(HttpServletRequest req, DataIntegrityViolationException e) throws IOException {
        log.error("Exception at request " + req.getRequestURL(), e);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(ValidationException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    void validationError(HttpServletRequest req, ValidationException e) {
        log.error("Exception at request " + req.getRequestURL());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(AccountDuplicateException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 3)
    AccountCreateResponse duplicateAccount(HttpServletRequest req, AccountDuplicateException e) {
        log.error("Exception at request " + req.getRequestURL(), e);
        return AccountCreateResponse.failure();
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(TargetUrlDuplicateException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE + 4)
    void duplicateAccount(HttpServletRequest req, TargetUrlDuplicateException e) {
        log.error("Exception at request " + req.getRequestURL(), e);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) //401
    @ExceptionHandler({UsernameNotFoundException.class, AccessDeniedException.class})
    @Order(Ordered.HIGHEST_PRECEDENCE + 5)
    void notAllowed(HttpServletRequest req, RuntimeException e) {
        log.error("Exception at request " + req.getRequestURL(), e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)      //400
    @ExceptionHandler(RuntimeException.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    void badRequest(HttpServletRequest req, RuntimeException e) {
        log.error("Exception at request {}" + req.getRequestURL(), e);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   //500
    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void handleError(HttpServletRequest req, Exception e) {
        log.error("Exception at request " + req.getRequestURL(), e);
    }

}