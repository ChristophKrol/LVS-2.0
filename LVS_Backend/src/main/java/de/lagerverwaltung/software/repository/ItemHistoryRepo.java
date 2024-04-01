package de.lagerverwaltung.software.repository;

import de.lagerverwaltung.software.model.ItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemHistoryRepo extends JpaRepository<ItemHistory, LocalDateTime> {

    /**
     *
     * History-All
     *
     */

    //@Query(value = "SELECT * FROM item_history WHERE container_id := containerID", nativeQuery = true)
    List<ItemHistory> findByContainer_Id(Long containerID);
    List<ItemHistory> findByContainer_IdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
            Long containerID,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

   // @Query(value = "SELECT * FROM item_history WHERE container_id := containerID AND timestamp >= ", nativeQuery = true)
    List<ItemHistory> findAllByTimestampGreaterThanEqualAndAndTimestampLessThanEqual(LocalDateTime startTime, LocalDateTime endTime);



    @Query(value = "SELECT * FROM item_history WHERE item_category_name = :itemCategory", nativeQuery = true)
    List<ItemHistory> getCategoryHistory(@Param("itemCategory") String categoryName);

    List<ItemHistory> findByItemCategoryName(String itemCategory);

    List<ItemHistory> findByItemCategoryNameAndTimestampGreaterThanEqualAndTimestampLessThanEqual
            (String categoryName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     *
     * Import-History
     *
     */

    @Query(value = "SELECT COUNT(*) AS ImportSum FROM item_history WHERE sold = false", nativeQuery = true)
    int countAllImportedItems();

    @Query(value = "SELECT COUNT(*) AS ImportSum FROM item_history WHERE sold = false AND timestamp >= :startTime AND timestamp <= :endTime"
            , nativeQuery = true)
    int countAllImportedItems(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "SELECT COUNT(*) AS ImportSum FROM item_history WHERE sold = false AND container_id = :containerID", nativeQuery = true)
    int countAllImportedItemsPerContainer(@Param("containerID") Long containerID);

    @Query(value =
            "SELECT COUNT(*) AS ImportSum FROM item_history " +
            "WHERE sold = false AND container_id = :containerID " +
            "AND timestamp >= :startTime AND  timestamp <= :endTime", nativeQuery = true)

    int countAllImportedItemsPerContainer(
            @Param("containerID") Long containerID,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);


    @Query(value =
            "SELECT COUNT(*) AS ImportSum FROM item_history " +
                    "WHERE sold = false AND item_category_name = :categoryName " , nativeQuery = true)
    int countAllImportedItemsPerCategory(@Param("categoryName") String categoryName);

    @Query(value =
            "SELECT COUNT(*) AS ImportSum FROM item_history " +
                    "WHERE sold = false AND item_category_name = :categoryName " +
                    "AND timestamp >= :startTime AND  timestamp <= :endTime", nativeQuery = true)
    int countAllImportedItemsPerCategory(
            @Param("categoryName") String categoryName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     *
     * Export-History (Sold Is True)
     *
     */

    int countAllBySoldIsTrue();
    int countByTimestampGreaterThanEqualAndTimestampLessThanEqualAndSoldIsTrue(LocalDateTime startTime, LocalDateTime endTime);

    int countByContainer_IdAndSoldIsTrue(Long containerID);
    int countByContainer_IdAndSoldIsTrueAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
            Long containerID, LocalDateTime startTime, LocalDateTime endTime);

    int countByItemCategoryAndSoldIsTrue(String categoryName);
    int countByItemCategoryAndSoldIsTrueAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
            String categoryName, LocalDateTime startTime, LocalDateTime endTime);


    /**
     *
     * Geldsumme Verkauf
     *
     */
    @Query(value = "SELECT IFNULL(SUM(item_price),0) FROM item_history WHERE sold = true", nativeQuery = true )
    double getSumExports();

    @Query(
            value = "SELECT IFNULL(SUM(item_price),0) FROM item_history " +
                    "WHERE sold = true AND timestamp >= :startTime " +
                    "AND timestamp <= :endTime", nativeQuery = true
    )
    double getSumExportsByTime(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(
            value = "SELECT IF NULL(SUM(item_price),0) FROM item_history " +
                    "WHERE sold = true AND item_category_name = :categoryName", nativeQuery = true
    )
    double getExportValueByCategory(@Param("categoryName") String categoryName);

    @Query(
         value = "SELECT IFNULL(SUM(item_price), 0) FROM item_history " +
                 "WHERE sold = true AND item_category_name = :categoryName " +
                 "AND timestamp >= :startTime AND timeStamp <= :endTime", nativeQuery = true
    )
    double getExportValuePerCategory(
            @Param("categoryName") String categoryName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

 /**
  *
  * Summe Einkauf
  *
  */

    @Query(value = "SELECT IFNULL(SUM(item_price), 0) FROM item_history WHERE sold = false", nativeQuery = true )
    double getSumImports();

    @Query(
         value = "SELECT IFNULL(SUM(item_price), 0) FROM item_history " +
                 "WHERE sold = false AND timestamp >= :startTime " +
                 "AND timestamp <= :endTime", nativeQuery = true
          )
    double getSumImportsByTime(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(
         value = "SELECT IFNULL(SUM(item_price), 0) FROM item_history " +
                 "WHERE sold = false  AND item_category_name = :categoryName", nativeQuery = true
    )
    double getImportValueByCategory(@Param("categoryName") String categoryName);

    @Query(
         value = "SELECT IFNULL(SUM(item_price), 0) FROM item_history " +
                 "WHERE sold = false AND item_category_name = :categoryName " +
                 "AND timestamp >= :startTime AND timeStamp <= :endTime", nativeQuery = true
    )
    double getImportValuePerCategory(
         @Param("categoryName") String categoryName,
         @Param("startTime") LocalDateTime startTime,
         @Param("endTime") LocalDateTime endTime);


}
