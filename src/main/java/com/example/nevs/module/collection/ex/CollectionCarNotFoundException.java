package com.example.nevs.module.collection.ex;

public class CollectionCarNotFoundException extends CollectionException{
    public CollectionCarNotFoundException() {
        super();
    }

    public CollectionCarNotFoundException(String message) {
        super(message);
    }

    public CollectionCarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionCarNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CollectionCarNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
