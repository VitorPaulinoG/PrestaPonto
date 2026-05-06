package br.senac.ead.prestaponto.api.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}
