package com.ventadirecta.pruebatecnica.exceptions;


public class NotFoundException extends RuntimeException {

    public NotFoundException(String mensaje) {
        super(mensaje);
    }

}
