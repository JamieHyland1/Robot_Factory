public class Robot extends Thread {

    private int id;
    private int installAmount;
    private final Factory factory;
    private Operator op;
    private Aircraft workingAircraft;

    public Robot(final Factory factory, final int id, Operator op) {
        this.factory = factory;
        this.id = id;
        this.installAmount = 1 + (int) Math.floor(Math.random() * 10);
        this.workingAircraft = null;
        Main.log("Starting Robot " + this.getID());
        this.op = op;
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
                        Main.log(this.toString() + " has recieved aircraft " + this.workingAircraft.getID());
                    } 
                    catch (Exception ex) {
                    Main.log(this.toString() + " is waiting");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
            }
            else if(this.workingAircraft != null) {
                getParts(this);
                Main.log(this.toString() + " is working on Aircraft " + this.workingAircraft.getID());
                while (this.installAmount > 0) {
                    Main.log(this.toString() + " :*clank* *clank*");
                    this.installAmount--;
                    try {
                        Thread.sleep(1000);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
               
                }
                Main.log(this.getID() + " has finished working on aircraft " + this.workingAircraft.getID());
                this.installAmount = 1 + (int) Math.floor(Math.random() * 10);
                this.returnToQueue();    
            }
        }
        Main.log(this.toString() + " is shutting down");

    }
}