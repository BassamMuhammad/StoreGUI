/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

/**
 *
 * @author Bassam Muhammad
 */
public class Item {
    private String name;
    private long id;
    private double cost;
    private int quantity; 
    private String imageAddress;
    
    public Item(String name, double cost, long id, int quantity) {
        this.name = name;
        this.cost = cost;
        this.id = id;
        this.quantity = quantity;
    }
    
    public Item(String name, String imageAddress, double cost) {
        this.name = name;
        this.cost = cost;
        this.imageAddress = imageAddress;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    
    
}
