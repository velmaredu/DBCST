package com.uva.users.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class BodegaException extends RuntimeException{
    public BodegaException(String mensaje) {
        super(mensaje);
    }
}
