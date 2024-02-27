package de.lagerverwaltung.software.service;

import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemContainer;

import java.util.Collection;

public interface ItemContainerService {

    ItemContainer create(ItemContainer container);

    Collection<ItemContainer> list(int limit);
    ItemContainer get(Long id);
    ItemContainer update(ItemContainer container);
    Boolean delete(Long id);
}
