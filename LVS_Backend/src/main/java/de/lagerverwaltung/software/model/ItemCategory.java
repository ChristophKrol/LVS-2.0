package de.lagerverwaltung.software.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class ItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Item> items;

    @OneToMany(mappedBy = "itemCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ItemHistory> itemHistory;
}
