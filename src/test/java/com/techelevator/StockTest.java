package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StockTest {

    Stock sut;

    @Before
    public void freshTest (){
        sut = new Stock();

    }

    @Test
    public void Restock_Method_Restocks_Map_With_Correct_Name(){
        Map<String, Item> testMap = new HashMap<>();
        Item testChip = new Chips("flays", 2.00);
        testMap.put("A1", testChip);
        sut.restockMap("A1", "flays", 2.00, "Chip");
        Assert.assertEquals(sut.getStockMap().get("A1").getName(), testMap.get("A1").getName());
        Assert.assertEquals(sut.getStockMap().get("A1").getPrice(), testMap.get("A1").getPrice(), 0.00);

    }

    @Test
    public void Restock_fails_if_incorrect_input(){
        sut.restockMap("A1", "flays", 2.00, "Beer");
        Assert.assertFalse(sut.getStockMap().containsKey("A1"));
    }

}