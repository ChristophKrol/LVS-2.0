package de.lagerverwaltung.software.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.lagerverwaltung.software.enumeration.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
//@JsonIgnoreProperties({"category", "container"}) // Wird benötigt, damit bei SELECT * FROM ITEM WHERE category_id = X keine Fehler kommen

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int space;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "category_id")
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "container_id")
    private ItemContainer container;
}
