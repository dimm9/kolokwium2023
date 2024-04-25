package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City extends Polygon{
    public City(List<Point> points, Point center) {
        super(points);
        this.center = center;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    protected Set<Resource> resources = new HashSet<>();
    public final Point center;

    private boolean port = false;
    public void setPort(boolean port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public City(Point center, String name, double length) {
        super(square(center, length));
        this.center = center;
        this.name = name;
    }

    public void addResourcesInRange(List<Resource> resources, double range){
        for(Resource r : resources){
            if(getDistance(this, r.itemLocation) <= range){
                if(!this.port && r.item.equals(Resource.Type.FISH)){
                    continue;
                }else {
                    this.resources.add(r);
                }
            }
        }
    }
    public static Point findCentre(Polygon poly){
        double x = poly.getPoints().get(0).x != poly.getPoints().get(1).x ? (poly.getPoints().get(0).x + poly.getPoints().get(1).x)/2 : (poly.getPoints().get(1).x + poly.getPoints().get(2).x)/2;
        double y = poly.getPoints().get(0).y != poly.getPoints().get(1).y ? (poly.getPoints().get(0).y + poly.getPoints().get(1).y)/2 : (poly.getPoints().get(1).y + poly.getPoints().get(2).y)/2;
        return new Point(x, y);
    }
    public static double getDistance(City c, Point p){
        return Math.sqrt(Math.pow(c.center.x - p.x, 2) + Math.pow(c.center.y - p.y, 2));
    }
    private static List<Point> square(Point center, double length){
        double x1 = (2*center.x - length)/2;
        double y1 = (2*center.y - length)/2;
        double x2 = x1 + length;
        double y2 = y1 + length;
        List<Point> square = new ArrayList<>();
        square.add(new Point(x1, y1));
        square.add(new Point(x2, y1));
        square.add(new Point(x2, y2));
        square.add(new Point(x1, y2));
        return square;
    }

    @Override
    public String toString() {
        return port ? name + "⚓" : name;
    }
}
