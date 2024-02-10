package de.lagerverwaltung.software.resource;


import de.lagerverwaltung.software.model.Item;
import de.lagerverwaltung.software.model.ItemCategory;
import de.lagerverwaltung.software.model.ItemContainer;
import de.lagerverwaltung.software.model.Response;
import de.lagerverwaltung.software.repository.CategoryRepo;
import de.lagerverwaltung.software.repository.ItemRepo;
import de.lagerverwaltung.software.service.ItemCategoryService;
import de.lagerverwaltung.software.service.implementation.ItemCategoryImpl;
import de.lagerverwaltung.software.service.implementation.ItemContainerImpl;
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
                        .data(Map.of("items", itemService.getFromContainer(id)))
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
                        .data(Map.of("deleted", itemService.delete(id)))
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
                        .data(Map.of("items", containerService.list(30)))
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
                        .data(Map.of("items", containerService.get(id)))
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
                        .data(Map.of("deleted", containerService.delete(id)))
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





}
