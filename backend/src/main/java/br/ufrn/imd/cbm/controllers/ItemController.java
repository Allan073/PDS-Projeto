package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.annotations.AdminOnly;
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

    @AdminOnly
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.findItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
