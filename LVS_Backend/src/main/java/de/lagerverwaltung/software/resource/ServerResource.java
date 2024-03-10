package de.lagerverwaltung.software.resource;


import de.lagerverwaltung.software.model.*;
import de.lagerverwaltung.software.repository.CategoryRepo;
import de.lagerverwaltung.software.repository.ItemRepo;
import de.lagerverwaltung.software.service.ItemCategoryService;
import de.lagerverwaltung.software.service.implementation.ItemCategoryImpl;
import de.lagerverwaltung.software.service.implementation.ItemContainerImpl;
import de.lagerverwaltung.software.service.implementation.ItemHistoryServiceImpl;
import de.lagerverwaltung.software.service.implementation.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;


/**
 * Erstellung der Response Objekte und deren URLs
 * Definition möglicher Abfragen
 */
@RestController
@RequestMapping(value = "/server")
@RequiredArgsConstructor
public class ServerResource {
    private final ItemServiceImpl itemService;
    private final ItemRepo itemRepository;

    private final CategoryRepo categoryRepo;
    private final ItemCategoryImpl categoryService;

    private final ItemContainerImpl containerService;
    private final ItemHistoryServiceImpl itemHistoryService;

    /**
     * ITEM
     * Baut das Response-Objekt fuer Get-Request: mehrere Items
     * @return Response
     */
    @GetMapping("/item/list")
    public ResponseEntity<Response> getItems(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("items", itemService.list(30)))
                        .message("Items retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


  @GetMapping("/item/get/category/{category}")
  public ResponseEntity<Response> getItemsByCategory(@PathVariable("category")Long category_id){
      return ResponseEntity.ok(
              Response.builder().timestamp(LocalDateTime.now())
                      .data(Map.of("items", itemService.listByCategory(category_id)))
                      .message("Items of chosen category retrieved")
                      .status(OK)
                      .statusCode(OK.value())
                      .build()
      );
  }

    @GetMapping("/item/test")
    public ResponseEntity<Response> testestetstesttetst(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("catGroup", this.itemRepository.groupByCategory()))
                        .message("Items of chosen category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/item/test2")
    public ResponseEntity<Response> testestetstesttetsts(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("catPriceSum", this.itemRepository.groupedByPriceSumsByCategory()))
                        .message("Items of chosen category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    /**
     * Baue das Response-Objekt für Get-Request: ein Item
     * @param id ID des Items
     * @return Response
     */
    @GetMapping("/item/get/{id}")
    public ResponseEntity<Response> getItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("items", itemService.get(id)))
                        .message("Item retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/item/get/container/{containerID}")
    public ResponseEntity<Response> getItemFromContainer(@PathVariable("containerID") Long id){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("container", itemService.getFromContainer(id)))
                        .message("Items retrieved from Container " + id)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/item/get/container/{containerID}/category/{categoryID}")
    public ResponseEntity<Response> getItemFromContainerGroupByCategory(@PathVariable("containerID") Long id, @PathVariable("categoryID") long cat_id){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("items", itemService.getFromContainerGroupByCategory(id, cat_id)))
                        .message("Items retrieved from Container " + id)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }



