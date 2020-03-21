import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class Operator {
    
    private Factory factory;
    private int partsPool;
    private ArrayList<Robot> robots;
    private Queue<Robot> robotsWaitingList;

    public Operator(Factory factory){
        this.factory = factory;
        this.partsPool = 5;
        this.robots = factory.getRobots();
        this.robotsWaitingList = new ArrayDeque<Robot>();
    }
    
    public int getPartsCount() {
        return this.partsPool;
    }

    public void takeParts(Robot robot) {
        factory.execute(() -> {
            synchronized (this) {
                int partsNeeded = robot.getInstallAmount();
                if (this.partsPool >= partsNeeded) {
                    this.partsPool -= partsNeeded;
                    System.out.println("Parts given to Robot " + robot.getID() + ". There now are " + this.partsPool + " parts remaining.");
                }
                else {
                    if (!robotsWaitingList.contains(robot)) {
                        System.out.println("Not enough parts avaiable for " + robot + ". Waiting for parts...");
                        robotsWaitingList.add(robot);
                        waitingArea(this.partsPool);
                    }
                }
            }
        });
    }

    public void orderParts(int order) {
        
        // factory.execute(() -> {
        //     synchronized (this) {
                if (order > 0) {
                    System.out.println("\nNew parts ordered. They will be delivered shortly...");
                    try {
                        Thread.sleep(3000);
                    } 
                    catch (final InterruptedException e) {
                        System.out.println(e);
                    }
                    this.partsPool = this.partsPool + order;
                    System.out.println("Parts have been restocked. There are now " + getPartsCount() + " parts in stock.\n");
                }
        //     }
        // });
    }

    public void moveAircraft(Aircraft aircraft) {
        factory.execute(() -> {
            synchronized (this) {
                ArrayList<Integer> parts = aircraft.getPartsNeeded();
                if (parts.isEmpty()) {
                    System.out.println("Aircraft is finished assembly. Removing from factory.");
                    return;
                }
                for (int i = 0; i < parts.size(); i++) {
                    Robot robot = (robots).get(parts.get(i));
                    if (robot.getWorkingAircraft() == null) {
                        System.out.println("Moving Aircraft " + aircraft.getID() + " to Robot " + robot.getID());
                        try {
                            Thread.sleep(5000); // wait for 100 ticks / 5 seconds
                        } 
                        catch (final InterruptedException e) {
                            System.out.println(e);
                        }
                        robot.setWorkingAircraft(aircraft);
                        System.out.println(robot + " is now working on Aircraft: " + aircraft.getID() + "\n");
                        robot.getParts(robot);
                        return;
                    }
                    else {
                        System.out.println("Robot " + parts.get(i) + " is busy. Assigning to next Robot.");
                    }
                }
                try {
                    System.out.println("All Robots are working. Aircraft " + aircraft.getID() + " will have to wait.");
                    Thread.sleep(5000);
                    System.out.println("Tring to assign aircraft " + aircraft.getID() + " to a Robot again.");
                    moveAircraft(aircraft);
                } 
                catch (final InterruptedException e) {
                    System.out.println(e);
                }
            }
        });
    }


    public  void waitingArea(int parts) {
        factory.execute(() -> {
            synchronized (this) {
                int partsNow = parts;
                if (partsPool <= 10){
                    orderParts(50);
                }
                while (!robotsWaitingList.isEmpty()) {
                    if (this.partsPool != partsNow) {
                        takeParts(robotsWaitingList.poll());
                    }
                }
            }
        });
    }


    
}