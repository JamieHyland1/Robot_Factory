import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.ArrayDeque;

public class Operator extends Thread {

    private Factory factory;
    private int partsPool;
    private Queue<Robot> robotsWaitingList;
    private HashSet<Integer> robotsInWaiting;
    private HashSet<Integer> robotsNeeded;


    public Operator(Factory factory) {
        this.factory = factory;
        this.partsPool = (1 + (int) Math.floor(Math.random() * 10)) * 10;
        this.robotsWaitingList = new ArrayDeque<Robot>();
        this.robotsInWaiting = new HashSet<Integer>();
        this.robotsNeeded = new HashSet<Integer>();
        for(int i = 0; i < 10; i++){
            robotsInWaiting.add(i);
            robotsNeeded.add((i));
        }
        
    }

    public void disperseAircrafts(){
        while(!this.factory.getAirCrafts().isEmpty()){
            Aircraft a = this.factory.getAirCrafts().poll();
            int id = a.getPartsNeeded().get(0);
            Main.log(a.getID() + " is going to Robot " + id);
            this.factory.getQueuesForRobots().get(id).add(a);
        }
    }

    public boolean empyRobotQueues(){
        for(int i = 0; i < 10; i++){
            if(this.factory.getQueuesForRobots().get(i).isEmpty() == false) return false;
        }
        return true;
    }

    public int getPartsCount() {
        return this.partsPool;
    }

    public boolean hasWork(int id){
        return this.robotsNeeded.contains(id);
    }

    public synchronized boolean getAircraftsQueue(){
        return this.factory.getAirCrafts().isEmpty();
    }

    public void enterWaiting(int id){
        if(!this.robotsInWaiting.contains(id)){
            this.robotsInWaiting.add(id);
        }
    }


    public  void leaveWaiting(int id){
        this.robotsInWaiting.remove(id);
    }

    public synchronized boolean checkProduction() {

        return (10 == this.robotsInWaiting.size() && this.factory.getAirCrafts().isEmpty() && this.empyRobotQueues());
    }

    public synchronized void removeRobot(int i) {
        this.robotsNeeded.remove(i);
    }

    public void takeParts(Robot robot) {
        synchronized (this) {
            int partsNeeded = robot.getInstallAmount();
            if (this.partsPool >= partsNeeded) {
                this.partsPool -= partsNeeded;
                Main.log("Parts given to Robot " + robot.getID() + ". There now are " + this.partsPool + " parts remaining.");
            } else {
                if (!robotsWaitingList.contains(robot)) {
                    Main.log("Not enough parts avaiable for " + robot + ". Waiting for parts...");
                    robotsWaitingList.add(robot);
                    waitingArea(this.partsPool);
                }
            }
        }
    }

    public synchronized Aircraft getAircraft(int id) {
        if(!this.factory.getQueuesForRobots().get(id).isEmpty()){
            Main.log("Robot " + id + " has this main aircrafts" + this.factory.getQueuesForRobots().get(id).toString());
            Aircraft a = this.factory.getQueuesForRobots().get(id).poll();
            HashSet<Integer> s = new HashSet<Integer>(a.getPartsNeeded());
            this.robotsNeeded.addAll(s);
            
                this.leaveWaiting(id); // remove the robot from the waitingList
                return a; //only give a robot an aircraft if it has the desired parts
        }
      //  this.factory.getAirCrafts().add(a);
        return null;
    }

    public void moveAircraft(Aircraft aircraft) {
        synchronized (this) {
            ArrayList<Integer> parts = aircraft.getPartsNeeded();
            Main.log(aircraft.getID() + ": " + parts.toString());
            if (parts.isEmpty()) {
                Main.log("Aircraft is finished assembly. Removing from factory.");
                return;
            }
            this.factory.getAirCrafts().add(aircraft);
            this.disperseAircrafts();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void orderParts(int order) {
        if (order > 0) {
            Main.log("\nNew parts ordered. They will be delivered shortly...");
            try {
                Thread.sleep(1500);
            } 
            catch (final InterruptedException e) {
                System.out.println(e);
            }
            this.partsPool = this.partsPool + order;
            Main.log("Parts have been restocked. There are now " + getPartsCount() + " parts in stock.\n");
        }
    }

    public String getRobotsNeeded(){
        return this.robotsNeeded.toString();
    }

    public  void waitingArea(int parts) {
        synchronized (this) {
            int partsNow = parts;
            if (partsPool <= 10){
                orderParts((1 + (int) Math.floor(Math.random() * 10)) * 10);
            }
            while (!robotsWaitingList.isEmpty()) {
                if (this.partsPool != partsNow) {
                    takeParts(robotsWaitingList.poll());
                }
            }
        }
    }


    
}