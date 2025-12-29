package org.example;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

//class for the user methods
public class User {
    String complaintFile = "Complaint.txt";
    public void addCompFile(String complaint){
        try{
            FileWriter fw = new FileWriter(complaintFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(complaint);
            bw.newLine();
            bw.close();
        }catch(FileNotFoundException e){
            System.out.println("File could not be found.");
        }
        catch(IOException e){
            System.out.println("File error.");
        }
    }
    public foodNode getFoodAtIndex(foodList menu, int index){
        //method to get the food node at a specified index in the menu
        foodNode cur= menu.front;
        int count= 1;
        while(cur!=null && count<index){
            cur= cur.link;
            count++;}
        if(count==index && cur!=null){
            return cur;}
        else{
            System.out.println("Invalid food item index.");
            return null;
        }
    }
}
