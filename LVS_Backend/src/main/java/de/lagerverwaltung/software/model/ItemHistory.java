package de.lagerverwaltung.software.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itemHistory")
public class ItemHistory {
    @Id
    LocalDateTime timestamp;
    Long itemID;
    String itemName;
    double itemPrice;
    int itemSpace;
    String itemCategory;
    boolean sold; // Wenn Item Delete, dann quasi sold = true


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "container_id")
    private ItemContainer container;
}
