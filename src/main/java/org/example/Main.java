package org.example;
public class Main {
    public static void main(String[] args) {
        MapParser parser = new MapParser();
        parser.parse("map.svg");
        System.out.println(parser.getLands().toString());
    }
}