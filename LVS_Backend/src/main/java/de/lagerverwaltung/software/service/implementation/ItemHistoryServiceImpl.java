package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemHistory;
import de.lagerverwaltung.software.repository.ItemHistoryRepo;
import de.lagerverwaltung.software.service.ItemHistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
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
                item.getId(),
                LocalDateTime.now(),
                item.getName(),
                item.getPrice(),
                item.getSpace(),
                item.getCategory().getName(),
                sold,
                item.getCategory(),
                item.getContainer()
        );
        return itemHistoryRepo.save(newHistory);
    }


    //Done
    @Override
    public Collection<ItemHistory> getHistoryFromContainer(Long containerID) {
        log.info("Fetching ItemHistory from container " + containerID );
        Collection<ItemHistory> result = itemHistoryRepo.findByContainer_Id(containerID);
        log.info(result.toString());
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

    //Done
    @Override
    public Collection<ItemHistory> getCategoryHistory(String categoryName) {
        Collection<ItemHistory> result = itemHistoryRepo.findByItemCategoryName(categoryName);
        log.info(result.toString());
        return result;
    }

    @Override
    public Collection<ItemHistory> getCategoryHistory(String categoryName, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo
                .findByItemCategoryNameAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
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
    public int countAllImportedItems(String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.countAllImportedItems(startTime, endTime);
    }

    @Override
    public int countImportedItemsPerContainer(Long containerID) {
        return itemHistoryRepo.countAllImportedItemsPerContainer(containerID);
    }

    @Override
    public int countImportedItemsPerContainer(Long containerID, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.countAllImportedItemsPerContainer(containerID, startTime, endTime);
    }

    @Override
    public int countImportedItemsPerCategory(String categoryName) {
        return itemHistoryRepo.countAllImportedItemsPerCategory(categoryName);
    }

    @Override
    public int countImportedItemsPerCategory(String categoryName, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.countAllImportedItemsPerCategory(categoryName, startTime, endTime);
    }

    @Override
    public int countAllExportedItems() {
        return itemHistoryRepo.countAllBySoldIsTrue();
    }

    @Override
    public int countAllExportedItems(String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.countByTimestampGreaterThanEqualAndTimestampLessThanEqualAndSoldIsTrue(startTime, endTime);
    }

    @Override
    public int countExportedItemsPerContainer(Long containerID) {
        return itemHistoryRepo.countByContainer_IdAndSoldIsTrue(containerID);
    }

    @Override
    public int countExportedItemsPerContainer(Long containerID, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo
                .countByContainer_IdAndSoldIsTrueAndTimestampGreaterThanEqualAndTimestampLessThanEqual
                        (
                                containerID,
                                startTime,
                                endTime
                        );
    }

    @Override
    public int countExportedItemsPerCategory(String categoryName) {
        return itemHistoryRepo.countByItemCategoryAndSoldIsTrue(categoryName);
    }

    @Override
    public int countExportedItemsPerCategory(String categoryName, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo
                .countByItemCategoryAndSoldIsTrueAndTimestampGreaterThanEqualAndTimestampLessThanEqual
                        (
                                categoryName,
                                startTime,
                                endTime
                        );
    }

    @Override
    public double getExportValue() {
        return itemHistoryRepo.getSumExports();
    }

    @Override
    public double getExportValue(String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.getSumExportsByTime(startTime, endTime);
    }

    @Override
    public double getExportValuePerCategory(String categoryName) {
        return itemHistoryRepo.getExportValueByCategory(categoryName);
    }

    @Override
    public double getExportValuePerCategory(String categoryName, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.getExportValuePerCategory(categoryName, startTime, endTime);
    }

    @Override
    public double getImportValue() {
        return itemHistoryRepo.getSumImports();
    }

    @Override
    public double getImportValue(String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.getSumImportsByTime(startTime, endTime);
    }

    @Override
    public double getImportValuePerCategory(String categoryName) {
        return itemHistoryRepo.getImportValueByCategory(categoryName);
    }

    @Override
    public double getImportValuePerCategory(String categoryName, String timeFrom, String timeTill) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTime = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime endTime = LocalDateTime.parse(timeTill, formatter);
        return itemHistoryRepo.getImportValuePerCategory(categoryName, startTime, endTime);
    }




}
