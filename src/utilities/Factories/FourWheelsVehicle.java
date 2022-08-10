package utilities.Factories;

import components.Vehicle;
import utilities.VehicleType;

public class FourWheelsVehicle implements VehicleFactory{

        @Override
    public Vehicle getVehicle(String type) {
        if (type == "private")
            return new Vehicle(VehicleType.car);
        if (type == "work")
            return new Vehicle(VehicleType.truck);
        return new Vehicle(VehicleType.bus);
    }

}
