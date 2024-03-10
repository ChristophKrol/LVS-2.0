package de.lagerverwaltung.software.service;

import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemHistory;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ItemHistoryService {

    ItemHistory create(ItemHistory itemHistory);

    ItemHistory create(Item item, boolean sold);

    Collection<ItemHistory> getHistoryFromContainer(Long containerID);
    Collection<ItemHistory> getHistoryFromContainer(Long containerID, String timeFrom, String timeTill);
    Collection<ItemHistory> getAllHistory();
    Collection<ItemHistory> getAllHistory(String timeFrom, String timeTill);
    Collection<ItemHistory> getCategoryHistory(String categoryName);
    Collection<ItemHistory> getCategoryHistory(String categoryName, String timeFrom, String timeTill);

    //TODO: Anzahl Eingang gesamt + Pro Container
    int countAllImportedItems();
    int countAllImportedItems(String timeFrom, String timeTill);
    int countImportedItemsPerContainer(Long containerID);
    int countImportedItemsPerContainer(Long containerID, String timeFrom, String timeTill);
    int countImportedItemsPerCategory(String categoryName);
    int countImportedItemsPerCategory(String categoryName, String timeFrom, String timeTill);
    //TODO: Anzahl Ausgänge gesamt + Pro Container -- das in Repo implementieren
    int countAllExportedItems();
    int countAllExportedItems(String timeFrom, String timeTill);
    int countExportedItemsPerContainer(Long containerID);
    int countExportedItemsPerContainer(Long containerID, String timeFrom, String timeTill);
    int countExportedItemsPerCategory(String categoryName);
    int countExportedItemsPerCategory(String categoryName, String timeFrom, String timeTill);
    //TODO: Gesamtsumme Verkauf
    double getExportValue();
    double getExportValue(String timeFrom, String timeTill);
    double getExportValuePerCategory(String categoryName);
    double getExportValuePerCategory(String categoryName, String timeFrom, String timeTill);
    //TODO: Gesamtsumme Einkauf
    double getImportValue();
    double getImportValue(String timeFrom, String timeTill);
    double getImportValuePerCategory(String categoryName);
    double getImportValuePerCategory(String categoryName, String timeFrom, String timeTill);


}
