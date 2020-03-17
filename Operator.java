import java.util.ArrayList;

public class Operator {
    
    private ArrayList<Robot> robots = new ArrayList<Robot>();
    private int partsPool = 0;

    public Operator(){
        for(int i = 0; i < 10; i++){
            robots.add(new Robot("'" + i + "'"));
            (robots.get(i)).start();
        }
        // start with 100 parts
        this.partsPool = 100;
    }
    
    public int getPartsCount() {
        return this.partsPool;
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
            System.out.println("Parts have been restocked. There are now " + getPartsCount() + " parts remaining.");
        }
    }

    public void moveAircraft() {
        //
    }
    //Add method order more parts for the robots
    //Add method to move aircraft to other Robots
    
}