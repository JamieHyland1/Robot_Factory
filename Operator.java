import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class Operator {
    
    private Factory factory;
    private int partsPool = 0;
    ArrayList<Robot> robots;
    private Queue<Integer> robotsWaitingList;

    public Operator(Factory factory){
        this.factory = factory;
        this.partsPool = 100;
        this.robots = factory.getRobots();
        this.robotsWaitingList = new ArrayDeque<Integer>();
    }
    
    public int getPartsCount() {
        return this.partsPool;
    }

    public void takeParts(int partsNeeded) {
        factory.execute(() -> {
            synchronized (this) {
                if (this.partsPool >= partsNeeded) {
                    this.partsPool -= partsNeeded;
                    if (this.partsPool <= 10) {
                        System.out.println("The factory is low on parts. Ordering more parts.");
                        try {
                            Thread.sleep(10000);
                        } 
                        catch (final InterruptedException e) {
                            System.out.println(e);
                        }
                        orderParts(50);
                    }
                }
                else {
                    System.out.println("The factory doesn't have enough parts. You will have to wait.");
                    robotsWaitingList.add(partsNeeded);
                    waitingArea();
                }
                System.out.println("Parts needed must be a positive number!");
            }
        });
    }

    public void orderParts(int order) {
        if (order > 0) {
            System.out.println("New parts ordered. They will be delivered shortly...");
            try {
                Thread.sleep(3000);
            } 
            catch (final InterruptedException e) {
                System.out.println(e);
            }
            this.partsPool = this.partsPool + order;
            System.out.println("Parts have been restocked. There are now " + getPartsCount() + " parts in stock.");
        }
    }

    public void moveAircraft(Aircraft aircraft) {
        ArrayList<Integer> parts = aircraft.getPartsNeeded();
        for (int i = 0; i < parts.size(); i++) {
            if ((robots).get(parts.get(i)).getWorkingAircraft() == null) {
                (robots).get(parts.get(i)).setWorkingAircraft(aircraft);
                aircraft.getPartsNeeded().remove(i);
                break;
            }
            else {
                System.out.println("Robot " + parts.get(i) + " is busy. Assigning to next Robot.");
            }
        } 
        
    }

    public synchronized void waitingArea() {
        // move robot from waiting area to wait for parts
    }


    
}