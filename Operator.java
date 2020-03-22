import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.ArrayDeque;

public class Operator extends Thread {

    private Factory factory;
    private int partsPool;
    private ArrayList<Robot> robots;
    private Queue<Robot> robotsWaitingList;
    private HashSet<Integer> robotsInWaiting;
    private boolean operating = true;

    public Operator(Factory factory) {
        this.factory = factory;
        this.partsPool = 5;
        this.robots = factory.getRobots();
        this.robotsWaitingList = new ArrayDeque<Robot>();
        this.robotsInWaiting = new HashSet<Integer>();
        for(int i = 0; i < 10; i++){
            robotsInWaiting.add(i);
        }
    }

    public int getPartsCount() {
        return this.partsPool;
    }

    public synchronized boolean getAircraftsQueue(){
        return this.factory.getAirCrafts().isEmpty();
    }

    public void enterWaiting(int id){
        if(!this.robotsInWaiting.contains(id)){
            System.out.println("Removing robot" + id + " from waiting list");
            this.robotsInWaiting.add(id);
            System.out.println("Current waiting List: " + this.robotsInWaiting.toString());
        }
    }

    public  void leaveWaiting(int id){
        System.out.println("Removing robot" + id + " from waiting list");
        this.robotsInWaiting.remove(id);
        System.out.println("Current waiting List: " + this.robotsInWaiting.toString());
    }

    public synchronized boolean checkProduction() {
        System.out.println(this.robotsInWaiting.toString());
        System.out.println(this.factory.getAirCrafts().isEmpty());
        return (10 == this.robotsInWaiting.size() && this.factory.getAirCrafts().isEmpty());
    }

    public void takeParts(Robot robot) {
        synchronized (this) {
            int partsNeeded = robot.getInstallAmount();
            if (this.partsPool >= partsNeeded) {
                this.partsPool -= partsNeeded;
                System.out.println("Parts given to Robot " + robot.getID() + ". There now are " + this.partsPool + " parts remaining.");
            } else {
                if (!robotsWaitingList.contains(robot)) {
                    System.out.println("Not enough parts avaiable for " + robot + ". Waiting for parts...");
                    robotsWaitingList.add(robot);
                    waitingArea(this.partsPool);
                }
            }
        }
    }

    public synchronized Aircraft getAircraft(int id) {
        Aircraft a = this.factory.getAirCrafts().poll();
        if(a.getPartsNeeded().contains(id)){
            this.leaveWaiting(id); // remove the robot from the waitingList
            return a; //only give a robot an aircraft if it has the desired parts
        }
        this.factory.getAirCrafts().add(a);
        return null;
    }

    public void moveAircraft(Aircraft aircraft) {
        synchronized (this) {
            ArrayList<Integer> parts = aircraft.getPartsNeeded();
            System.out.println(aircraft.getID() + ": " + parts.toString());
            if (parts.isEmpty()) {
                System.out.println("Aircraft is finished assembly. Removing from factory.");
                System.out.println("Robots are finished working: " + this.checkProduction());
                return;
            }
            System.out.println(this.factory.getAirCrafts().size());
            this.factory.getAirCrafts().add(aircraft);
            System.out.println("Robots are finished working: " + this.checkProduction());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
               
        }
        
    }
    public void orderParts(int order) {
        if (order > 0) {
            System.out.println("\nNew parts ordered. They will be delivered shortly...");
            try {
                Thread.sleep(1500);
            } 
            catch (final InterruptedException e) {
                System.out.println(e);
            }
            this.partsPool = this.partsPool + order;
            System.out.println("Parts have been restocked. There are now " + getPartsCount() + " parts in stock.\n");
        }
    }

    public  void waitingArea(int parts) {
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
    }


    
}