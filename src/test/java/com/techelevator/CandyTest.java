package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CandyTest {
    @Test
    public void Decrease_Count_Decreases_Count (){
        int currentCount = 5;
        Drink drink = new Drink("chonka bar", 2.15);
        drink.decreaseCount();
        Assert.assertEquals(4, drink.getCurrentCount());

    }

    @Test
    public void ToString_Returns_Correct_String() {
        Drink drink = new Drink("chonka bar", 2.15);
        String expected = "chonka bar: $2.15, Qty: 5";
        Assert.assertEquals(expected, drink.toString());
    }



}