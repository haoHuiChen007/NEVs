package com.example.nevs.module.collection.ex;

public class CollectionUserIdException extends CollectionException{
    public CollectionUserIdException() {
        super();
    }

    public CollectionUserIdException(String message) {
        super(message);
    }

    public CollectionUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionUserIdException(Throwable cause) {
        super(cause);
    }

    protected CollectionUserIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
