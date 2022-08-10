package utilities;


import java.util.concurrent.ThreadLocalRandom;

public abstract class Point implements Utilities {
    //================================VARIABLES================================
    final private int minVal = 0;
    final private int maxX = 800;
    final private int maxY = 600;
    private double x;
    private double y;

    //================================CONSTRUCTORS================================

    /**
     * constructor.
     * @param x
     * @param y
     */
    public Point(double x, double y){
        if(checkValue(x,maxX,minVal))
            this.x = x;

        else {
            this.x = getRandomDouble(minVal, maxX);
            correctingMessage(x, this.x, "x");
        }
        if(checkValue(y,maxY,minVal))
            this.y = y;
        else {
            this.y = getRandomDouble(minVal, maxY);
            correctingMessage(x, this.x, "x");
        }
    }
    /**
     * copy constructor.
     */
    public Point() {
        this.x = getRandomDouble(minVal, maxX);
        this.y = getRandomDouble(minVal, maxY);
    }

    //================================METHODS================================

    /**
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * sets x.
     * @param x
     */
    public void setX(double x) {
        if(checkValue(x,maxX,minVal))
            this.x = x;

        else {
            errorMessage(x, "x");
        }
    }

    /**
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * sets y.
     * @param y
     */
    public void setY(double y) {
        if(checkValue(y,maxY,minVal))
            this.y = y;

        else {
            errorMessage(y, "y");
        }
    }


    /**
     * represent class Point in string.
     * @return
     */
    public String toString() {
        return "( " +
                this.x +
                ", " + this.y +
                ")";
    }

    /**
     * @param o
     * @return true if this instance of object equals to another.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    public double calcDistance(Point other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }


}
