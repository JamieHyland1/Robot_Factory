import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class Operator {
    
    private ArrayList<Robot> robots = new ArrayList<Robot>();
    private Factory factory;
    private int partsPool = 0;
    private Queue<Integer> robotsWaitingList;

    public Operator(Factory factory){
        this.factory = factory;
        this.partsPool = 100;
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

    public void moveAircraft() {
        // move aircraft to robot
    }

    public synchronized void waitingArea() {
        // move robot from waiting area to get parts
    }


    
}