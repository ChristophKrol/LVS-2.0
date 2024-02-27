package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.dto.ItemDTO;
import de.lagerverwaltung.software.dto.ItemDTOMapper;
import de.lagerverwaltung.software.exception.NoSpaceAvailableException;
import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemCategory;
import de.lagerverwaltung.software.model.ItemContainer;
import de.lagerverwaltung.software.model.ItemHistory;
import de.lagerverwaltung.software.repository.ContainerRepo;
import de.lagerverwaltung.software.repository.ItemRepo;
import de.lagerverwaltung.software.service.ItemHistoryService;
import de.lagerverwaltung.software.service.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


/*
* ServerServiceImpl implementiert Funktionen, die später Datenbankabfragen durchführen
* */

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo;
    private final ContainerRepo containerRepo;
    private final ItemDTOMapper itemDTOMapper;
    private final ItemHistoryService itemHistoryService;

    /**
     * Erstellt neue Items
     * @param item Item, das erstellt werden soll
     * @return Rueckmeldung ueber erstelltes Item
     */
    @Override
    public Item create(Item item) {
        log.info("Saving new item: {}", item.getName());
        //item.setImageUrl(item.getCategory().getImageUrl()); //Set Image of items category
        // Nimm immer direkt aus Datenbank und überprüfe
        Optional<ItemContainer> storingContainer = containerRepo.findById(item.getContainer().getId());
        //Überprüfe Kapazität
        if (storingContainer.get().getCurCapacity() + item.getSpace() < storingContainer.get().getMaxCapacity()){
            storingContainer.get().setCurCapacity(storingContainer.get().getCurCapacity() + item.getSpace());
            // Ändere Kapazität
            containerRepo.save(storingContainer.get());

            // History-Object anlegen

            Item createdItem = itemRepo.save(item);
            itemHistoryService.create(createdItem, false);
            return createdItem;

        }
        else {
            throw new NoSpaceAvailableException(item.getContainer().getId());
        }
    }



    /**
     * Loescht Item
     * @param id ID des zu loeschenden Items
     * @return Response
     */
    @Override
    public Boolean delete(Long id) {
        log.info("Deleting item by ID: {}", id);
        Item itemToDelete = itemRepo.findById(id).get();
        ItemContainer storingContainer = containerRepo.findById(itemToDelete.getContainer().getId()).get();
        // Platz freimachen im Container
        storingContainer.setCurCapacity(storingContainer.getCurCapacity() - itemToDelete.getSpace());
        containerRepo.save(storingContainer);
        itemRepo.deleteById(id);
        //History objekt anlegen
        itemHistoryService.create(itemToDelete, true);
        return Boolean.TRUE;
    }


    /**
     * Gibt Liste der Items aus
     * @param limit Definiert, wie viele Items gezeigt werden sollen
     * @return Liste der Items
     */
    @Override
    public Collection<ItemDTO> list(int limit) {
        log.info("Fetching all items");
        return itemRepo.findAll(PageRequest.of(0, limit))
                .stream()
                .map(itemDTOMapper)
                .toList();
    }



    @Override
    public Collection<ItemDTO> listByCategory(Long category_id) {
        log.info("Fetching all items by Category");
        return itemRepo.filterItemsByCategory(category_id).stream().map(itemDTOMapper).toList();
    }

    public Collection<ItemDTO> getFromContainer(Long containerID){
        log.info("Fetching all items from container " + containerID);
        return itemRepo.getItemsFromContainer(containerID).stream().map(itemDTOMapper).toList();
    }

    public Collection<Item> getItemFromContainer(Long containerID){
        log.info("Fetching all items from container " + containerID);
        return itemRepo.getItemsFromContainer(containerID);
    }

    public Collection<ItemDTO> getFromContainerGroupByCategory(Long containerID, Long categoryID){
        log.info("Fetching all items by category from container " + containerID);
        return itemRepo.getItemsFromContainerGroupByCategory(containerID, categoryID).stream().map(itemDTOMapper).toList();
    }

    /**
     * Gibt ein ausgewaehltes Item zurück
     * @param id ID des Items
     * @return ausgewaehltes Item
     */
    @Override
    public ItemDTO get(Long id) {
        log.info("Fetching chosen Item by id: {}", id);
        return itemRepo.findById(id).stream().map(itemDTOMapper).toList().get(0); //Nur ein Item zurückgeben, also Index 0
    }

    /**
     * Aendert Eigenschaften eines Items
     * @param item Items, das geaendert werden soll
     * @return Response
     */
    @Override
    public Item update(Item item) {
        log.info("Updating item: {}", item.getName());
        return itemRepo.save(item);
    }


    /**
     * KPI-Services
     */

    public double calculateItemValue(){
        log.info("Returning total value");
        return itemRepo.calculateItemValue();
    }



}
