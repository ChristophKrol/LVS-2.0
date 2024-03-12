package de.lagerverwaltung.software.service;

import de.lagerverwaltung.software.dto.ItemDTO;
import de.lagerverwaltung.software.enumeration.Category;
import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemCategory;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    Item create(Item item);
    Collection<ItemDTO> list(int limit);
    Collection<ItemDTO> listByCategory(Long categoryID);
    ItemDTO get(Long id);
    Item update(Item item);
    Boolean delete(Long id);



}
