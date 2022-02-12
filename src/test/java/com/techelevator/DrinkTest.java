package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrinkTest {

    @Test
    public void Decrease_Count_Decreases_Count (){
        int currentCount = 5;
        Drink drink = new Drink("beer", 2.00);
        drink.decreaseCount();
        Assert.assertEquals(4, drink.getCurrentCount());

    }

    @Test
    public void ToString_Returns_Correct_String() {
        Drink drink = new Drink("beer", 2);
        String expected = "beer: $2.0, Qty: 5";
        Assert.assertEquals(expected, drink.toString());
    }

}