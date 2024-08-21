package com.dashboard;

public class test4 {
    public static void main(String[] args) {
        ShapeFactory factory = new CircleFactory();
        Shape shape = factory.getShape();
        shape.draw();
        ShapeFactory factory1 = new SquareFactory();
        Shape shape1 = factory1.getShape();
        shape1.draw();

    }
}
