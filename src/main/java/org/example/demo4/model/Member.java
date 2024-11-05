package org.example.demo4.model;

public class Member {
    private String name;
    private String type;
    private double length;
    private double calories;

    public Member(String name, String type, double length, double calories) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.calories = calories;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getCalories() {
        return calories;
    }
    public void setCalories(double calories) {
        this.calories = calories;
    }
    public String toString() {
        return "Nombre: " + name +
                ", Tipo de actividad: " + type +
                ", Duración: " + length + " min" +
                ", Calorías quemadas: " + calories;
    }
}