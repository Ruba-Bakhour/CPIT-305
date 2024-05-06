/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305project;

import java.sql.*;
import java.util.*;


/**
 *
 * @author shahad
 */
class ApplyFilterCats  {
    private static final String ConnectionURL = "jdbc:mysql://localhost:3306/Catopia_DB";
    
    
    public static List<Cat> filtering(int Age,String color, String breed) {
        //list to fetch all the cats from the database
        List<Cat> allCats = retrieveAllCatsFromDatabase();
        //list for the cats that matches the prefrences of the user
        List<Cat> filteredCats = filterCats(allCats, Age, color, breed);
        
        // Print or display the filtered cats to the user
        for (Cat cat : filteredCats) {
            System.out.println(cat);
        }
        
        return filteredCats;
        
    }

    // method that retrive all the cats in the database
    private static List<Cat> retrieveAllCatsFromDatabase() {
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //list for retrieving cats from the database
        List<Cat> cats = new ArrayList<>();   
        
        try{
            //connect to the database
            connection = DriverManager.getConnection(ConnectionURL, "root", "raghad"); // Change it to your settings
            
            //prepare the SQL query
            String sql = "SELECT * FROM cat";
            statement = connection.prepareStatement(sql);

            //execute the query
            resultSet = statement.executeQuery();

            //process the results
            while (resultSet.next()) {
                String name = resultSet.getString("cat_name");
                int age = resultSet.getInt("age");
                String color = resultSet.getString("color");
                String breed = resultSet.getString("breed");
                
                //create a new Cat object and add it to the list
                cats.add(new Cat(age, color, breed,name));
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        }catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cats;
    }
    
    
    //method to filter all the cats based on the user prefrences
    //using multithreading every filter is a thread
    private static List<Cat> filterCats(List<Cat> cats, int Age, String color, String breed) {
        List<Cat> filteredCats = new ArrayList<>();
        List<Cat> ageFilteredCats = new ArrayList<>();
        List<Cat> colorFilteredCats = new ArrayList<>();
        List<Cat> breedFilteredCats = new ArrayList<>();
        
        //create threads for filtering cats by age
        Thread ageFilterThread = new Thread(() -> {
            for (Cat cat : cats) {
                if (cat.getAge() == Age) {
                    ageFilteredCats.add(cat);
                }
            }
        });
        
        //create threads for filtering cats by color
        Thread colorFilterThread = new Thread(() -> {
            for (Cat cat : cats) {
                if (cat.getColor().equalsIgnoreCase(color)) {
                    colorFilteredCats.add(cat);
                }
            }
        });
        
        //create threads for filtering cats by breed
        Thread breedFilterThread = new Thread(() -> {
            for (Cat cat : cats) {
                if (cat.getBreed().equalsIgnoreCase(breed)) {
                    breedFilteredCats.add(cat);
                }
            }
        });
        
        //start the threads
        ageFilterThread.start();
        colorFilterThread.start();
        breedFilterThread.start();
        
        //wait for all threads to finish
        try {
            ageFilterThread.join();
            colorFilterThread.join();
            breedFilterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //merge results from all threads into the final list of filtered cats
        for (Cat cat : cats) {
            if (ageFilteredCats.contains(cat) || colorFilteredCats.contains(cat) || breedFilteredCats.contains(cat)) {
                filteredCats.add(cat);
            }
        }
        
        return filteredCats;
    }
    
}