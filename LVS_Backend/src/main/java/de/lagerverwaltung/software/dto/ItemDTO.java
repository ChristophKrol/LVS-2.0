package de.lagerverwaltung.software.dto;

import java.util.UUID;

public record ItemDTO (
        Long id,
        String name,
        double price,
        int space,
        String category,
        Long containerID
){
}
