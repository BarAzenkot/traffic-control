package utilities;
import components.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameDriver {
    static public final int runtime = 1000;
    static public Random r = new Random();
    static public ApplicationMediator mediator = new ApplicationMediator();

    public static void main(String[] args) {


        Moked m = new Moked();
        GUI start = new GUI();
        start.setSize(860,740);
        start.setVisible(true);

        //driving.drive(20);
    }
}
