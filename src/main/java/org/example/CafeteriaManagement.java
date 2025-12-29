package org.example;

import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

public class CafeteriaManagement {
    public static void main(String[] args) {
        String complaintFile = "Complaint.txt";
        Scanner input = new Scanner(System.in);

        foodList menu = new foodList();
        foodList requests = new foodList();
        Admin admin = new Admin();
        User user = new User();

        menu.clearMenuFile();
        requests.clearReqFile();
        menu.clearCompFile();

        //populating the menu
        menu.addTail("Risotto", "Italy", "None", 250, 12.9);
        menu.addTail("Adobo", "Philippines", "Soy", 230, 13.9);
        menu.addTail("Biryani", "India", "Dairy", 350, 14.95);
        menu.addTail("Kabsa", "Saudi Arabia", "Nuts", 350, 12.95);

        //populating requests
        requests.addTail("Siopao", "Philippines");
        requests.addTail("Beef Noodle Soup", "Taiwan");
        requests.addTail("Ramen", "Japan");
        requests.addTail("Shawarma", "Turkey");

        // start iterating through the main menu in a loop
        while (true) {
            System.out.println("============================================\n\tCAFETERIA MANAGEMENT SYSTEM\t\n============================================");
            System.out.println("1. Admin \n2. User \n3. Exit");

            try{

                System.out.print(">>> Enter option: ");
                int userType = input.nextInt();

                if (userType == 1) { //Admin
                    // iterate through the admin menu in a loop
                    while (true){ //will keep looping until loop is broken out of
                        System.out.println("=================================");
                        System.out.println("Perform an action: \n1. Add menu item \n2. Delete menu item \n3. Generate Report \n4. Check requests \n5. Search for a particular food \n6. Check the number of foods on the menu \n7. Check complaints \n8. Exit");
                        System.out.println("=================================");
                        System.out.print(">>> Enter option: ");
                        int adminAction = input.nextInt();

                        //add menu item
                        if (adminAction == 1) {
                            System.out.println("Enter item name, origin, allergen, calories, and price");
                            System.out.println("(separate all allergens with commas)");
                            System.out.println("Example: \n>>> Name: Veggie Stir Fry \n>>> Origin: China \n>>> Allergen: Soy, gluten, sesame \n>>> Calories: 230 \n>>> Price: 12.00");

                            System.out.println("==========================================");
                            input.nextLine();   //program skips over the name without it

                            System.out.print(">>> Name: ");
                            String name = input.nextLine();
                            System.out.print(">>> Origin: ");
                            String origin = input.nextLine();
                            System.out.print(">>> Allergen: ");
                            String allergen = input.nextLine();
                            System.out.print(">>> Calories: ");
                            int cals = input.nextInt();
                            System.out.print(">>> Price: ");
                            double price = input.nextDouble();

                            menu.addTail(name, origin, allergen, cals, price);
                        }

                        //delete menu item
                        else if (adminAction == 2){
                            //display all the items for the admin to pick from
                            System.out.println("==========================================");
                            System.out.println("Menu:");
                            menu.displayItems();

                            System.out.println("==========================================");
                            System.out.print("Enter the number of the item you want to delete: ");
                            int itemNum = input.nextInt();

                            if ((itemNum < 1) || (itemNum > menu.length())){
                                System.out.println("Invalid selection.");
                            }
                            else {
                                menu.deleteAtPosition(itemNum);
                                System.out.println("==========================================");
                                System.out.println("Updated menu:");
                                menu.displayItems();  //check if item was deleted by displaying the new menu
                            }
                        }

                        //generate a report
                        else if (adminAction == 3){
                            input.nextLine();
                            System.out.println("==========================================");
                            System.out.println("Generate a report of...\n1. All item names \n2. The countries of origin \n3. All item information ");
                            System.out.print(">>> Enter option: ");
                            int choice = input.nextInt();


                            if (choice == 1){
                                System.out.println("============REPORT============");
                                menu.displayItems();
                            }
                            else if (choice == 2){
                                System.out.println("============REPORT============");
                                menu.displayOrigins();
                            }
                            else if (choice == 3){
                                System.out.println("============REPORT============");
                                menu.displayAllInfo();
                            }
                            else{
                                System.out.println("Invalid input.");
                            }
                        }

                        //check requests and then ask if one is to be approved
                        else if (adminAction == 4){
                            System.out.println("==========================================");
                            System.out.println("Requests:");
                            requests.displayRequests();
                            int length = menu.length();

                            System.out.println("Enter the number of the request you would like to approve");
                            System.out.println("(0 if none)");
                            System.out.println("==========================================");

                            System.out.print(">>> Enter option: ");
                            int choice = input.nextInt();

                            // approve the request
                            if (choice >= 1){
                                input.nextLine();
                                System.out.print("Enter the allergens (comma delimited): ");
                                String allergen = input.nextLine();
                                System.out.print("Enter the calories per serving: ");
                                int calories = input.nextInt();
                                System.out.print("Enter the price: ");
                                double price = input.nextDouble();
                                admin.approveRequest(menu, requests, choice, allergen, calories, price);
                            }
                            else if ((choice < 0) || (choice > length)) {
                                // User inputted an invalid option so we will display the Admin menu again without performing any action.
                                System.out.println("==========================================");
                                System.out.println("Invalid input. Back to Admin menu.");
                            }
                            else {
                                // User does not want to approve a request so we will display the Admin menu again
                                System.out.println("==========================================");
                                System.out.println("Not approving any requests. Back to Admin menu.");
                            }
                        }

                        else if (adminAction == 5){ //search for a particular food
                            input.nextLine();
                            System.out.println("==========================================");
                            System.out.println("Enter the name of food you want to search for: ");
                            String searchItem = input.nextLine();

                            menu.checkAvailability(searchItem);
                        }

                        else if (adminAction == 6){
                            int menuLength = menu.length();
                            System.out.println("==========================================");
                            System.out.println("There are currently " +menuLength+ " items on the menu.");
                        }

                        else if (adminAction == 7){
                            System.out.println("=================================");
                            System.out.println("Complaints received:");
                            try {
                                BufferedReader reader = new BufferedReader(new FileReader(complaintFile));
                                String line;
                                int count = 1;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(count+". "+line);
                                    count++;
                                }
                                reader.close();
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                            }
                        }

                        // Go back to main menu.
                        else if (adminAction == 8){
                            break;
                        }

                        //display an error message for invalid input
                        if ((adminAction < 1) || (adminAction > 8)){
                            System.out.println("Invalid choice. Please select an action from 1 to 8.\n");
                        }

                    }
                }

                else if (userType == 2) { //User
                    // iterate through the user menu in a loop
                    while (true){

                        System.out.println("=================================");
                        System.out.println("Perform an action: \n1. Order two food items \n2. Inquire about a specific item \n3. Send in a complaint \n4. Request an item \n5. Exit");
                        System.out.print("=================================\n>>> Enter option: ");
                        int userAction = input.nextInt();

                        //display an error message for invalid input
                        if ((userAction < 1) || (userAction > 5)){
                            System.out.println("=================================");
                            System.out.println("Invalid choice. Please select an item from 1 to 5.\n");
                        }

                        //ordering two food items
                        else if (userAction == 1){
                            input.nextLine();
                            System.out.println("What would you like to order?\n Menu:");
                            menu.displayAllInfo();
                            System.out.println("==========================================");
                            System.out.print("Enter first item's number: \n>>> Item No. :");
                            foodNode food1= user.getFoodAtIndex(menu, input.nextInt());
                            System.out.print("Enter second item's number: \n>>> Item No. :");
                            foodNode food2= user.getFoodAtIndex(menu, input.nextInt());
                            if(food1!=null && food2!=null){
                                // Display selected items & total price
                                System.out.println("===========RECEIPT============\nYou have selected: \n1. "+food1.name+ ", SAR " + food1.price+"\n2. " + food2.name + ", SAR " + food2.price);
                                System.out.println("---------------------------------\nTotal Price: SAR "+(food1.price + food2.price)+"");
                            }
                            else
                                System.out.println("Invalid food item index. Please try again.");
                        }

                        //inquire about a specific item
                        else if (userAction == 2){
                            input.nextLine();
                            System.out.println("==========================================");
                            System.out.println("Enter the name of food you want to search for: ");
                            String searchItem = input.nextLine();

                            menu.checkAvailability(searchItem);
                        }

                        //send in a complaint
                        else if (userAction == 3){
                            input.nextLine();
                            System.out.println("Please let us know your thoughts. ");
                            System.out.print(">>> Complain here: ");
                            String complaint=input.nextLine();
                            //adding complaint to Complaint file
                            user.addCompFile(complaint);
                            System.out.println("----------------------------");
                            System.out.println("Complaint submitted.");
                        }

                        //request an item to be added to the menu
                        else if (userAction == 4){
                            input.nextLine();
                            System.out.println("Enter the name of the food you would like to request: ");
                            String requestName= input.nextLine();
                            System.out.println("Enter its country of origin: ");
                            String requestOrigin= input.nextLine();
                            //adding request to Requests file
                            requests.addTail(requestName, requestOrigin);
                            System.out.println("---------------------------");
                            System.out.println("Request submitted!");
                        }

                        //exit back to the main menu
                        else if (userAction == 5){
                            break;
                        }
                    }
                }

                else if (userType == 3){
                    break;
                }

            }catch(InputMismatchException e){
                System.out.println("Invalid input entered.");
            }

        }

    }
}


