package utilities.Factories;

import components.Vehicle;

public class VFactory implements WheelsFactory{
    public VehicleFactory getFactory(int numOfWheels) {
        switch (numOfWheels) {
            case 2:
                return new TwoWheelsVehicle();

            case 4:
                return new FourWheelsVehicle();

            default:
                return new TenWheelsVehicle();
        }
    }
}
