package utilities.Builders;

import components.Map;

public class Engineer {
    private MapBuilder mapBuilder;

    public Engineer(MapBuilder builder) {
        mapBuilder = builder;
    }

    public Map getMap() {
        return mapBuilder.getMap();
    }

    public String getType() {
        return mapBuilder.getType();
    }

    public void constructMap() {
        mapBuilder.buildJunctions();
        mapBuilder.buildRoads();
    }
}
