package utilities;


import components.*;
import utilities.Builders.CityBuilder;
import utilities.Builders.CountryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class GUI extends JFrame implements ActionListener {
    private Driving driving;
    private JMenu File, Background, VehiclesColor, Help, BAM, CAC, Rep;
    private JMenuBar MenuBar;
    private JMenuItem Exit, BBlue, None, VBlue, Magenta, Orange, Random, HHelp, City, Country, RReport;
    private JButton CRS, Sta, Sto, Res, Inf;
    private JPanel ButtonPanel;
    private JDialog VoteDialog;
    private JOptionPane HelpDialog;
    private JSlider VSlide, JSlide;
    private JPanel VDPanel, VDBPanel;
    private JButton OK, Cancel;
    private JTable Table;
    private boolean stop;
    private String Type = "None";

    /**
     * default constructor.
     * @throws HeadlessException
     */
    public GUI() throws HeadlessException {
        super("Road System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        MenuBar = new JMenuBar();
        ButtonPanel = new JPanel();

        File = new JMenu("File");
        Background = new JMenu("Background");
        VehiclesColor = new JMenu("Vehicles color");
        Help = new JMenu("Help");
        BAM = new JMenu("Build a map");
        CAC = new JMenu("Clone a car");
        Rep = new JMenu("Reports");

        Exit = new JMenuItem("Exit");
        BBlue = new JMenuItem("Blue");
        None = new JMenuItem("None");
        VBlue = new JMenuItem("Blue ");
        Magenta = new JMenuItem("Magenta");
        Orange = new JMenuItem("Orange");
        Random = new JMenuItem("Random");
        City = new JMenuItem("City");
        Country = new JMenuItem("Country");
        HHelp = new JMenuItem("Help");
        RReport = new JMenuItem("Report");



        CRS = new JButton("Create road system");
        Sta = new JButton("Start");
        Sto = new JButton("Stop");
        Res = new JButton("Resume");
        Inf = new JButton("Info");


        this.add(ButtonPanel, BorderLayout.SOUTH);
        this.add(MenuBar, BorderLayout.NORTH);
        MenuBar.add(File);
        MenuBar.add(Background);
        MenuBar.add(VehiclesColor);
        MenuBar.add(Help);
        MenuBar.add(BAM);
        MenuBar.add(CAC);
        MenuBar.add(Rep);

        File.add(Exit);
        Background.add(BBlue);
        Background.add(None);
        VehiclesColor.add(VBlue);
        VehiclesColor.add(Magenta);
        VehiclesColor.add(Orange);
        VehiclesColor.add(Random);
        Help.add(HHelp);
        BAM.add(City);
        BAM.add(Country);
        Rep.add(RReport);


        ButtonPanel.add(CRS);
        ButtonPanel.add(Sta);
        ButtonPanel.add(Sto);
        ButtonPanel.add(Res);
        ButtonPanel.add(Inf);


        CRS.addActionListener(this);
        Sta.addActionListener(this);
        Sto.addActionListener(this);
        Res.addActionListener(this);
        Inf.addActionListener(this);


        Exit.addActionListener(this);
        BBlue.addActionListener(this);
        None.addActionListener(this);
        VBlue.addActionListener(this);
        Magenta.addActionListener(this);
        Orange.addActionListener(this);
        Random.addActionListener(this);
        HHelp.addActionListener(this);
        BAM.addActionListener(this);
        City.addActionListener(this);
        Country.addActionListener(this);
        CAC.addActionListener(this);
        RReport.addActionListener(this);

    }

    /**
     * creates the main window and buttons.
     */
    void createRoadSystem(Boolean b) {

        OK = new JButton("OK");
        Cancel = new JButton("Cancel");

        VDPanel = new JPanel();
        VDBPanel = new JPanel();

        VoteDialog = new JDialog();
        VoteDialog.setTitle("Create Road System");
        VoteDialog.setSize(600,300);
        VoteDialog.setVisible(true);
        VoteDialog.add(VDPanel);
        VoteDialog.add(VDBPanel, BorderLayout.SOUTH);

        JSlide = new JSlider(3, 20, 11);
        JSlide.setMajorTickSpacing(1);
        JSlide.setPaintTicks(true);
        JSlide.setPaintLabels(true);

        VSlide = new JSlider(0, 50, 25);
        VSlide.setMinorTickSpacing(1);
        VSlide.setMajorTickSpacing(5);
        VSlide.setPaintTicks(true);
        VSlide.setPaintLabels(true);

        VDPanel.setLayout(new GridLayout(4,0));
        if (b) {
            VDPanel.add(new Label("Number of Junctions"));
            VDPanel.add(JSlide, BorderLayout.CENTER);
        }

        VDPanel.add(new Label("Number of Vehicles"));
        VDPanel.add(VSlide, BorderLayout.CENTER);

        VDBPanel.add(OK, BorderLayout.SOUTH);
        VDBPanel.add(Cancel, BorderLayout.SOUTH);

        OK.addActionListener(this);
        Cancel.addActionListener(this);

    }

    /**
     * prints the system on the main window.
     * @param g
     */
    public void paint(Graphics g) {
        this.paintComponents(g);
        try {
            for (Road road : driving.getMap().getRoads()) {
                if(road.isEnable())
                    road.paintRoad(g);
            }
            for (Junction junction : driving.getMap().getJuctions()) {
                junction.paintJunction(g);
                }

            for (Vehicle vehicle : driving.getVehicles())
                vehicle.paintVehicle(g);

        }
        catch (NullPointerException exception){
            System.out.println("NullPointerException");
        }

    }

    /**
     * creates a table of all the data of the vehicles.
     */
    private void CreateATable() {
        String data[][] = new String[driving.getVehicles().size()][5];
        String column[]={"Vehicle #","Type","Location", "Time on loc", "Speed"};
        JFrame f = new JFrame();
        for(int i = 0; i < driving.getVehicles().size(); i++) {
            data[i][0] = String.valueOf(driving.getVehicles().get(i).getID());
            data[i][1] = String.valueOf(driving.getVehicles().get(i).getVehicleType());
            data[i][2] = String.valueOf(driving.getVehicles().get(i).getCurrentRoutePart());
            data[i][3] = String.valueOf(driving.getVehicles().get(i).getTimeOnCurrentPart());
            data[i][4] = String.valueOf(driving.getVehicles().get(i).getCurrentSpeed());
        }
        Table = new JTable(data, column);
        Table.setBounds(300,40,600,300);
        JScrollPane sp=new JScrollPane(Table);
        f.add(sp);
        f.setSize(900,400);
        f.setVisible(true);

    }

    /**
     * activates the system after pausing it.
     */
    public synchronized void resumePainting() {
        this.stop = false;
        notifyAll();
    }

    /**
     * pauses the system.
     */
    private synchronized void stopPainting() {
        while (stop) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * case for every button of the system.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Create road system":
                if(Type.equals("None")){
                    createRoadSystem(true);
                }
                else{
                    createRoadSystem(false);
                }
                break;

            case "Start":
                try {
                    driving.setStop(false);
                    driving.start();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                repaint();
                                stopPainting();
                                try {
                                    Thread.sleep(GameDriver.runtime);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });
                    t.start();
                } catch (NullPointerException ex) {
                    System.out.println("NullPointerException");
                } catch (IllegalThreadStateException ex) {
                    System.out.println("IllegalThreadStateException");
                }
                break;

            case "Stop":
                driving.setStop(true);
                this.stop = true;
                break;

            case "Resume":
                driving.resumeDriving();
                resumePainting();
                break;

            case "Info":
                try {
                    CreateATable();
                } catch (NullPointerException ex){
                    System.out.println("NullPointerException");
                }
                break;

            case "City":
                Type = "City";

                break;

            case "Country":
                Type = "Country";

                break;

            case "Report":
                try {
                    Desktop.getDesktop().open(new File(Moked.address));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;

            case "OK":
                if (Type.equals("City"))
                    this.driving = new Driving(new CityBuilder(), VSlide.getValue());
                else if(Type.equals("Country"))
                    this.driving = new Driving(new CountryBuilder(), VSlide.getValue());
                else
                    this.driving = new Driving(JSlide.getValue(), VSlide.getValue());
                VoteDialog.setVisible(false);
                repaint();
                break;

            case "Cancel":
                VoteDialog.setVisible(false);
                break;

            case "Exit":
                if(Moked.allApproved())
                    System.exit(1);
                break;

            case "Blue":
                getContentPane().setBackground(Color.BLUE);
                repaint();
                break;

            case "None":
                getContentPane().setBackground(null);
                repaint();
                break;

            case "Blue ":
                try {
                    for (Vehicle vehicle : driving.getVehicles())
                        vehicle.setMyColor(Color.BLUE);
                    repaint();
                } catch (NullPointerException ex) {
                    System.out.println("NullPointerException");
                }
                break;

            case "Magenta":
                try {
                    for (Vehicle vehicle : driving.getVehicles())
                        vehicle.setMyColor(Color.MAGENTA);
                    repaint();
                } catch (NullPointerException ex) {
                    System.out.println("NullPointerException");
                }
                break;

            case "Orange":
                try {
                    for (Vehicle vehicle : driving.getVehicles())
                        vehicle.setMyColor(Color.ORANGE);
                    repaint();
                } catch (NullPointerException ex) {
                    System.out.println("NullPointerException");
                }
                break;

            case "Random":
                try {
                    for (Vehicle vehicle : driving.getVehicles())
                        vehicle.setMyColor(vehicle.randColor());
                    repaint();
                } catch (NullPointerException ex) {
                    System.out.println("NullPointerException");
                }
                break;

            case "Help":
                HelpDialog = new JOptionPane();
                HelpDialog.showMessageDialog(null, "Home Work 3\nGUI @ Threads");
                HelpDialog.setVisible(true);
                break;
        }
    }
}
