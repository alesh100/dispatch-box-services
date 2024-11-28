package com.alesh.Dispatch.service;

import com.alesh.Dispatch.model.Box;
import com.alesh.Dispatch.model.Item;
import com.alesh.Dispatch.model.State;
import com.alesh.Dispatch.repository.BoxRepository;
import com.alesh.Dispatch.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public BoxService(BoxRepository boxRepository, ItemRepository itemRepository) {
        this.boxRepository = boxRepository;
        this.itemRepository = itemRepository;
    }

    // Service for creating a box
    public Box createBox(Box box) {
        return boxRepository.save(box);
    }

    // Service to load items into a box
    public List<Item> loadItems(String txref, List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        Optional<Box> optionalBox = boxRepository.findById(txref);
        if (optionalBox.isEmpty()) {
            return null;
        }

        Box box = optionalBox.get();

        double totalWeight = items.stream().mapToDouble(Item::getWeight).sum();
        double currentWeight = box.getItems().stream().mapToDouble(Item::getWeight).sum();

        if (totalWeight + currentWeight > box.getWeightLimit()) {
            return null;
        }

        if (box.getBatteryCapacity() < 25) {
            return null;
        }

        // Assign box to items
        for (Item item : items) {
            item.setBox(box);
        }

        // Save items
        itemRepository.saveAll(items);

        box.setState(State.LOADED);
        boxRepository.save(box);

        return items;
    }

    // Service to get loaded items for a box
    public List<Item> getLoadedItems(String txref) {
        return itemRepository.findByBoxTxref(txref);
    }

    // Service to get available boxes (IDLE state)
    public List<Box> getAvailableBoxes() {
        return boxRepository.findByState(State.IDLE);
    }

    // Service to get the battery level of a box
    public int getBatteryLevel(String txref) {
        Optional<Box> optionalBox = boxRepository.findById(txref);
        if (optionalBox.isEmpty()) {
            return -1;
        }
        Box box = optionalBox.get();
        return box.getBatteryCapacity();
    }
}
