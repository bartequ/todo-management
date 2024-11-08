package org.bszabat.todomanagement.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TodoManagementExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TodoManagementNotFoundException.class)
    public ClientError handleTodoManagementNotFoundException(TodoManagementNotFoundException e) {
        ClientError client = new ClientError(e.getMessage());
        log.info(client.code(), e);
        return client;
    }
}
