package de.lagerverwaltung.software.repository;

import de.lagerverwaltung.software.enumeration.Category;
import de.lagerverwaltung.software.model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepo extends JpaRepository<ItemCategory, Long> {

    @Query(value = "SELECT * FROM category WHERE name = :name", nativeQuery = true)
    ItemCategory getByName(@Param("name") String name);

    @Query(value = "DELETE FROM category WHERE name = :name", nativeQuery = true)
    void deleteByName(@Param("name") String name);

}
