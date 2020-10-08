package com.davidk.rest.webservices.restfulwebservices.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse  {

    private Date timestamp;
    private String message;
    private String details;


}
