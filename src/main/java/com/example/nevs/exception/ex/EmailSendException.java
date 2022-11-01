package com.example.nevs.exception.ex;

public class EmailSendException extends ServiceException{
    public EmailSendException() {
        super();
    }

    public EmailSendException(String message) {
        super(message);
    }

    public EmailSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSendException(Throwable cause) {
        super(cause);
    }

    protected EmailSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
