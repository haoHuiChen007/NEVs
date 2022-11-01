package com.example.nevs.module.collection.ex;

public class CollectionInsertException extends CollectionException{
    public CollectionInsertException() {
        super();
    }

    public CollectionInsertException(String message) {
        super(message);
    }

    public CollectionInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionInsertException(Throwable cause) {
        super(cause);
    }

    protected CollectionInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
