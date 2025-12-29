package org.example;

import java.io.*;

public class foodList {
    public String menuFile = "Menu.txt";
    public String requestFile = "Request.txt";
    public String complaintFile = "Complaint.txt";
    public foodNode front; //head, public to be accessed by other classes (Admin, User)

    //method that adds a node to the end of the list
    public foodNode addTail(String name, String origin, String allergen, int calories, double price){
        foodNode newNode = new foodNode(name, origin, allergen, calories, price);   //creating a new node to add to the list
        foodNode cur = front;

        if (front == null){
            front = newNode;        //if we want to add a node but there is no existing head, we create a head
            addMenuFile(newNode);
            return newNode;
        }

        while (cur.link != null){   //until we reach the end of the list keep traversing through it
            cur = cur.link;         //move the pointer "current" forward
        }
        cur.link = newNode;         //we link the last node to the new node we created
        addMenuFile(newNode);
        return newNode;
    }

    //method that adds a user request into the request file
    public foodNode addTail(String name, String origin){
        foodNode newNode = new foodNode(name, origin);
        foodNode cur = front;

        if (front == null){
            front = newNode;
            addReqFile(newNode);
            return newNode;
        }

        while (cur.link != null){
            cur = cur.link;
        }
        cur.link = newNode;

        addReqFile(newNode);
        return newNode;
    }

    //method that deletes the node at a specified position
    public foodNode deleteAtPosition(int position){

        if ((front == null) || (position < 1) || (position > this.length())){
            return null;}

        if (position==1){
            front = front.link;
            return front;
        }

        foodNode cur = front;   //making pointers for traversing through the list
        foodNode pre = front;
        int count=1;            //declaring and initializing a counter

        while (count < position){   //until node at position is reached..
            pre=cur;
            cur=cur.link;
            count++;                //keep traversing through list
        }

        pre.link = cur.link;    //skipping current and linking previous ot the node after current
        cur.link = null;        //detaching node at position from the linked list
        deleteFoodFile(menuFile, position);
        return cur;
    }
    public foodNode reqDeleteAtPosition(int position){
        if ((front == null) || (position < 1) || (position > this.length())){
            return null;}

        if (position==1){
            front = front.link;
            deleteFoodFile(requestFile, position);
            return front;
        }

        foodNode cur = front;   //making pointers for traversing through the list
        foodNode pre = front;
        int count=1;            //declaring and initializing a counter

        while (count < position){   //until node at position is reached..
            pre=cur;
            cur=cur.link;
            count++;                //keep traversing through list
        }

        pre.link = cur.link;    //skipping current and linking previous ot the node after current
        cur.link = null;        //detaching node at position from the linked list
        deleteFoodFile(requestFile, position);
        return cur;
    }
    public int length(){
        foodNode cur = front;
        int count = 0;

        if (cur == null)
            return 0;
        while (cur != null){
            count++;
            cur = cur.link;
        }
        return count;
    }

    public void checkAvailability(String name){
        foodNode cur = front;
        boolean found = false;

        while (cur != null){
            if (cur.name.equalsIgnoreCase(name)){
                found = true;
                System.out.println(cur.name+ " is currently on the menu!");
                display(cur);
                return;  //once the item is found, stop traversing the list
            }
            cur = cur.link;
        }
        if (!found){
            System.out.println(name+ " is not currently on the menu.");
        }
    }

    public void display(foodNode food){
        System.out.println(food.name+ "\nOrigin: " +food.origin+ "\nAllergen: " +food.allergen+
                "\nCalories: " +food.calories+ "\nPrice: $" +food.price);
    }

    //method that displays all the menu items' names in a numbered list
    public void displayItems(){
        foodNode cur = front;       //creating a pointer for traversing through the list
        int i=1;                    //the list numbering should start from 1

        if (front == null)
            System.out.println("No items on the menu yet");

        while (cur.link != null){   //keep traversing until the pointer "cur" points to null
            System.out.println(i+ ". " +cur.name);      //making the list numbered
            cur=cur.link;
            i++;
        }
        System.out.println(i+ ". " +cur.name);  //to display the very last foodNode name
    }

