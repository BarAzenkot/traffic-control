package utilities.Factories;

import components.Junction;
import components.LightedJunction;
import utilities.Utilities;

public class Country implements CityOrCountry, Utilities {

    @Override
    public Junction getJunction() {
        if(getRandomBoolean())
            return new LightedJunction();
        return new Junction();
    }
}
