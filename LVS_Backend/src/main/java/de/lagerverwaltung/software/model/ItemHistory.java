package de.lagerverwaltung.software.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itemHistory")

public class ItemHistory {
    @Id
    private Long itemID;
    private LocalDateTime timestamp;
    private String itemName;
    private double itemPrice;
    private int itemSpace;
    private String itemCategoryName;
    private boolean sold; // Wenn Item Delete, dann quasi sold = true




    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private ItemCategory itemCategory;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "container_id")
    private ItemContainer container;
}
