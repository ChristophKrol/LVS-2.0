package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.model.ItemCategory;
import de.lagerverwaltung.software.repository.CategoryRepo;
import de.lagerverwaltung.software.service.ItemCategoryService;
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
public class ItemCategoryImpl implements ItemCategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public ItemCategory create(ItemCategory itemCategory) {
        log.info("Saving new Container: {}", itemCategory.getName());
        return categoryRepo.save(itemCategory);
    }

    @Override
    public Collection<ItemCategory> list(int limit) {
        log.info("Fetching all items");
        return categoryRepo.findAll(PageRequest.of(0, limit)).toList();

    }

    @Override
    public ItemCategory get(String categoryName) {
        log.info("Fetching chosen Container by name: {}", categoryName);
        return categoryRepo.getByName(categoryName);
    }

    @Override
    public ItemCategory update(ItemCategory category) {
        log.info("Updating existing category: {}", category.getName());
        return categoryRepo.save(category);
    }

    @Override
    public Boolean delete(String categoryName) {
        log.info("Deleting category by ID: {}", categoryName);
        categoryRepo.deleteByName(categoryName);
        return Boolean.TRUE;
    }
}
