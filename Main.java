import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class Main extends Thread {

    private static Scanner in = new Scanner(System.in);

    public static void main(final String[] args) {
        final Queue<Aircraft> aircrafts = new LinkedList<>();
        boolean operating = true;
        for (int i = 0; i <= 20; i++) {
            aircrafts.add(new Aircraft("Aircraft: " + i, 5));
        }
        final String choice = " ";
        final Robot r = new Robot("Jamie");
        System.out.println(r.isAlive());
        r.StartOperating(); //start the main robot thread

        while(operating){
            while (!aircrafts.isEmpty()) {
                if(r.workingAircraft == null){
                    r.setWorkingAircraft(aircrafts.poll(),aircrafts.size());
                }else if(r.workingAircraft != null){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Robot working");
                }
            }
            if(!r.isAlive()){
                operating = false;
            }
        }
        System.out.println("Finished Production");
        // p("|============================================================================|");
        // p("|            Welcome to Hyland & Monahan Aircraft Manufacturing              |");
        // p("|                                                                            |");
        // p("|           Please press Enter to start the manufactoring process.           |");
        // p("|============================================================================|");

        // while(!choice.equals("")){
        //     choice = in.nextLine();
        //     switch(choice){
        //         case "":
        //             p("Starting the system...");
        //             //initialise system
        //             Factory f = new Factory();
        //             f.setup();
        //         break;
        //         default:
        //             p(choice + " isn't a valid option!");
        //             break;
        //     }
        // }
      
    }

    // helper method that uses polymorphism to print out anything you supply to it,
    // this will be removed with a find and replace when we submit but just makes it
    // easier
    // printing out stuff
    public static void p(final Object o) {
        System.out.println(o);
    }
    
}
