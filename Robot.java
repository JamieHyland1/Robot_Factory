public class Robot extends Thread {

    private int id;
    private int installAmount;
    private final Factory factory;
    private Operator op;
    private Aircraft workingAircraft;
    int numOfAircrafts;

    public Robot(final Factory factory, final int id, Operator op) {
        this.factory = factory;
        this.id = id;
        this.installAmount = 5;
        this.workingAircraft = null;
        Main.log("Starting Robot " + this.getID());
        this.op = op;
        numOfAircrafts = 0;
    }

    public int getID() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public synchronized void setWorkingAircraft(final Aircraft aircraft) {
        this.workingAircraft = aircraft;

    }

    public Aircraft getWorkingAircraft() {
        return this.workingAircraft;

    }

    public int getInstallAmount() {
        return this.installAmount;
    }

    public synchronized void getParts(final Robot robot) {
        factory.getOperator().takeParts(robot);
    }

    public String toString() {
        return "Robot: " + getID();
    }

    public void returnToQueue() {
        try {
            for (int i = 0; i < this.workingAircraft.getPartsNeeded().size(); i++) {
                if (this.workingAircraft.getPartsNeeded().get(i) == this.getID())
                    this.workingAircraft.getPartsNeeded().remove(i);
                    this.op.enterWaiting(this.getID()); //enter waiting list now
            }
            this.op.moveAircraft(this.workingAircraft);
            this.workingAircraft = null;
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void run() {
        while (!this.op.checkProduction()) {
            if(this.workingAircraft == null ) {
                    try {
                        this.workingAircraft = this.op.getAircraft(this.id);
                    } 
                    catch (Exception ex) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                          
                            e.printStackTrace();
                        }
                    }
            }
            else if(this.workingAircraft != null) {
                getParts(this);
                Main.log(this.toString() + " is working on Aircraft " + this.workingAircraft.getID());
                Main.log(this.toString() + " :*clank* *clank*");
                this.numOfAircrafts++;
                while (this.installAmount > 0) {
                    this.installAmount--;
                    try {
                        Thread.sleep(1000);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
               
                }
                Main.log(this.getID() + " has finished working on aircraft " + this.workingAircraft.getID());
                this.installAmount = 5;
                this.returnToQueue();    
            }
        }
        Main.log(this.toString() + " worked on " + this.numOfAircrafts + " aircrafts");
        Main.log(this.toString() + " is shutting down");
    }
}