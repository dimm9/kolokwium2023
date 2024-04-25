package org.example;

import java.util.List;

public class Polygon {
    public List<Point> getPoints() {
        return points;
    }

    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }
    public boolean inside(Point point){
        int counter = 0;
        for(int i=0; i<points.size(); i++){
            Point pa = points.get(i);
            double x;
            Point pb = points.get((i + 1) % points.size());
            if(pa.y < point.y && point.y < pb.y){
                double d = pb.x - pa.x;
                if(d == 0){
                    x = pa.x;
                }else{
                    double a = (pb.y - pa.y)/d;
                    double b = pa.y - a * pa.x;
                    x = (point.y - b) / a;
                }if(x < point.x){
                    counter++;
                }
            }
        }
        return counter%2==1;
    }
}
