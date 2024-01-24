package de.lagerverwaltung.software.model;

import de.lagerverwaltung.software.enumeration.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int space;
    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private ItemCategory category;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "container_id")
    private ItemContainer container;
}
