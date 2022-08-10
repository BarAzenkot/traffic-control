package utilities.Builders;

import components.Map;

public interface MapBuilder {
    public String getType();
    public void buildJunctions();
    public void buildRoads();
    public Map getMap();
}
