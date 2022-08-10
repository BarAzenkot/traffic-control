package utilities;

import components.Vehicle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BigBrother extends Colleague implements Observer, Utilities {

    private static volatile BigBrother brother;

    private BigBrother() {
        super(GameDriver.mediator);
        ((ApplicationMediator) getMediator()).addColleague(this);
    }

    public static synchronized BigBrother getInstance() {
        if (brother == null) {
            synchronized (BigBrother.class) {
                if (brother == null)
                    brother = new BigBrother();
            }
        }
        return brother;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (((Vehicle) arg).getCurrentSpeed() > ((Vehicle) arg).getLastRoad().getMaxSpeed()){
            send(arg);
        }

    }

    @Override
    public void receive(Object obj) {
        ((Report) obj).getVehicle().ApproveReport((Report) obj);
    }
}
