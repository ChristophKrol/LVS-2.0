package de.lagerverwaltung.software.exception;

public class ContainerNotEmptyException extends RuntimeException{
    public ContainerNotEmptyException(Long id){
        super("Container " + id + "is not empty");
    }

    public ContainerNotEmptyException(Long id, Throwable throwable){
        super("Container " + id + "is not empty", throwable);
    }

}
