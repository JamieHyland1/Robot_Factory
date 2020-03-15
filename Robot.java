public class Robot extends Thread{

    private String id;
    Thread t;

    public Robot(){
        //default constructor
    }

    public Robot(String id){
        this.id = id;
    }

    public String getID(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public void start(){
        System.out.println("Starting Robot " + this.getID());
        this.run();
    }
    
    //main logic for robot goes in here
    public void run(){
        System.out.println("Running current Robot...");
        System.out.println("*robot noises*");
    }

    public void waiting(){
        System.out.println("Robot " + this.getID() + " is waiting for more parts");
        //wait for 1 second
       try{ this.sleep(1000);}
       catch(InterruptedException e){
           System.out.println(e);
       }
    }

    //Add method to notify system its done installing parts on aircraft
    //Add method to wait if not in service
    
}