package components;

import java.util.ArrayList;

public class RandomTrafficLights extends TrafficLights {
    //================================CONSTRUCTORS================================

        public RandomTrafficLights(ArrayList<Road> roads){
            super(roads);
        }

    //================================METHODS================================

    public void changeIndex(){
        int index;
        do{
            index = getRandomInt(0, getRoads().size());
        } while(getGreenLightIndex() == index);
        this.setGreenLightIndex(index);
    }

    @Override
    public String toString() {
        if(getGreenLightIndex() == -1)
            return "";
        return "Random traffic lights " + getRoads().get(getGreenLightIndex()).getEndJunction(); }
}
