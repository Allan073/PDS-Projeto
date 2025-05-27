package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateItemDto;
import br.ufrn.imd.cbm.models.Item;
import br.ufrn.imd.cbm.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public void createItem(CreateItemDto createItemDto) {
        Item newItem = Item.builder()
                .name(createItemDto.name())
                .description(createItemDto.description())
                .quantity(createItemDto.quantity())
                .price(createItemDto.price())
                .build();

        itemRepository.save(newItem);
    }

    public void updateItemById(Long id, CreateItemDto createItemDto) {
        Item item = findItemById(id);
        if (createItemDto.name() != null) item.setName(createItemDto.name());
        if (createItemDto.description() != null) item.setDescription(createItemDto.description());
        if (createItemDto.price() != null) item.setPrice(createItemDto.price());
        if (createItemDto.quantity() != null) item.setQuantity(createItemDto.quantity());
        itemRepository.save(item);
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

}
