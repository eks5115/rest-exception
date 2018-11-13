package com.github.eks5115.rest.exception.handler;

import com.github.eks5115.rest.exception.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eks5115
 */
public abstract class AbstractRestExceptionHandler {

    @Resource
    private HttpServletRequest request;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception exception) {
        exception.printStackTrace();
        return addBasic(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(RestValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRestValidationException(RestValidationException exception) {
        Map<String, Object> map = addBasic(HttpStatus.BAD_REQUEST, exception);
        return addBindingResultException(map, exception);
    }

    protected Map<String, Object> addBasic(HttpStatus httpStatus, Exception exception) {

        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", new Date());
        map.put("path", request.getServletPath());
        map.put("status", httpStatus.value());
        map.put("error", httpStatus.getReasonPhrase());

        addBasicException(map, exception);

        return map;
    }

    /**
     *
     * @param map
     * @param exception
     * @return
     */
    private Map<String, Object> addBasicException(Map<String, Object> map, Exception exception) {

        map.put("exception", exception.getClass().getName());
        map.put("message", exception.getMessage());

        return map;
    }

    /**
     *
     * @param map
     * @param exception
     * @return
     */
    private Map<String, Object> addBindingResultException(Map<String, Object> map, RestValidationException exception) {
        map.put("errors", exception.getErrors());
        return map;
    }
}