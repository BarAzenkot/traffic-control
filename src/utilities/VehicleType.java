package utilities;

public enum VehicleType implements Utilities{
    car(90 / 10), bus(60 / 10), bicycle(40 / 10), motorcycle(120 / 10), truck(80 / 10), tram(50 / 10), semitrailer(85 / 10);

    private int averageSpeed;

    VehicleType(int speed){
        averageSpeed = speed;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public static VehicleType getRandom() {
        return values()[GameDriver.r.nextInt(VehicleType.values().length)];
    }
}