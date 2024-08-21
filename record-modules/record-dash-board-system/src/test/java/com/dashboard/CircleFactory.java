package com.dashboard;

public class CircleFactory implements ShapeFactory{

    @Override
    public Shape getShape() {
        return null;
    }
}

class SquareFactory implements ShapeFactory{
    @Override
    public Shape getShape() {
        return new Square();
    }
}