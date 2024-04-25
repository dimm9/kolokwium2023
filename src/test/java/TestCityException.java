import org.example.City;
import org.example.Land;
import org.example.Point;

import java.util.ArrayList;
import java.util.List;

public class TestCityException {
    @org.junit.Test
    public void firstTest() {
        City c = new City(new Point(1, 2), "London", 2);
        c.getPoints().forEach(System.out::println);
        List<Point> list = new ArrayList<>();
        list.add(new Point(0, 0));
        list.add(new Point(0, 7));
        list.add(new Point(10, 7));
        list.add(new Point(10, 0));
        Land land = new Land(list);
        land.addCity(c);

    }
}