    /**
     * Baue das Response-Objekt fuer Post-Request: Item speichern
     * @param item zu speicherndes Item
     * @return Response
     */
    @PostMapping("/item/save")
    public ResponseEntity<Response> saveItem(@RequestBody Item item) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("item", itemService.create(item)))
                        .message("Item created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

//   @PostMapping ("container/{id}/addItem")
//   public ResponseEntity<Response> addItemToContainer(@PathVariable("id") Long containerID, @RequestBody Item item){
//       return ResponseEntity.ok(
//               Response.builder()
//                       .timestamp(LocalDateTime.now())
//                       .data(Map.of("container", itemService.create(item)))
//                       .message("Item created in Container: " + containerID)
//                       .status(CREATED)
//                       .statusCode(CREATED.value())
//                       .build()

//       );
//   }

    /**
     * Baue das Response-Objekt für Delete-Request: Item loeschen
     * @param id ID des Items
     * @return Response
     */
    @DeleteMapping("/item/delete/{id}")
    public ResponseEntity<Response> deleteItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("items_deleted", itemService.delete(id)))
                        .message("Item deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    //Test Request
    @GetMapping("/test")
    public ResponseEntity<String> testRequest(){
        String response = "Hello";
        return new ResponseEntity<>(OK);
    }

    //@GetMapping(path="/image/{filename}", produces = IMAGE_PNG_VALUE) // Erstellt PNG-Werte, keine JSONs
    //public byte[] getCategoryImage(@PathVariable("filename") String filename) throws IOException {
    //   return Files.readAllBytes(Paths.get("src", "main", "java", "de"
    //           , "lagerverwaltung", "software", "images", filename));
    //}
//

    @PostMapping("/container/save")
    public ResponseEntity<Response> saveContainer(@RequestBody ItemContainer container) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("container", containerService.create(container)))
                        .message("Container created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/container/list")
    public ResponseEntity<Response> getContainers(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("containers", containerService.list(30)))
                        .message("Containers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/container/get/{id}")
    public ResponseEntity<Response> getContainer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("container", containerService.get(id)))
                        .message("Container retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/container/delete/{id}")
    public ResponseEntity<Response> deleteContainer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("container_deleted", containerService.delete(id)))
                        .message("Container deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/category/save")
    public ResponseEntity<Response> saveCategory(@RequestBody ItemCategory category) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("category", categoryService.create(category)))
                        .message("Category created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/category/get/{name}")
    public ResponseEntity<Response> getCategory(@PathVariable("name") String categoryName) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("categories", categoryService.get(categoryName)))
                        .message("Category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/category/list")
    public ResponseEntity<Response> getCategories(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("categories", categoryService.list(30)))
                        .message("Categories retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    /**
     * KPI-Requests
     */
    @GetMapping("/item/totalValue")
    public ResponseEntity<Response> getTotalItemValue(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("itemValue", itemService.calculateItemValue()))
                        .message("Total Item Value retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    /**
     *
     * ItemHistory
     *
     */

    @PostMapping("/itemhistory/save")
    public ResponseEntity<Response> saveItemHistory(@RequestBody ItemHistory itemHistory){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("itemHistory", itemHistoryService.create(itemHistory)))
                        .message("ItemHistory-Entity created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/itemhistory/list")
    public ResponseEntity<Response> getItemHistories(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("itemHistory", itemHistoryService.getAllHistory()))
                        .message("ItemHistory retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/itemhistory/list/{startTime}/{endTime}")
    public ResponseEntity<Response> getItemHistories(@PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("itemHistory", itemHistoryService.getAllHistory(startTime, endTime)))
                        .message("ItemHistory retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/container/{containerID}")
    public ResponseEntity<Response> getItemHistoryFromContainer(@PathVariable("containerID") Long containerID){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of("itemHistory_container", itemHistoryService.getHistoryFromContainer(containerID)))
                        .message("ItemHistory from Container: " + containerID + " retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/container/{containerID}/{startTime}/{endTime}")
    public ResponseEntity<Response> getItemHistoryFromContainer(
            @PathVariable("containerID") Long containerID,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
            ){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "itemHistoryByContainer",
                                itemHistoryService.getHistoryFromContainer(containerID, startTime, endTime)

                        ))
                        .message("ItemHistory from Container: " + containerID + " retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/category/{categoryName}")
    public ResponseEntity<Response> getItemHistoryByCategory(@PathVariable("categoryName") String categoryName){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "itemHistoryByCategory",
                                itemHistoryService.getCategoryHistory(categoryName)))
                        .message("ItemHistory from Category: " + categoryName + " retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }


    @GetMapping("itemhistory/category/{categoryName}/{startTime}/{endTime}")
    public ResponseEntity<Response> getItemHistoryByCategory(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "itemHistoryByCategory",
                                itemHistoryService.getCategoryHistory(categoryName, startTime, endTime)))
                        .message("ItemHistory from Category: " + categoryName + " retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

    @GetMapping("itemhistory/getImports")
    public ResponseEntity<Response> countImportedItems(){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "ImportsTotal",
                                itemHistoryService.countAllImportedItems()))
                        .message("Sum of imported Items retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }
    @GetMapping("itemhistory/getImports/{startTime}/{endTime}")
    public ResponseEntity<Response> countImportedItems(
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getImportsByTime",
                                itemHistoryService.countAllImportedItems(startTime, endTime)))
                        .message("Sum of imported Items retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

    @GetMapping("itemhistory/getImports/container/{containerID}")
    public ResponseEntity<Response> countImportedItemsByContainer(@PathVariable("containerID") Long containerID){
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getImportsByContainer",
                                itemHistoryService.countImportedItemsPerContainer(containerID)))
                        .message("Sum of imported Items retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }



    @GetMapping("itemhistory/getImports/container/{containerID}/{startTime}/{endTime}")
    public ResponseEntity<Response> countImportedItemsPerContainer(
            @PathVariable("containerID") Long containerID,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countImportedItemsPerContainer",
                                itemHistoryService.countImportedItemsPerContainer(containerID, startTime, endTime)))
                        .message("Count of imported items per container retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getImports/category/{categoryName}")
    public ResponseEntity<Response> countImportedItemsPerCategory(
            @PathVariable("categoryName") String categoryName
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countImportedItemsPerCategory",
                                itemHistoryService.countImportedItemsPerCategory(categoryName)))
                        .message("Count of imported items per category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getImports/category/{categoryName}/{startTime}/{endTime}")
    public ResponseEntity<Response> countImportedItemsPerCategory(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countImportedItemsPerCategory",
                                itemHistoryService.countImportedItemsPerCategory(categoryName, startTime, endTime)))
                        .message("Count of imported items per category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExports")
    public ResponseEntity<Response> countAllExportedItems() {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countAllExportedItems",
                                itemHistoryService.countAllExportedItems()))
                        .message("Count of all exported items retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExports/{startTime}/{endTime}")
    public ResponseEntity<Response> countAllExportedItems(
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countAllExportedItems",
                                itemHistoryService.countAllExportedItems(startTime, endTime)))
                        .message("Count of all exported items retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExports/container/{containerID}")
    public ResponseEntity<Response> countExportedItemsPerContainer(
            @PathVariable("containerID") Long containerID
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countExportedItemsPerContainer",
                                itemHistoryService.countExportedItemsPerContainer(containerID)))
                        .message("Count of exported items per container retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExports/container/{containerID}/{startTime}/{endTime}")
    public ResponseEntity<Response> countExportedItemsPerContainer(
            @PathVariable("containerID") Long containerID,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countExportedItemsPerContainer",
                                itemHistoryService.countExportedItemsPerContainer(containerID, startTime, endTime)))
                        .message("Count of exported items per container retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExports/category/{categoryName}")
    public ResponseEntity<Response> countExportedItemsPerCategory(
            @PathVariable("categoryName") String categoryName
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countExportedItemsPerCategory",
                                itemHistoryService.countExportedItemsPerCategory(categoryName)))
                        .message("Count of exported items per category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExports/category/{categoryName}/{startTime}/{endTime}")
    public ResponseEntity<Response> countExportedItemsPerCategory(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "countExportedItemsPerCategory",
                                itemHistoryService.countExportedItemsPerCategory(categoryName, startTime, endTime)))
                        .message("Count of exported items per category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExportValue")
    public ResponseEntity<Response> getExportValue() {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getExportValue",
                                itemHistoryService.getExportValue()))
                        .message("Export value retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExportValue/{startTime}/{endTime}")
    public ResponseEntity<Response> getExportValue(
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getExportValue",
                                itemHistoryService.getExportValue(startTime, endTime)))
                        .message("Export value retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExportValue/category/{categoryName}")
    public ResponseEntity<Response> getExportValuePerCategory(
            @PathVariable("categoryName") String categoryName
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getExportValuePerCategory",
                                itemHistoryService.getExportValuePerCategory(categoryName)))
                        .message("Export value per category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getExportValue/category/{categoryName}/{startTime}/{endTime}")
    public ResponseEntity<Response> getExportValuePerCategory(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getExportValuePerCategory",
                                itemHistoryService.getExportValuePerCategory(categoryName, startTime, endTime)))
                        .message("Export value per category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @GetMapping("itemhistory/getImportValue")
    public ResponseEntity<Response> getImportValue() {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getImportValue",
                                itemHistoryService.getImportValue()))
                        .message("Total import value retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getImportValue/{startTime}/{endTime}")
    public ResponseEntity<Response> getImportValue(
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getImportValueByTime",
                                itemHistoryService.getImportValue(startTime, endTime)))
                        .message("Import value by time range retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getImportValue/category/{categoryName}")
    public ResponseEntity<Response> getImportValuePerCategory(
            @PathVariable("categoryName") String categoryName
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getImportValuePerCategory",
                                itemHistoryService.getImportValuePerCategory(categoryName)))
                        .message("Import value for a specific category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("itemhistory/getImportValue/category/{categoryName}/{startTime}/{endTime}")
    public ResponseEntity<Response> getImportValuePerCategory(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime
    ) {
        return ResponseEntity.ok(
                Response.builder().timestamp(LocalDateTime.now())
                        .data(Map.of(
                                "getImportValuePerCategoryByTime",
                                itemHistoryService.getImportValuePerCategory(categoryName, startTime, endTime)))
                        .message("Import value for a specific category by time range retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }













}
