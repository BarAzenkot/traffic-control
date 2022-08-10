package utilities.Builders;

import components.Junction;
import components.LightedJunction;
import components.Map;
import components.Road;
import utilities.Factories.JFactory;

import java.util.ArrayList;

public class CityBuilder implements MapBuilder {

    private Map map;
    private JFactory jf = new JFactory();

    public CityBuilder() {
        map = new Map();
    }


    @Override
    public String getType() {
        return "city";
    }

    @Override
    public void buildJunctions() {
        for(int i = 0; i < 12; i++) {
            map.getJuctions().add(jf.getJunction("City").getJunction());
            map.getLights().add(((LightedJunction) map.getJuctions().get(i)).getLights());
        }
    }

    @Override
    public void buildRoads() {
        for(int i = 0; i < 12; i++)
            for(int j = 0; j < 12; j++)
                if(j != i)
                    map.getRoads().add(new Road(map.getJuctions().get(i), map.getJuctions().get(j)));
        for(int i = 0; i < map.getRoads().size(); i++)
            map.getRoads().get(i).setEnable(true);

    }

    @Override
    public Map getMap() {
        return map;
    }

}
