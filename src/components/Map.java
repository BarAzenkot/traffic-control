package components;

import utilities.*;

import java.util.ArrayList;

public class Map implements Utilities {
    //================================VARIABLES================================

    private ArrayList<Junction> juctions = new ArrayList<Junction>();
    private ArrayList<Road> roads = new ArrayList<Road>();
    private ArrayList<TrafficLights> lights = new ArrayList<TrafficLights>();

    //================================CONSTRUCTORS================================

    public Map(int numOfJunctions){
        System.out.println("================ CREATING JUNCTIONS ================");
        for (int j = 0; j < numOfJunctions; j++) {
            boolean lighted = getRandomBoolean();
            if(lighted) {
                LightedJunction junc = new LightedJunction();
                this.juctions.add(junc);
                this.lights.add(junc.getLights());
            }
            else this.juctions.add(new Junction());
        }
        System.out.println();
        System.out.println("================ CREATING ROADS ================");
        setAllRoads();
        System.out.println();
        System.out.println("================ TRAFFIC LIGHTS TURN ON ================");
        turnLightOn();
        System.out.println();
        System.out.println("================ GAME MAP HAS BEEN CREATED ================");
        System.out.println();
    }

    public Map() {

    }

    //================================METHODS================================

    public ArrayList<Junction> getJuctions() {
        return juctions;
    }

    public void setJuctions(ArrayList<Junction> juctions) {
        this.juctions = juctions;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public ArrayList<TrafficLights> getLights() {
        return lights;
    }

    public void setLights(ArrayList<TrafficLights> lights) {
        this.lights = lights;
    }

    public void setAllRoads() {
        for (int i = 0; i < this.juctions.size(); i++) {
            for (int j = 0; j < this.juctions.size(); j++) {
                if (i != j) {
                    Road road = new Road(this.juctions.get(i), this.juctions.get(j));
                    if (road.isEnable()) {
                        this.roads.add(road);
                        //road.printRoad();
                    }
                }
            }
        }
    }

    public void turnLightOn(){
        for(int i = 0; i <this.lights.size(); i++)
            if(getRandomBoolean())
                this.lights.get(i).changeLights();
        printLightsOn();
    }

    private void printLightsOn() {
        for (int i = 0; i < this.juctions.size(); i++) {
            if (this.juctions.get(i) instanceof LightedJunction) {
                if (((LightedJunction) this.juctions.get(i)).getLights() instanceof RandomTrafficLights)
                    System.out.print("Random ");
                else System.out.print("Sequential ");
                System.out.println("traffic lights " + this.juctions.get(i).getJunctionName() + " turned ON, delay time: " + ((LightedJunction) this.juctions.get(i)).getLights().getDelay());
            }
        }
    }

}
