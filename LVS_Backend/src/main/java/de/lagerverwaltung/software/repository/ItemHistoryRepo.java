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

   // @Query(value = "SELECT * FROM item_history WHERE container_id := containerID AND timestamp >= ", nativeQuery = true)
    List<ItemHistory> findAllByTimestampGreaterThanEqualAndAndTimestampLessThanEqual(LocalDateTime startTime, LocalDateTime endTime);



    @Query(value = "SELECT * FROM item_history WHERE item_category = :itemCategory", nativeQuery = true)
    List<ItemHistory> getCategoryHistory(@Param("itemCategory") String categoryName);

    List<ItemHistory> findByItemCategoryAndTimestampGreaterThanEqualAndTimestampLessThanEqual
            (String categoryName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     *
     * Import-History
     *
     */

    @Query(value = "SELECT COUNT(*) AS ImportSum FROM item_history WHERE sold IS NULL", nativeQuery = true)
    int countAllImportedItems();

    @Query(value = "SELECT COUNT(*) AS ImportSum FROM item_history WHERE sold IS NULL AND timestamp >= :startTime AND timestamp <= :endTime"
            , nativeQuery = true)
    int countAllImportedItems(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "SELECT COUNT(*) AS ImportSum FROM item_history WHERE sold IS NULL AND container_id = :containerID", nativeQuery = true)
    int countAllImportedItemsPerContainer(@Param("containerID") Long containerID);

    @Query(value =
            "SELECT COUNT(*) AS ImportSum FROM item_history " +
            "WHERE sold IS NULL AND container_id = :containerID " +
            "AND timestamp >= :startTime AND  timestamp <= :endTime", nativeQuery = true)

    int countAllImportedItemsPerContainer(
            @Param("containerID") Long containerID,
            @Param("startTime") LocalDateTime startTime,
            @Param("endtime") LocalDateTime endTime);


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


}
