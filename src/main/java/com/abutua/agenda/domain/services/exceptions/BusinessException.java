package com.abutua.agenda.domain.services.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String msg) {
        super(msg);
    }
}