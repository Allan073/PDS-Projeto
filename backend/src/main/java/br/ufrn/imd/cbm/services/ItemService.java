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

    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

}
