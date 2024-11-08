package org.bszabat.todomanagement.application.exception;

public class TodoManagementNotFoundException extends RuntimeException {

    public TodoManagementNotFoundException(String message) {
        super(message);
    }
}
