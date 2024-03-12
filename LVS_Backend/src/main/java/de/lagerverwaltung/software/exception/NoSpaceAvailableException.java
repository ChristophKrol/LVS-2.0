package de.lagerverwaltung.software.exception;

public class NoSpaceAvailableException extends RuntimeException{
    public NoSpaceAvailableException(Long containerID){
        super("No Space available in Container " + containerID);
    }

    public NoSpaceAvailableException(Long containerID, Throwable cause) {
        super("No Space available in Container " + containerID, cause);
    }
}
