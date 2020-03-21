public class Robot extends Thread {

    private int id;
    private int installAmount;
    private Factory factory;
    private Aircraft workingAircraft;
    private boolean active = true;

    public Robot(Factory factory, final int id) {
        this.factory = factory;
        this.id = id;
        this.installAmount = 1 + (int)Math.floor(Math.random()*10);
        this.workingAircraft = null;
    }

    public int getID() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public synchronized void setWorkingAircraft(Aircraft aircraft) {
        this.workingAircraft = aircraft;
        this.notify();
    }

    public Aircraft getWorkingAircraft() {
        return this.workingAircraft;
        
    }

    public int getInstallAmount() {
        return this.installAmount;
    }

    public void getParts(Robot robot) {
        factory.getOperator().takeParts(robot);
        this.installAmount = 5;
    }

    public String toString() {
        return "Robot: " + getID();
    }

    public void returnToQueue(){
        System.out.println(this.toString() + " is returning " + this.workingAircraft.getId() + " to the operator");
        for(int i = 0; i < this.workingAircraft.getPartsNeeded().size(); i ++){
            if(this.workingAircraft.getPartsNeeded().get(i) == this.getID()) this.workingAircraft.getPartsNeeded().remove(i);
        }
        System.out.println(this.workingAircraft.getId() + " needs these parts " + this.workingAircraft.getPartsNeeded().toString());
        factory.getOperator().moveAircraft(this.workingAircraft);
    }

    // main logic for robot goes in here
    // not sure if this is correct? I dont think we can supply the parts variable at
    // runtime as java is pass by value

    public void run() {
        while (this.active) {
            if (this.workingAircraft == null) {
                System.out.println(this.getID() + " is waiting for work!");
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println(this.toString() + " is working on Aircraft " + this.workingAircraft.getId());
                while (this.installAmount > 0) {
                    System.out.println(" " + this.toString() + " :*clank* *clank*");
                    this.installAmount --;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.getID() + " has finished working on aircraft");
                this.returnToQueue();
                this.workingAircraft = null;
            }
        }
    }
}