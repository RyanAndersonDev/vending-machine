package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Item {

    private String id;

    private String name;
    private BigDecimal price;
    private String type;
    private int stock;
    private final File STOCK_FILE = new File("vendingmachine.csv");

    public Item(String id, String name, BigDecimal price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.stock = 5;
    }
//getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getStock(){
        return stock;
    }


//setter


    //method
    public void createItemsFromFolder(){
        try(Scanner fileInput = new Scanner(STOCK_FILE);){
            //loop
            while(fileInput.hasNextLine()){
                String fileLine = fileInput.nextLine();
                String[] fileLineElements = fileLine.split("|");

                String id = fileLineElements[0];
                String name = fileLineElements[1];
                BigDecimal price = new BigDecimal(fileLineElements[2]);
                String category = fileLineElements[3];

                Item item = new Item(id, name, price, category);

                VendingMachineInventory.INSTANCE.getInventoryMap().put(id, item);
            }
        } catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found");
        }
    }
}
