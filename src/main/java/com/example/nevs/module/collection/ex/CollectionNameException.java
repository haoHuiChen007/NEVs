package com.example.nevs.module.collection.ex;

public class CollectionNameException extends CollectionException{
    public CollectionNameException() {
        super();
    }

    public CollectionNameException(String message) {
        super(message);
    }

    public CollectionNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionNameException(Throwable cause) {
        super(cause);
    }

    protected CollectionNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
