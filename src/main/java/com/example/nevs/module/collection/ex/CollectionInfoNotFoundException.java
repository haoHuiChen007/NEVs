package com.example.nevs.module.collection.ex;

public class CollectionInfoNotFoundException extends CollectionException {
    public CollectionInfoNotFoundException() {
        super();
    }

    public CollectionInfoNotFoundException(String message) {
        super(message);
    }

    public CollectionInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionInfoNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CollectionInfoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
