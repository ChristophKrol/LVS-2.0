package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemHistory;
import de.lagerverwaltung.software.repository.ItemHistoryRepo;
import de.lagerverwaltung.software.service.ItemHistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ItemHistoryServiceImpl implements ItemHistoryService {
    private final ItemHistoryRepo itemHistoryRepo;

    @Override
    public ItemHistory create(ItemHistory itemHistory) {
        log.info("Creating ItemHistory entity");
        return itemHistoryRepo.save(itemHistory);
    }

    //DONE
    @Override
    public ItemHistory create(Item item, boolean sold) {
        ItemHistory newHistory = new ItemHistory(
                LocalDateTime.now(),
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getSpace(),
                item.getCategory().getName(),
                sold,
                item.getContainer()
        );
        return itemHistoryRepo.save(newHistory);
    }


    //Done
    @Override
    public Collection<ItemHistory> getHistoryFromContainer(Long containerID) {
        log.info("Fetching ItemHistory from container " + containerID );
        return itemHistoryRepo.findByContainer_Id(containerID);
    }

    //Done
    @Override
    public Collection<ItemHistory> getHistoryFromContainer(Long containerID, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        log.info("fetching ItemHistory from container " + containerID );
        return itemHistoryRepo
                .findByContainer_IdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
                        containerID,
                        startTime,
                        endTime
                );
    }

    //Done
    @Override
    public Collection<ItemHistory> getAllHistory() {
        return itemHistoryRepo.findAll();
    }
    //DONE
    @Override
    public Collection<ItemHistory> getAllHistory(String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo
                .findAllByTimestampGreaterThanEqualAndAndTimestampLessThanEqual(startTime, endTime);
    }


    @Override
    public Collection<ItemHistory> getCategoryHistory(String categoryName) {
        return itemHistoryRepo.getCategoryHistory(categoryName);
    }

    @Override
    public Collection<ItemHistory> getCategoryHistory(String categoryName, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo
                .findByItemCategoryAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
                        categoryName,
                        startTime,
                        endTime
                );
    }

    @Override
    public int countAllImportedItems() {
        return itemHistoryRepo.countAllImportedItems();
    }

    @Override
    public int countAllImportedItems(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.countAllImportedItems(timeFrom, timeTill);
    }

    @Override
    public int countImportedItemsPerContainer(Long containerID) {
        return itemHistoryRepo.countAllImportedItemsPerContainer(containerID);
    }

    @Override
    public int countImportedItemsPerContainer(Long containerID, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.countAllImportedItemsPerContainer(containerID, timeFrom, timeTill);
    }

    @Override
    public int countImportedItemsPerCategory(String categoryName) {
        return itemHistoryRepo.countAllImportedItemsPerCategory(categoryName);
    }

    @Override
    public int countImportedItemsPerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.countAllImportedItemsPerCategory(categoryName, timeFrom, timeTill);
    }

    @Override
    public int countAllExportedItems() {
        return itemHistoryRepo.countAllBySoldIsTrue();
    }

    @Override
    public int countAllExportedItems(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.countByTimestampGreaterThanEqualAndTimestampLessThanEqualAndSoldIsTrue(timeFrom, timeTill);
    }

    @Override
    public int countExportedItemsPerContainer(Long containerID) {
        return itemHistoryRepo.countByContainer_IdAndSoldIsTrue(containerID);
    }

    @Override
    public int countExportedItemsPerContainer(Long containerID, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo
                .countByContainer_IdAndSoldIsTrueAndTimestampGreaterThanEqualAndTimestampLessThanEqual
                        (
                                containerID,
                                timeFrom,
                                timeTill
                        );
    }

    @Override
    public int countExportedItemsPerCategory(String categoryName) {
        return itemHistoryRepo.countByItemCategoryAndSoldIsTrue(categoryName);
    }

    @Override
    public int countExportedItemsPerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo
                .countByItemCategoryAndSoldIsTrueAndTimestampGreaterThanEqualAndTimestampLessThanEqual
                        (
                                categoryName,
                                timeFrom,
                                timeTill
                        );
    }

    @Override
    public double getExportValue() {
        return itemHistoryRepo.getSumExports();
    }

    @Override
    public double getExportValue(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.getSumExportsByTime(timeFrom, timeTill);
    }

    @Override
    public double getExportValuePerCategory(String categoryName) {
        return itemHistoryRepo.getExportValueByCategory(categoryName);
    }

    @Override
    public double getExportValuePerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.getExportValuePerCategory(categoryName, timeFrom, timeTill);
    }

    @Override
    public double getImportValue() {
        return itemHistoryRepo.getSumImports();
    }

    @Override
    public double getImportValue(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.getSumImportsByTime(timeFrom, timeTill);
    }

    @Override
    public double getImportValuePerCategory(String categoryName) {
        return itemHistoryRepo.getImportValueByCategory(categoryName);
    }

    @Override
    public double getImportValuePerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return itemHistoryRepo.getImportValuePerCategory(categoryName, timeFrom, timeTill);
    }
}