    //method that displays a report of all the countries of origin
    public void displayOrigins() {
        //creating an array to store the UNIQUE country names
        String[] countries = new String[this.length()];
        //creating an array to store how many times each country appears
        int[] count = new int[this.length()];

        int index = 0;  //to determine where to input new unique countries in coutries array

        foodNode cur = front;

        while (cur != null) {   //traverse through the list
            String curCountry = cur.origin;

            //checking if the country name already exists in the countries array
            boolean found = false;
            for (int i = 0; i < index; i++) {
                if (countries[i].equalsIgnoreCase(curCountry)) {
                    //if a match is found, stop searching through the array
                    found = true;
                    break;
                }
            }

            //if no match is found, add that country to the countries array
            if (!found) {
                //count the country's occurances in the menu
                int countryCount = 1;
                foodNode temp = cur.link;

                //traverse through the list in an inner loop to compare the countries of origin
                while (temp != null) {
                    if (temp.origin.equalsIgnoreCase(curCountry)) {
                        countryCount++;
                    }
                    temp = temp.link;
                }

                //store the unique country name in the countries array
                countries[index] = curCountry;
                //store the unique country's count in the count array
                count[index] = countryCount;
                index++;
            }
            cur = cur.link;
        }

        //display the countries and their counts
        for (int i = 0; i < index; i++) {
            System.out.println(countries[i] + ": " + count[i]);
        }

        if (index == 0) {
            System.out.println("No items found.");
        }
    }

    public void displayAllInfo(){
        foodNode cur = front;       //creating a pointer for traversing through the list
        int i=1;                    //list numbering starts from 1

        if (front == null)
            System.out.println("No items on the menu yet");

        while (cur.link != null){   //keep traversing until the pointer "cur" points to null
            System.out.print(i+ ". ");
            display(cur);
            System.out.println();
            cur=cur.link;
            i++;
        }
        System.out.print(i+ ". ");
        display(cur);
        System.out.println();
    }

    public void displayRequests(){
        foodNode cur = front;
        int count = 1;

        if (cur == null){
            System.out.println("No requests available.");
        }

        //traverse through the list until the last node is reached
        else{
            while (cur.link != null){
                System.out.println( count+ ". Name: " +cur.name+ ", Origin: " +cur.origin); //displaying request info in a numbered list
                cur = cur.link;
                count++;
            }
            System.out.println( count+ ". Name: " +cur.name+ ", Origin: " +cur.origin);//displays last node
        }
    }


    //////////////////////////////// methods related to files ///////////////////////////

    public void addMenuFile(foodNode food){//enters a log for each food item on the menu
        try {
            FileWriter fw = new FileWriter(menuFile, true);
            //append true will write to the end of the file rather than beginning
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(food.name+". Origin: "+food.origin+", Allergens: "+food.allergen+
                    ", Calories: "+food.calories+" kcal, Price: "+food.price+" SAR.");
            bw.newLine();
            bw.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File could not be found.");
        }
        catch(IOException e) {
            System.out.println("File error.");
        }
    }

    public void addReqFile(foodNode food){//enters a log for each requested item
        try{
            FileWriter fw = new FileWriter(requestFile, true);
            //append true will write to the end of the file rather than beginning
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(food.name+". Origin: "+food.origin+".");

            bw.newLine();
            bw.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File could not be found.");
        }
        catch(IOException e){
            System.out.println("File error.");
        }
    }

    public void deleteFoodFile(String originalFile, int ln){    //Deletes food from file depending on line position
        String tempFile = "temp.txt";   //create temporary text file
        File oldfile = new File(originalFile);  //create file of original file
        File newFile = new File(tempFile);      //create new file for temp.txt
        try{
            FileReader readOriginal = new FileReader(originalFile); //reads original file
            BufferedReader read = new BufferedReader(readOriginal);

            FileWriter fw = new FileWriter(tempFile);   //writes to temp file
            BufferedWriter write = new BufferedWriter(fw);
            PrintWriter writeTemp = new PrintWriter(write);

            int counter=0;  //counts each line from original file
            String line;    //stores text

            while ((line = read.readLine())!=null){
                counter++;
                if (ln != counter){             //excluding the line that equals the unwanted position,
                    writeTemp.println(line);    //this loop writes down all lines in the original txt file into the new file.
                }
            }

            writeTemp.close();  //close objects
            read.close();
            write.close();

            oldfile.delete();       //delete the original file.
            File newmenu = new File(originalFile); //creates file object with content of original file
            newFile.renameTo(newmenu);  //temporary file is renamed to the original files name, and overwrites it

        } catch(FileNotFoundException e){
            System.out.print("File not found.");
        }
        catch(IOException e){
            System.out.print("File error.");
        }
    }

    public void clearMenuFile() {
//       use this method to clear the menu file with each run
        try {
            PrintWriter pw = new PrintWriter(menuFile);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found.");
        }
    }
    public void clearReqFile() {
        //        use this method to clear the request  file with each run
        try {
            PrintWriter pw = new PrintWriter(requestFile);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found.");
        }
    }
    public void clearCompFile() {
        //        use this method to clear the complaint file with each run
        try {
            PrintWriter pw = new PrintWriter(complaintFile);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found.");
        }
    }
}
