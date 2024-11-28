package com.alesh.Dispatch.controller;

import com.alesh.Dispatch.model.Box;
import com.alesh.Dispatch.model.Item;
import com.alesh.Dispatch.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    private final BoxService boxService;

    @Autowired
    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    //For creating box
    @PostMapping
    public Box createBox( @RequestBody Box box) {
        return boxService.createBox(box);
    }

    @PostMapping("/{txref}/load")
    public List<Item> loadItems(@PathVariable String txref, @RequestBody List<Item> items) {
        return boxService.loadItems(txref, items);
    }

    @GetMapping("/{txref}/items")
    public List<Item> getLoadedItems(@PathVariable String txref) {
        return boxService.getLoadedItems(txref);
    }

    @GetMapping("/available")
    public List<Box> getAvailableBoxes() {
        return boxService.getAvailableBoxes();
    }

    @GetMapping("/{txref}/battery")
    public int getBatteryLevel(@PathVariable String txref) {
        return boxService.getBatteryLevel(txref);
    }
}
