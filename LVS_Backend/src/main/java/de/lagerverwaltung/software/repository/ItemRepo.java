package de.lagerverwaltung.software.repository;

import de.lagerverwaltung.software.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Wird für (Custom) queries genutzt
 */
public interface ItemRepo extends JpaRepository<Item, Long> {

    //JOIN?

    @Query(value = "SELECT * FROM item WHERE category_id = :category_id", nativeQuery = true)
    List<Item> filterItemsByCategory(@Param("category_id") Long category_id);

    @Query(value = "SELECT COUNT(*) AS 'count', category_id AS 'category' FROM item GROUP BY category_id ORDER BY category_id ASC;", nativeQuery = true)
    List<CategoryGrouped> groupByCategory();

    @Query(value = "SELECT SUM(price) AS 'price', category FROM item GROUP BY category ORDER BY category ASC;", nativeQuery = true)
    List<CategoryPriceSum> groupedByPriceSumsByCategory();

    @Query(value = "SELECT * FROM item WHERE container_id = :containerID", nativeQuery = true)
    List<Item> getItemsFromContainer(@Param("containerID") Long containerID);

    @Query(value = "SELECT * FROM item WHERE container_id = :containerID AND category_id = :categoryID", nativeQuery = true )
    List<Item> getItemsFromContainerGroupByCategory(@Param("containerID") Long containerID, @Param("categoryID") Long categoryID);

    @Query(value = "SELECT SUM(price) AS 'price' FROM item", nativeQuery = true)
    double calculateItemValue();



    public static interface CategoryGrouped {
        int getCategory();

        int getCount();
    }

    public static interface CategoryPriceSum {
        int getCategory();

        double getPrice();
    }

}
