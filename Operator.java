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
                        System.out.println("Not enough parts avaiable. You will have to wait.");
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
                    System.out.println("New parts ordered. They will be delivered shortly...");
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
                System.out.println(parts.toString());
                if (parts.isEmpty()) {
                    System.out.println("Aircraft is finished assembly. Removing from factory.");
                    return;
                }
                for (int i = 0; i < parts.size(); i++) {
                    if ((robots).get(parts.get(i)).getWorkingAircraft() == null) {
                        (robots).get(parts.get(i)).setWorkingAircraft(aircraft);
                        System.out.println((robots).get(parts.get(i)) + " is now working on Aircraft: " + aircraft.getId());
                        (robots).get(parts.get(i)).getParts((robots).get(parts.get(i)));;
                        return;
                    }
                    else {
                        System.out.println("Robot " + parts.get(i) + " is busy. Assigning to next Robot.");
                    }
                }
                // implement Aircraft needs to wait until next Robot is free if no robots are free
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