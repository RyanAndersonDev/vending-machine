package com.techelevator;

import java.util.HashMap;
import java.util.Map;

// This was given by James because static maps aren't the industry standard
// This is called the singleton design pattern
public class VendingMachineInventory {
    private final Map<String, Item> inventoryMap = new HashMap<>();
    public static VendingMachineInventory INSTANCE = new VendingMachineInventory();

    private VendingMachineInventory(){}

    public Map<String, Item> getInventoryMap() {
        return inventoryMap;
    }

    public String toString() {
        String output = "";
        for(Item item : inventoryMap.values()) {

            output += "[" + item.getId() + "] " + item.getName() + ": $" + item.getPrice() + "\n";
            if (item.getStock() > 0) {
                output += "(Quantity: " + item.getStock() + ")\n\n";
            } else {
                output += "Out of stock.\n\n";
            }
        }
        return output;
    }


}
