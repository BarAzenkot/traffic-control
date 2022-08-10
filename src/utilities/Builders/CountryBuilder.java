package utilities.Builders;

import components.*;
import utilities.Factories.JFactory;
import utilities.GameDriver;
import utilities.Utilities;

import java.util.ArrayList;

public class CountryBuilder implements MapBuilder, Utilities {

    private Map map;
    private JFactory jf = new JFactory();

    public CountryBuilder() {
        map = new Map();
    }

    @Override
    public void buildJunctions() {
        for (int i = 0; i < 6; i++) {
            Junction junc = jf.getJunction("Country").getJunction();
            map.getJuctions().add(junc);
            if (junc instanceof LightedJunction)
                map.getLights().add(((LightedJunction) map.getJuctions().get(map.getJuctions().size() - 1)).getLights());
        }

    }

    @Override
    public String getType() {
        return "Country";
    }

    @Override
    public void buildRoads() {
        for (int i = 0; i < map.getJuctions().size(); i++) {
            for (int j = 0; j < map.getJuctions().size(); j++) {
                if (i != j) {
                    map.getRoads().add(new Road(map.getJuctions().get(i), map.getJuctions().get(j)));
                }
            }
        }
        map.turnLightOn();

    }

    @Override
    public Map getMap() {
        return map;
    }


}
