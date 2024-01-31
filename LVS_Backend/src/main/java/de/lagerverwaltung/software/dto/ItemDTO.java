package de.lagerverwaltung.software.dto;

public record ItemDTO (
        Long id,
        String name,
        double price,
        int space,
        String category,
        Long containerID
){
}
