package org.example;

public class Resource {

    public final Point itemLocation;
    public final Type item;

    public Resource(Point itemLocation, Type item) {
        this.itemLocation = itemLocation;
        this.item = item;
    }

    public enum Type{
        COAL, WOOD, FISH
    }
    //
}