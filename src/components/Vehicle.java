package components;

import utilities.*;

import java.awt.*;
import java.util.Observable;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Vehicle extends Observable implements Utilities, Timer, Runnable {
    //================================VARIABLES================================

    private int ID;
    private VehicleType vehicleType;
    private Route currentRoute;
    private RouteParts currentRoutePart;
    private double timeFromRouteStart;
    private double timeOnCurrentPart;
    static private int objectsCount;
    private Road lastRoad;
    private String status;
    private Color color = Color.BLUE;
    private double currentXroute,currentYroute;
    private int currentSpeed;

    //================================CONSTRUCTORS================================

    public Vehicle(Road road) {
        this.ID = ++objectsCount;
        this.vehicleType = VehicleType.values()[GameDriver.r.nextInt(VehicleType.values().length)];
        this.status = null;
        this.timeOnCurrentPart = 0;
        this.timeFromRouteStart = 0;
        System.out.println(this + " has been created.");
        this.currentRoute = new Route(road, this);
        this.currentXroute = ((Road) currentRoute.getStartRoute()).getStartJunction().getX();
        this.currentYroute = ((Road) currentRoute.getStartRoute()).getStartJunction().getY();
        addObserver(BigBrother.getInstance());
    }

    public Vehicle(VehicleType type) {
        this.ID = ++objectsCount;
        this.vehicleType = VehicleType.values()[GameDriver.r.nextInt(VehicleType.values().length)];
        this.status = null;
        this.timeOnCurrentPart = 0;
        this.timeFromRouteStart = 0;
        System.out.println(this + " has been created.");
        this.currentRoute = new Route(Driving.addRandomRoad(), this);
        this.currentXroute = ((Road) currentRoute.getStartRoute()).getStartJunction().getX();
        this.currentYroute = ((Road) currentRoute.getStartRoute()).getStartJunction().getY();
        addObserver(BigBrother.getInstance());
    }

    //================================METHODS================================


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    public RouteParts getCurrentRoutePart() {
        return currentRoutePart;
    }

    public void setCurrentRoutePart(RouteParts currentRoutePart) {
        this.currentRoutePart = currentRoutePart;
    }

    public double getTimeFromRouteStart() {
        return timeFromRouteStart;
    }

    public void setTimeFromRouteStart(double timeFromRouteStart) {
        this.timeFromRouteStart = timeFromRouteStart;
    }

    public double getTimeOnCurrentPart() {
        return timeOnCurrentPart;
    }

    public void setTimeOnCurrentPart(double timeOnCurrentPart) {
        this.timeOnCurrentPart = timeOnCurrentPart;
    }

    public int getObjectsCount() {
        return objectsCount;
    }

    public void setObjectsCount(int objectsCount) {
        this.objectsCount = objectsCount;
    }

    public Road getLastRoad() {
        return lastRoad;
    }

    public void setLastRoad(Road lastRoad) {
        this.lastRoad = lastRoad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrentSpeed() { return currentSpeed;}

    public void setCurrentSpeed(int speed) { currentSpeed = speed; }

    public void move() {
        currentRoute.findNextPart(this);
        if (currentRoutePart.canLeave(this)) {
            currentRoutePart.checkOut(this);
            currentRoute.getRouteParts().get(currentRoute.getRouteParts().indexOf(currentRoutePart) + 1).checkIn(this);
            if(currentRoute.canLeave(this))
                currentRoute.checkOut(this);
        }

        else currentRoutePart.stayOnCurrentPart(this);

    }

    public void incrementDrivingTime(){
        this.timeFromRouteStart ++;
        this.timeOnCurrentPart ++;
        move();
    }

    /**
     * prints a vehicle.
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param d
     * @param h
     */
    private void drawRotatedVehicle(Graphics g, int x1, int y1, int x2, int y2, int d, int h){
        int dx = x2 - x1, dy = y2 - y1, delta = 10;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = delta, xn = xm, ym = h, yn = -h, x;
        double xm1 = delta + d, xn1 = xm1, ym1 = h, yn1 = -h, xx;
        double sin = dy / D, cos = dx / D;
        x = xm*cos - ym*sin + x1;
        xx = xm1*cos - ym1*sin + x1;
        ym = xm*sin + ym*cos + y1;
        ym1 = xm1*sin + ym1*cos + y1;
        xm = x;
        xm1 = xx;
        x = xn*cos - yn*sin + x1;
        xx = xn1*cos - yn1*sin + x1;
        yn = xn*sin + yn*cos + y1;
        yn1 = xn1*sin + yn1*cos + y1;
        xn = x;
        xn1 = xx;
        int[] xpoints = {(int) xm1, (int) xn1,  (int) xn, (int) xm};
        int[] ypoints = {(int) ym1, (int) yn1, (int) yn, (int) ym};
        g.setColor(this.color);
        g.fillPolygon(xpoints, ypoints, 4);
        g.setColor(Color.BLACK);
        g.fillOval((int) xm1-2,(int) ym1-2,4,4);
        g.fillOval((int) xn1-2,(int) yn1-2,4,4);
        g.fillOval((int) xm-2,(int) ym-2,4,4);
        g.fillOval((int) xn-2,(int) yn-2,4,4);
        g.setColor(this.color);

    }

    /**
     * promotes the vehicles in their routes.
     */
    public void updateMove() {
        double m, d, x1, x2, y1, y2;
        if (currentRoutePart instanceof Road) {
            d = getCurrentSpeed();

            m = (((Road) currentRoutePart).getEndJunction().getY() - ((Road) currentRoutePart).getStartJunction().getY()) /
                    (((Road) currentRoutePart).getEndJunction().getX() - ((Road) currentRoutePart).getStartJunction().getX());

            x1 = currentXroute + d / Math.sqrt((m * m) + 1);
            x2 = currentXroute - d / Math.sqrt((m * m) + 1);

            y1 = currentYroute + m * (x1 - currentXroute);
            y2 = currentYroute + m * (x2 - currentXroute);

            if (calcDistance(x1, y1, ((Road) currentRoutePart).getEndJunction().getX(), ((Road) currentRoutePart).getEndJunction().getY()) <
                    calcDistance(x2, y2, ((Road) currentRoutePart).getEndJunction().getX(), ((Road) currentRoutePart).getEndJunction().getY())) {
                currentXroute = x1;
                currentYroute = y1;
            } else {
                currentXroute = x2;
                currentYroute = y2;
            }
        }

    }

    public double calcDistance(double x1, double y1,double x2, double y2){
        double dx = x2 - x1, dy = y2 - y1;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public void setMyColor(Color c){
        color = c;
    }

    /**
     * choose and return a random color for the vehicle.
     * @return
     */
    public Color randColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    public void paintVehicle(Graphics g){
        drawRotatedVehicle(g, (int)currentXroute + 30,(int)currentYroute + 70, (int) lastRoad.getEndJunction().getX() + 30, (int) lastRoad.getEndJunction().getY() + 70,10,4);
    }

    public void run() {
        while (true) {
            incrementDrivingTime();
            try {
                sleep(GameDriver.runtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void change() {
        this.setChanged();
    }

    public void ApproveReport(Report report) {
        report.getState().setStatus();

    }

    @Override
    public String toString() {
        return "Vehicle " + ID + ": " + vehicleType + ", speed: " + getCurrentSpeed() * 10;
    }
}

