package org.example;

public class Admin {
    public void approveRequest(foodList menu, foodList requests, int reqPos, String allergen, int calories, double price) { //only to be used in menu list.
        foodNode R_pointer = requests.front;    //pointer for request list
        foodNode M_pointer = menu.front;         //pointer for menu
        int count = 1;

        if (R_pointer.link == null) {   //handles if the request list only has one node
            foodNode newnode = new foodNode(R_pointer.name, R_pointer.origin, allergen, calories, price);
            while (M_pointer.link != null) {    //traverse to the end of menu list
                M_pointer = M_pointer.link;
            }
            M_pointer.link = newnode; //after the properties are added, they can be added to the menu list
            menu.addMenuFile(newnode); //write new node to menu file
        }

        else {
            while (count != reqPos) {   //traverses the request list to find the specified node
                R_pointer = R_pointer.link;
                count++;
            }
            foodNode newnode = new foodNode(R_pointer.name, R_pointer.origin, allergen, calories, price);
            while (M_pointer.link != null) {    //traverse to the end of menu list
                M_pointer = M_pointer.link;
            }
            M_pointer.link = newnode; //after the properties are added, they can be added to the menu list
            menu.addMenuFile(newnode); //write new node to menu file
        }
        requests.reqDeleteAtPosition(reqPos); //remove the food from request file now that it is in the menu
    }
}
