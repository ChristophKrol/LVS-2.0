package de.lagerverwaltung.software.service.implementation;

import de.lagerverwaltung.software.model.ItemHistory;
import de.lagerverwaltung.software.service.ItemHistoryService;

import java.time.LocalDateTime;
import java.util.Collection;

public class ItemHistoryServiceImpl implements ItemHistoryService {

    @Override
    public ItemHistory create(ItemHistory itemHistory) {
        return null;
    }

    @Override
    public Collection<ItemHistory> getHistoryFromContainer(Long containerID) {
        return null;
    }

    @Override
    public Collection<ItemHistory> getHistoryFromContainer(Long containerID, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return null;
    }

    @Override
    public Collection<ItemHistory> getAllHistory() {
        return null;
    }

    @Override
    public Collection<ItemHistory> getAllHistory(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return null;
    }

    @Override
    public Collection<ItemHistory> getCategoryHistory(String categoryName) {
        return null;
    }

    @Override
    public Collection<ItemHistory> getCategoryHistory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return null;
    }

    @Override
    public int countAllImportedItems() {
        return 0;
    }

    @Override
    public int countAllImportedItems(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public int countImportedItemsPerContainer(Long containerID) {
        return 0;
    }

    @Override
    public int countImportedItemsPerContainer(Long containerID, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public int countImportedItemsPerCategory(String categoryName) {
        return 0;
    }

    @Override
    public int countImportedItemsPerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public int countAllExportedItems() {
        return 0;
    }

    @Override
    public int countAllExportedItems(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public int countExportedItemsPerContainer(Long containerID) {
        return 0;
    }

    @Override
    public int countExportedItemsPerContainer(Long containerID, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public int countExportedItemsPerCategory(String categoryName) {
        return 0;
    }

    @Override
    public int countExportedItemsPerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public double getExportValue() {
        return 0;
    }

    @Override
    public double getExportValue(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public double getExportValuePerCategory(String categoryName) {
        return 0;
    }

    @Override
    public double getExportValuePerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public double getImportValue() {
        return 0;
    }

    @Override
    public double getImportValue(LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }

    @Override
    public double getImportValuePerCategory(String categoryName) {
        return 0;
    }

    @Override
    public double getImportValuePerCategory(String categoryName, LocalDateTime timeFrom, LocalDateTime timeTill) {
        return 0;
    }
}
