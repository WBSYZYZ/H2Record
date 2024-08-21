package com.dashboard;

public class Circle implements Shape{
    @Override
    public void draw() {
        System.out.println("Circle");
    }
}

class Square implements Shape{
    @Override
    public void draw() {
        System.out.println("Square");
    }
}
