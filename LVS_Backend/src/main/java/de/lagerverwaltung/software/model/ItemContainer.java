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
@Table(name="container")
public class ItemContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int maxCapacity;
    private int curCapacity;
    private String name;


    @OneToMany(mappedBy = "container")
    private Set<Item> items;

    @OneToMany(mappedBy = "container", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ItemHistory> itemHistory;
}
