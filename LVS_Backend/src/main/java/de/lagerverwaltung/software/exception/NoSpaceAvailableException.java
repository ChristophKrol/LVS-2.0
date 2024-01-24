package de.lagerverwaltung.software.exception;

public class NoSpaceAvailableException extends  Exception{
    public NoSpaceAvailableException(Long ContainerID){
        super("No Space available in Container " + ContainerID);
    }
}
