package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Land extends Polygon{
    public City getCity(int i) {
        return cities.get(i);
    }

    private List<City> cities = new ArrayList<>();
    public Land(List<Point> points) {
        super(points);
    }
    public void addCity(City c){
        try {
            checkBoundsAdd(c);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
    private void checkBoundsAdd(City c) throws RuntimeException{
        if(this.inside(c.center)){
            cities.add(c);
            checkPort();
        }else{
            throw new RuntimeException("City not found on the land " + c.getName());
        }
    }
    public void checkPort(){
        for(City c : cities){
            List<Point> cityWalls = c.getPoints();
            for(Point p : cityWalls){
                if(!this.inside(p)){
                    c.setPort(true);
                    break;
                }
            }
        }
    }
    @Override
    public String toString() {
        return cities.stream().map(City::getName).collect(Collectors.joining(","));
    }
}
