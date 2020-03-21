public class Robot extends Thread {

    private String id;
    private int installAmount;
    private Factory factory;
    private Aircraft workingAircraft;

    public Robot(Factory factory, final String id) {
        this.factory = factory;
        this.id = id;
        this.installAmount = 5;
        this.workingAircraft = null;
        System.out.println("Starting Robot " + this.getID());
    }

    public String getID() {
        return this.id;
    }

    public void setID(final String id) {
        this.id = id;
    }

    public synchronized void setWorkingAircraft(Aircraft aircraft) {
        this.workingAircraft = aircraft;
    }

    public Aircraft getWorkingAircraft() {
        return this.workingAircraft;
    }

    public int getInstallAmount() {
        return this.installAmount;
    }

    public synchronized void getParts(Robot robot) {
        factory.getOperator().takeParts(robot);
    }

    public String toString() {
        return "Robot: " + getID();
    }


    // main logic for robot goes in here
    // not sure if this is correct? I dont think we can supply the parts variable at runtime as java is pass by value
    
    public void run(Aircraft a, int parts) {
        System.out.println("Running current Robot...");
        System.out.println("*robot noises*");
        synchronized (this) {
            parts -= this.installAmount;
            System.out.println("Robot " + this.id + " Installing " + this.installAmount + " parts on aircraft");
            long timeToInstall = 100 * this.installAmount; //time taken to install is based off how many parts are needed 
            try {
                this.sleep(timeToInstall);
            } catch (InterruptedException e) {
                System.out.println("Robot " + this.id + " was unable to install parts");
                e.printStackTrace();
            }
        }
    }

    public void waiting() {
        System.out.println("Robot " + this.getID() + " is waiting for another Aircraft");
        // wait for 1 second
        try {
            this.sleep(1000);
        } catch (final InterruptedException e) {
           System.out.println(e);
       }
    }


    //Add method to notify system its done installing parts on aircraft
    
}