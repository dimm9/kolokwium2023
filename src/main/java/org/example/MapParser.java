package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapParser {
    private List<Land> lands = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    public List<Land> getLands() {
        return lands;
    }

    static public final class Svg {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("rect")
        private List<Map<String, String>> rects;

        public List<Map<String, String>> getPolygons() {
            return polygons;
        }

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("polygon")
        private List<Map<String, String>> polygons;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("text")
        private List<Map<String, String>> texts;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("circle")
        private List<Map<String, String>> circles;
    }

    private record Label(Point point, String text) {}
    private List<Label> labels = new ArrayList<>();

    private void parseText(Map<String, String> params) {
        addLabel(params.get(""), new Point (Double.parseDouble(params.get("x")), Double.parseDouble(params.get("y"))));
    }

    private void parsePoly(Map<String, String> params) {
        String pointsString = params.get("points");
        String[] pointsArray = pointsString.split("\\s+");
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < pointsArray.length; i += 2) {
            double x = Double.parseDouble(pointsArray[i]);
            double y = Double.parseDouble(pointsArray[i + 1]);
            points.add(new Point(x, y));
        }
        addLand(points);
    }

    private void parseRect(Map<String, String> params) {
        double x = Double.parseDouble(params.get("x"));
        double y = Double.parseDouble(params.get("y"));
        double width = Double.parseDouble(params.get("width"));
        double height = Double.parseDouble(params.get("height"));
        List<Point> points = List.of(
                new Point(x, y),
                new Point(x + width, y),
                new Point(x + width, y + height),
                new Point(x, y + height)
        );
        cities.add(new City(points, City.findCentre(new Polygon(points))));
    }
    private void addLabel(String text, Point bottomLeft) {
        labels.add(new Label(bottomLeft, text));
    }
    private void addLand(List<Point> points) {
        lands.add(new Land(points));
    }


    void matchLabelsToTowns() {
        for (City c : cities) {
            double max = 0;
            Label nearest = null;
            for (Label l : labels) {
                double distance = City.getDistance(c, l.point());
                if (distance > max) {
                    max = distance;
                    nearest = l;
                }
            }
            assert nearest != null;
            c.setName(nearest.text);
        }
    }

    void addCitiesToLands() {
        for(Land l : lands){
            for(City c : cities){
                l.addCity(c);
            }
        }
    }

    void parse(String path) {
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(path);
        try {
            Svg svg = xmlMapper.readValue(file, Svg.class);
            for(var item : svg.texts)
                parseText(item);
            for(var poly : svg.polygons){
                parsePoly(poly);
            }
            for(var city : svg.rects){
                parseRect(city);
            }
            matchLabelsToTowns();
            addCitiesToLands();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
