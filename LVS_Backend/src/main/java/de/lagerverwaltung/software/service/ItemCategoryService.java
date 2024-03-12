package de.lagerverwaltung.software.service;

import de.lagerverwaltung.software.model.ItemCategory;

import java.util.Collection;

public interface ItemCategoryService {

    ItemCategory create(ItemCategory itemCategory);
    Collection<ItemCategory> list(int limit);
    ItemCategory get(String categoryName);
    ItemCategory update(ItemCategory category);
    Boolean delete(String categoryName);
}
