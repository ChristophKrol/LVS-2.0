package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.exception.ContainerNotEmptyException;
import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemContainer;
import de.lagerverwaltung.software.repository.ContainerRepo;
import de.lagerverwaltung.software.service.ItemContainerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ItemContainerImpl implements ItemContainerService {
    private final ContainerRepo containerRepo;
    private final ItemServiceImpl itemService;
    @Override
    public ItemContainer create(ItemContainer container) {
        log.info("Saving new Container: {}", container.getName());
        return containerRepo.save(container);
    }


    @Override
    public Collection<ItemContainer> list(int limit) {
        log.info("Fetching all items");
        return containerRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public ItemContainer get(Long id) {
        log.info("Fetching chosen Container by id: {}", id);
        return containerRepo.findById(id).get();
    }

    @Override
    public ItemContainer update(ItemContainer container) {
        log.info("Updating container: {}", container.getName());
        return containerRepo.save(container);

    }

    @Override
    public Boolean delete(Long id) {
        Collection<Item> containerItems = itemService.getItemFromContainer(id);
        log.info("Empty? " + containerItems.isEmpty());
        if (containerItems.isEmpty()){
            log.info("Deleting container by ID: {}", id);
            containerRepo.deleteById(id);
            return Boolean.TRUE;
        }
        else {
            throw new ContainerNotEmptyException(id);
        }


    }
}
