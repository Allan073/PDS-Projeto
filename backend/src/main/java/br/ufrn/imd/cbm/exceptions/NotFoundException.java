package br.ufrn.imd.cbm.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(NotFoundException e) { this(e.getMessage()); }
}
