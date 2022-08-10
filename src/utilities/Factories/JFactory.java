package utilities.Factories;

        import components.Junction;

public class JFactory {

    public CityOrCountry getJunction(String junction) {
        if (junction == "Country")
            return new Country();
        return new City();

    }
}
