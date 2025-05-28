package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.annotations.AdminOnly;
import br.ufrn.imd.cbm.annotations.AnyAuthed;
import br.ufrn.imd.cbm.dtos.CreateItemDto;
import br.ufrn.imd.cbm.models.Item;
import br.ufrn.imd.cbm.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @AdminOnly
    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody CreateItemDto createItemDto) {
        itemService.createItem(createItemDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AnyAuthed
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.findItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @AdminOnly
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable Long id, @RequestBody CreateItemDto createItemDto) {
        itemService.updateItemById(id, createItemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @AnyAuthed
    @GetMapping("/orderable")
    public ResponseEntity<List<Item>> getOrderableItems() {
        List<Item> items = itemService.findOrderable();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
