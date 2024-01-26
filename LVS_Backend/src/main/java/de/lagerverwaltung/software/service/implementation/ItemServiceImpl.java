package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.exception.NoSpaceAvailableException;
import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemCategory;
import de.lagerverwaltung.software.model.ItemContainer;
import de.lagerverwaltung.software.repository.ContainerRepo;
import de.lagerverwaltung.software.repository.ItemRepo;
import de.lagerverwaltung.software.service.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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

    /**
     * Erstellt neue Items
     * @param item Item, das erstellt werden soll
     * @return Rckmeldung ueber erstelltes Item
     */
    @Override
    public Item create(Item item) {
        log.info("Saving new item: {}", item.getName());
        //item.setImageUrl(item.getCategory().getImageUrl()); //Set Image of items category
        if (item.getContainer().getCurCapacity() + item.getSpace() < item.getContainer().getMaxCapacity()){
            Optional<ItemContainer> storingContainer = containerRepo.findById(item.getContainer().getId());
            storingContainer.get().setCurCapacity(storingContainer.get().getCurCapacity() + item.getSpace());
            containerRepo.save(storingContainer.get());
            return itemRepo.save(item);
        }
        else {
            try {
                throw new NoSpaceAvailableException(item.getContainer().getId());
            } catch (NoSpaceAvailableException e) {
                log.info(e.getMessage());
                return null;
            }
        }
    }


    /**
     * Gibt Liste der Items aus
     * @param limit Definiert, wie viele Items gezeigt werden sollen
     * @return Liste der Items
     */
    @Override
    public Collection<Item> list(int limit) {
        log.info("Fetching all items");
        return itemRepo.findAll(PageRequest.of(0, limit)).toList();
    }



    @Override
    public Collection<Item> listByCategory(String category) {
        log.info("Fetching all items by Category");
        return itemRepo.filterItemsByCategory(category).stream().toList();
    }

    /**
     * Gibt ein ausgewaehltes Item zurück
     * @param id ID des Items
     * @return ausgewaehltes Item
     */
    @Override
    public Item get(Long id) {
        log.info("Fetching chosen Item by id: {}", id);
        return itemRepo.findById(id).get();
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
     * Loescht Item
     * @param id ID des zu loeschenden Items
     * @return Response
     */
    @Override
    public Boolean delete(Long id) {
        log.info("Deleting item by ID: {}", id);
        itemRepo.deleteById(id);
        return Boolean.TRUE;
    }

}
