package components;

import java.awt.*;

public class LightedJunction extends Junction {
    //================================VARIABLES================================

    private TrafficLights lights;

    //================================CONSTRUCTORS================================

    public LightedJunction (){
        super();
        this.lights = getRandomLightedType();
    }

    public LightedJunction (String name, double x, double y, boolean sequential, boolean lightsOn){
        super(name, x, y);
        if(sequential)
            this.lights = new SequentialTrafficLights(this.getEnteringRoads());
        else this.lights = new RandomTrafficLights(this.getEnteringRoads());
        this.lights.setTrafficLightOn(lightsOn);
    }

    //================================METHODS================================

    public TrafficLights getLights() {
        return lights;
    }

    public void setLights(TrafficLights lights) {
        this.lights = lights;
    }

    public double calcEstimatedTime(Object obj) {
        return lights.getDelay() * (this.getEnteringRoads().size() - 1) + 1;
    }

    public boolean checkAvailability(Vehicle vehicle) {
        if(this.getExitingRoads().isEmpty())
            return false;
        if(vehicle.getLastRoad().isGreenLight())
            return true;
        return false;
    }

    public void paintJunction(Graphics g){
        if (this.lights.isTrafficLightOn()) {
            g.setColor(Color.RED);
        }
            else g.setColor(Color.GREEN);
        g.fillOval((int) getX() + 20, (int) getY() + 60, 20, 20);
        paintTraffics(g);
    }

    private TrafficLights getRandomLightedType(){
        if(getRandomBoolean())
            return new RandomTrafficLights(this.getEnteringRoads());
        return new SequentialTrafficLights(this.getEnteringRoads());
    }

    /**
     * prints the green arrow.
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param d
     * @param h
     */
    public void drawRotatedArrow(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1, delta = 10;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = delta, xn = xm, ym = h, yn = -h, x;
        double xm1 = delta + d, xn1 = xm1, ym1 = h, yn1 = -h, xx;
        double sin = dy / D, cos = dx / D;
        x = xm * cos - ym * sin + x1;
        xx = xm1 * cos - ym1 * sin + x1;
        ym = xm * sin + ym * cos + y1;
        ym1 = xm1 * sin + ym1 * cos + y1;
        xm = x;
        xm1 = xx;
        x = xn * cos - yn * sin + x1;
        xx = xn1 * cos - yn1 * sin + x1;
        yn = xn * sin + yn * cos + y1;
        yn1 = xn1 * sin + yn1 * cos + y1;
        xn = x;
        xn1 = xx;
        double Xm = (xn + xm) / 2;
        double Ym = (yn + ym) / 2;
        int[] xpoints = {(int) xm1, (int) xn1, (int) Xm};
        int[] ypoints = {(int) ym1, (int) yn1, (int) Ym};
        g.setColor(Color.GREEN);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    /**
     * prints the lighted junctions.
     * @param g
     */
    private void paintTraffics(Graphics g) {
        try {
            if(lights.isTrafficLightOn())
                drawRotatedArrow(g, (int) getEnteringRoads().get(lights.getGreenLightIndex()).getEndJunction().getX() + 30, (int) getEnteringRoads().get(lights.getGreenLightIndex()).getEndJunction().getY() + 70,
                        (int) getEnteringRoads().get(lights.getGreenLightIndex()).getStartJunction().getX() + 30, (int) getEnteringRoads().get(lights.getGreenLightIndex()).getStartJunction().getY() + 70, 10, 4);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("IndexOutOfBoundsException");
        }
    }

}
