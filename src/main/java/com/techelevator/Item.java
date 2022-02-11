package com.techelevator;

public abstract class Item {
    //fields
    //String name (wonka bar, etc)
    //Double price
    //currentCountofItems (how many items are in the slot)
    private int currentCount = 5;
    private String name;
    private double price;


    public Item (String name, double price){
        this.name = name;
        this.price = price;
    }

    public int getCurrentCount() {
        return currentCount;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void decreaseCount(){
        if (currentCount == 0){
            System.out.println("SOLD OUT");
        }
        currentCount--;
    }

    @Override
    public String toString() {
        String printCurrentCount = String.valueOf(getCurrentCount());
        if (getCurrentCount() == 0){
            printCurrentCount = "SOLD OUT";
        }
        return getName() + ":  $" +getPrice() +  ", Qty: "  + printCurrentCount;
    }
//methods
    //printMessage (toString) - Abstract method - force it to be definited in chip, candy, etc. -
    //          when the item is vended "Crunch Crunch, Yum!" when in Chip class
    //vendItem( ) method to subtract from current count when the user completes purchase
    //              -- include logic to prevent negative count

    public abstract String printMessage();








}
