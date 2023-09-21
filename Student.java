/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput;

import java.io.Serializable;

/**
 *
 * @author matth
 */


public class Student implements Serializable {
    private String name;
    private String id;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", id=" + id + ", score=" + score + '}';
    }

    public Student(String name, String id, int score) {
        this.name = name;
        this.id = id;
        this.score = score;
    }

    // Rest of the class...

    public Student() {
       
        
        this.name= "Liam";
        this.id = "12345";
        this.score= 90;
        
       
    }
}