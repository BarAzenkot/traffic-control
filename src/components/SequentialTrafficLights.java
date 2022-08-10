package components;

import java.util.ArrayList;

public class SequentialTrafficLights extends TrafficLights {
    //================================VARIABLES================================

    private int increment;

    //================================CONSTRUCTORS================================

    public SequentialTrafficLights(ArrayList<Road> roads){
        super(roads);
        this.increment = 1;
    }

    //================================METHODS================================


    public int getIncrement() { return increment; }

    public void setIncrement(int increment) { this.increment = increment; }

    public void changeIndex() {
        this.setGreenLightIndex((getGreenLightIndex() + this.increment) % getRoads().size());
    }

    @Override
    public String toString() {
        if(getGreenLightIndex() == -1)
            return "";
        return "Sequential traffic lights " + getRoads().get(getGreenLightIndex()).getEndJunction(); }
}
