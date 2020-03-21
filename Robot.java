public class Robot extends Thread {

    private String id;
    private int installAmount;
    // private Factory factory;
    public Aircraft workingAircraft;
    private int aircraftsInWaiting;
    private boolean active = false;

    public Robot(String id) {
        this.id = id;
        this.workingAircraft = null;
    }

    // public Robot( final String id) {
    // // this.factory = factory;
    // this.id = id;
    // this.installAmount = (int) Math.floor(Math.random() * (5) + 1);
    // this.workingAircraft = null;
    // }

    public void StartOperating() {
        this.active = true;
        this.start();
    }

    public String getID() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    // assign a robot an aircraft
    public synchronized void setWorkingAircraft(Aircraft aircraft, int aircraftsInWaiting) {
        this.aircraftsInWaiting = aircraftsInWaiting;
        this.workingAircraft = aircraft;
        notify(); //if the thread is asleep notify it that it was given an aircraft
    }

    // supply the operator with an aircraft
    public Aircraft getWorkingAircraft() {
        Aircraft a = this.workingAircraft;
        this.workingAircraft = null;
        return a;
    }

    // finish the working thread.
    public void FinishOperations() {
        System.out.println("Robot " + this.getID() + " shutting down...");
        this.active = false;
    }

    // this.active just makes sure the robot runs for the entirety of the program
    // still need to add code to get more parts
    public void run() throws NullPointerException {
        System.out.println("Starting up");
        while (this.active) {
            if (this.workingAircraft == null && this.aircraftsInWaiting > 0) {
                System.out.println(this.getID() + ": No aircraft supplied, going to sleep...zZzZzZz");
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else if(this.aircraftsInWaiting > 0) {
                System.out.println("Waking up!");
              //  this.getWorkingAircraft();
                synchronized (this.workingAircraft) {
                    System.out.println("Robot " + this.getID() + " installing parts on aircraft " + this.workingAircraft.getId());
                    System.out.println(this.workingAircraft.getPartsNeeded() + " parts left to install");
                    while(this.workingAircraft.getPartsNeeded() > 0){
                        try {
                            this.workingAircraft.installParts();
                            System.out.println("*clank clank*");
                            System.out.println(this. workingAircraft.getPartsNeeded() + " parts needed left to install on " + this.workingAircraft.getId()); 
                            this.sleep(500);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    System.out.println(this.getID() + " finished working on " + this.workingAircraft.getId());
                    this.getWorkingAircraft();
                }              
            }else{
                System.out.println("No aircrafts left");
                this.FinishOperations();
            }
           
        }
    }
}

