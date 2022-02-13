package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GumTest {


    @Test
    public void Decrease_Count_Decreases_Count (){
        int currentCount = 5;
        Drink drink = new Drink("acacia gum", 1.35);
        drink.decreaseCount();
        Assert.assertEquals(4, drink.getCurrentCount());

    }

    @Test
    public void ToString_Returns_Correct_String() {
        Drink drink = new Drink("acacia gum", 1.35);
        String expected = "acacia gum: $1.35, Qty: 5";
        Assert.assertEquals(expected, drink.toString());
    }

}