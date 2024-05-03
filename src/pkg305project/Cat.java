/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305project;

/**
 *
 * @author shahad
 */
public class Cat {
    private String name;
     private int age;
    private String color;
    private String breed;

    // Constructor, getters, and setters
    public Cat(int age, String color, String breed,String name) {
        this.name=name;
        this.age = age;
        this.color = color;
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return 
                " name= " + name + '\n' +
                " age=" + age +'\n' +
                " color= " + color + '\n' +
                " breed= " + breed + '\n' ;
               
    }
    
}
