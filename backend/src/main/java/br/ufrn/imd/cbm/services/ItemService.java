package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateItemDto;
import br.ufrn.imd.cbm.exceptions.InvalidArgumentException;
import br.ufrn.imd.cbm.exceptions.NotFoundException;
import br.ufrn.imd.cbm.models.Item;
import br.ufrn.imd.cbm.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public void createItem(CreateItemDto createItemDto) throws InvalidArgumentException {
        if (createItemDto.name() == null || createItemDto.name().isEmpty()) {
            throw new InvalidArgumentException("Nome de item vazio!");
        }
        if (createItemDto.quantity() == null || createItemDto.quantity() < 0) {
            throw new InvalidArgumentException("Quantidade do item vazio ou inválido!");
        }
        if (createItemDto.price() == null || createItemDto.price() <= 0) {
            throw new InvalidArgumentException("Preço do item vazio ou inválido!");
        }
        Item newItem = Item.builder()
                .name(createItemDto.name())
                .description(createItemDto.description())
                .quantity(createItemDto.quantity())
                .price(createItemDto.price())
                .build();
        itemRepository.save(newItem);
    }

    public void updateItemById(Long id, CreateItemDto createItemDto) throws NotFoundException {
        try {
            Item item = findItemById(id);
            if (createItemDto.name() != null) item.setName(createItemDto.name());
            if (createItemDto.description() != null) item.setDescription(createItemDto.description());
            if (createItemDto.price() != null) item.setPrice(createItemDto.price());
            if (createItemDto.quantity() != null) item.setQuantity(createItemDto.quantity());
            if (createItemDto.orderable() != null) item.setOrderable(createItemDto.orderable());
            itemRepository.save(item);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }

    public Item findItemById(Long id) throws NotFoundException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));
    }
    public Item findItemByName(String name) throws NotFoundException{
        return itemRepository.findByName(name).orElseThrow(() -> new NotFoundException("Item " + name + " não encontrado"));
    }

    public void deleteItemById(Long id) throws NotFoundException {
        try {
            Item item = findItemById(id);
            itemRepository.delete(item);
        } catch (NotFoundException e) {
            throw e;
        }
    }

    public List<Item> findAllInList(List<String> names) throws NotFoundException {
        ArrayList<Item> items = new ArrayList<>();
        for (String name : names) {
            items.add(findItemByName(name));
        }
        if (items.isEmpty()) {
            throw new NotFoundException("Nenhum item encontrado!");
        }
        return items;
    }


    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }
    public List<Item> findOrderable() {
        return itemRepository.findByOrderable(Boolean.TRUE);
    }

    public List<Item> findAllById(List<Long> ids) throws NotFoundException {
        List<Item> items = itemRepository.findAllById(ids);
        if (items.isEmpty()) {
            throw new NotFoundException("Lista de itens vazia!");
        }
        return items;
    }

}
