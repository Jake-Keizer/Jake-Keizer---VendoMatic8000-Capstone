package com.techelevator;

public abstract class Item {
    //fields
    //String name (wonka bar, etc)
    //Double price
    //currentCountofItems (how many items are in the slot)
    private int currentCount = 5;


    //getters and setters



    //methods
    //printMessage (toString) - Abstract method - force it to be definited in chip, candy, etc. -
    //          when the item is vended "Crunch Crunch, Yum!" when in Chip class
    //vendItem( ) method to subtract from current count when the user completes purchase
    //              -- include logic to prevent negative count

    public abstract String printMessage();








}
