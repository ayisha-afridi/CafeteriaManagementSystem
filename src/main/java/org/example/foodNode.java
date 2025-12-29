package org.example;

public class foodNode {
    public String name;
    public String origin;
    public String allergen;
    public int calories;
    public double price;
    public foodNode link;

    public foodNode(){
        name="None";
        origin="None";
        allergen="None";
        calories=0;
        price=0;
    }
    public foodNode(String name, String origin, String allergen, int calories, double price){
        this.name=name;
        this.origin=origin;
        this.allergen=allergen;
        this.calories=calories;
        this.price=price;
    }
    public foodNode(String name, String origin){
        /*
        This constructor should be used when users create new requests.
        In the main method, another foodList will be created to contain user requests.
        Each request should be created using this constructor,
        so they will only have name and country in its data,
        until admin adds it and sets other properties (allergens, calories, price).
         */
        this.name=name;
        this.origin=origin;
    }
}
