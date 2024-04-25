import org.example.City;
import org.example.Land;
import org.example.Point;
import org.example.Resource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

public class CityTest {
    @org.junit.Test
    public void firstTest() {
        City c = new City(new Point(1, 2), "London", 2);
        List<Point> list = new ArrayList<>();
        list.add(new Point(0, 0));
        list.add(new Point(0, 7));
        list.add(new Point(10, 7));
        list.add(new Point(10, 0));
        Land land = new Land(list);
        land.addCity(c);
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource(new Point(1, 2), Resource.Type.COAL));
        land.addCity(c);
        land.getCity(0).addResourcesInRange(resources, 1);
        boolean b = !resources.isEmpty();
        System.out.println(b);
        resources.add(new Resource(new Point(30, 59), Resource.Type.WOOD));
        b = land.getCity(1).getResources().size() == 2;
        System.out.println(b);
        resources.add(new Resource(new Point(1, 2), Resource.Type.FISH));
        b = land.getCity(1).getResources().size() == 3;
        System.out.println(b);
    }
}
