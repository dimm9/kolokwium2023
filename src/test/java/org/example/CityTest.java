package org.example;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityTest {
    public static void setInlandCity(City inlandCity) {
        CityTest.inlandCity = inlandCity;
    }

    public static void setCoastalCity(City coastalCity) {
        CityTest.coastalCity = coastalCity;
    }

    private static City inlandCity;
    private static City coastalCity;

    @BeforeEach
    void setUp() {
        inlandCity = new City(new Point(50, 50), "InlandCity", 20);
        coastalCity = new City(new Point(100, 100), "CoastalCity", 20);
        coastalCity.setPort(true);
    }

    @ParameterizedTest(name = "Test {index}: Dodanie zasobu {1} do miasta {0} (oczekiwane: {2})")
    @MethodSource("resourceAdditionDataProvider")
    @DisplayName("Test dodawania zasobów do miast")
    void testResourceAddition(City city, Resource resource, boolean expected) {
        city.addResourcesInRange(List.of(resource), 30);
        assertEquals(expected, city.resources.contains(resource));
    }

    private static Stream<Arguments> resourceAdditionDataProvider() {
        Resource coal = new Resource(new Point(55, 55), Resource.Type.COAL);
        Resource wood = new Resource(new Point(150, 150), Resource.Type.WOOD);
        Resource fish = new Resource(new Point(105, 105), Resource.Type.FISH);
        return Stream.of(
                Arguments.of(inlandCity, coal, true),             // Poprawne dodanie węgla do miasta śródlądowego
                Arguments.of(inlandCity, wood, false),            // Nieudana próba dodania drewna do miasta śródlądowego spoza zasięgu
                Arguments.of(coastalCity, fish, true),            // Poprawne dodanie ryb do miasta portowego
                Arguments.of(inlandCity, fish, false)             // Nieudana próba dodania ryb do miasta śródlądowego
        );
    }
}
