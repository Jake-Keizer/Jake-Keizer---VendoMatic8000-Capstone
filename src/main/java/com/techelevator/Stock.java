package com.techelevator;

import java.util.HashMap;
import java.util.Map;

public class Stock {

    private Map<String, Item> stockMap = new HashMap<>();

    public void restockMap(String location, String name, double price, String itemType){
        if (itemType.equals("Chip")){
            Item item = new Chips(name,price);
            stockMap.put(location, item);

        } else if (itemType.equals("Candy")){
            Item item = new Candy(name, price);
            stockMap.put(location, item);

        } else if (itemType.equals("Gum")){
            Item item = new Gum(name, price);
            stockMap.put(location, item);

        } else if (itemType.equals("Drink")){
            Item item = new Drink(name, price);
            stockMap.put(location, item);
        }
    }

    public Map<String, Item> getStockMap() {
        return stockMap;
    }
//restock( )  -- reads CSV again, fills up all items to max (5)


}

