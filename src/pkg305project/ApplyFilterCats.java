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
    
    
    public static List<Cat> filtering(int minAge, int maxAge, String color, String breed) {
        List<Cat> allCats = retrieveAllCatsFromDatabase();
        
        // Take user preferences as input
//        int minAge, maxAge;
//        String color, breed;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your preferences:");
//        
//        System.out.print("Minimum age: ");
//        minAge = scanner.nextInt();
//        
//        System.out.print("Maximum age: ");
//        maxAge = scanner.nextInt();
//        
//        scanner.nextLine(); // Consume newline
//        
//        System.out.print("Color: ");
//        color = scanner.nextLine();
//        
//        System.out.print("Breed: ");
//        breed = scanner.nextLine();
        
        
        List<Cat> filteredCats = filterCats(allCats, minAge, maxAge, color, breed);
        
        // Print or display the filtered cats to the user
        for (Cat cat : filteredCats) {
            System.out.println(cat);
        }
        
        return filteredCats;
        
    }

    private static List<Cat> retrieveAllCatsFromDatabase() {
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        // retrieving cats from the database
        List<Cat> cats = new ArrayList<>();   
        
        try{
            // Connect to the database
            connection = DriverManager.getConnection(ConnectionURL, "root", "1234"); // Change it to your settings
            
            // Prepare the SQL query
            String sql = "SELECT * FROM cat";
            statement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                int age = resultSet.getInt("age");
                String color = resultSet.getString("color");
                String breed = resultSet.getString("breed");
                
                // Create a new Cat object and add it to the list
                cats.add(new Cat(age, color, breed));
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
    
    private static List<Cat> filterCats(List<Cat> cats, int minAge, int maxAge, String color, String breed) {
        List<Cat> filteredCats = new ArrayList<>();
        List<Cat> ageFilteredCats = new ArrayList<>();
        List<Cat> colorFilteredCats = new ArrayList<>();
        List<Cat> breedFilteredCats = new ArrayList<>();
        
        // Create threads for filtering cats by age
        Thread ageFilterThread = new Thread(() -> {
            for (Cat cat : cats) {
                if (cat.getAge() >= minAge && cat.getAge() <= maxAge) {
                    ageFilteredCats.add(cat);
                }
            }
        });
        
        // Create threads for filtering cats by color
        Thread colorFilterThread = new Thread(() -> {
            for (Cat cat : cats) {
                if (cat.getColor().equalsIgnoreCase(color)) {
                    colorFilteredCats.add(cat);
                }
            }
        });
        
        // Create threads for filtering cats by breed
        Thread breedFilterThread = new Thread(() -> {
            for (Cat cat : cats) {
                if (cat.getBreed().equalsIgnoreCase(breed)) {
                    breedFilteredCats.add(cat);
                }
            }
        });
        
        // Start the threads
        ageFilterThread.start();
        colorFilterThread.start();
        breedFilterThread.start();
        
        // Wait for all threads to finish
        try {
            ageFilterThread.join();
            colorFilterThread.join();
            breedFilterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Merge results from all threads into the final list of filtered cats
        for (Cat cat : cats) {
            if (ageFilteredCats.contains(cat) || colorFilteredCats.contains(cat) || breedFilteredCats.contains(cat)) {
                filteredCats.add(cat);
            }
        }
        
        return filteredCats;
    }
    
}