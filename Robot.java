public class Robot extends Thread {

    private int id;
    private int installAmount;
    private final Factory factory;
    private Operator op;
    private Aircraft workingAircraft;
    private final boolean active = true;

    public Robot(final Factory factory, final int id) {
        this.factory = factory;
        this.id = id;
        this.installAmount = 1 + (int) Math.floor(Math.random() * 10);
        this.workingAircraft = null;
        Main.log("Starting Robot " + this.getID());
    }

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
        this.installAmount = 5;
    }

    public String toString() {
        return "Robot: " + getID();
    }

    public void returnToQueue() {
        // Main.log(this.toString() + " is returning " + this.workingAircraft.getID() +
        // " to the operator");
        try {
            for (int i = 0; i < this.workingAircraft.getPartsNeeded().size(); i++) {
                if (this.workingAircraft.getPartsNeeded().get(i) == this.getID())
                    this.op.removeRobot(i);
                this.workingAircraft.getPartsNeeded().remove(i);
                this.op.enterWaiting(this.getID()); // enter waiting list now
            }
            // Main.log(this.workingAircraft.getID() + " needs these parts "+
            // this.workingAircraft.getPartsNeeded().toString());
            this.op.moveAircraft(this.workingAircraft);
            this.workingAircraft = null;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (!this.op.checkProduction()) {
            if (this.workingAircraft == null) {
                if (this.op.hasWork(this.getID())) {
                    try {

                        this.workingAircraft = this.op.getAircraft(this.id);
                        Main.log(this.toString() + " has recieved aircraft " + this.workingAircraft.getID());
                    } catch (Exception ex) {
                        Main.log(this.toString() + " is waiting");
                        try {
                            this.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    Main.log(this.getID() + " isnt needed");
                    Main.log(this.op.getRobotsNeeded());
                    try {
                        this.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            else if(this.workingAircraft != null) {
                 Main.log(this.toString() + " is working on Aircraft " + this.workingAircraft.getID());
                while (this.installAmount > 0) {
                    Main.log(" " + this.toString() + " :*clank* *clank*");
                    this.installAmount--;
                    try {
                        Thread.sleep(1000);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
               
                }
                this.installAmount = 5;
                //TODO://order parts
                Main.log(this.getID() + " has finished working on aircraft " + this.workingAircraft.getID());
                this.returnToQueue();    
            }
        }
        Main.log(this.toString() + " is shutting down");

    }
}