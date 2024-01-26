package de.lagerverwaltung.software.repository;

import de.lagerverwaltung.software.model.ItemContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.OptionalLong;

public interface ContainerRepo extends JpaRepository<ItemContainer, Long> {

    /*
    @Modifying
    @Query(value = "UPDATE container SET cur_capacity = cur_capacity - :item_capacity WHERE id = :containerID", nativeQuery = true)
    void subtractCapacity(@Param("itemCapacity") int itemCapacity, @Param("containerID") Optional<Long> containerID);

    @Modifying
    @Query(value = "UPDATE container SET cur_capacity = cur_capacity + :item_capacity WHERE id = :containerID", nativeQuery = true)
    void addCapacity(@Param("itemCapacity") int itemCapacity, @Param("containerID") Optional<Long> containerID);


     */

}
