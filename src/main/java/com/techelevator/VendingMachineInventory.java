package com.techelevator;

import java.util.HashMap;
import java.util.Map;

// This was given by James because static maps aren't the industry standard
// This is called the singleton design pattern
public class VendingMachineInventory {
    // VendingMachineInventory.INSTANCE.getInventoryMap();
    private final Map<String, Item> inventoryMap = new HashMap<>();
    public static VendingMachineInventory INSTANCE = new VendingMachineInventory();

    private VendingMachineInventory(){}

    public Map<String, Item> getInventoryMap() {
        return inventoryMap;
    }


}
