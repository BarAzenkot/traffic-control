package utilities.Factories;

import components.LightedJunction;

public class City implements CityOrCountry {

    @Override
    public LightedJunction getJunction() {
        return new LightedJunction();
    }
}
