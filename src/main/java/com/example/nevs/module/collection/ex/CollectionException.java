package com.example.nevs.module.collection.ex;


import com.example.nevs.exception.ex.ServiceException;

public class CollectionException extends ServiceException {
    public CollectionException() {
        super();
    }

    public CollectionException(String message) {
        super(message);
    }

    public CollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionException(Throwable cause) {
        super(cause);
    }

    protected CollectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
