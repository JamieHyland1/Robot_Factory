import java.util.Scanner;

public class Main extends Thread {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args){

        String choice  = " ";

        p("|============================================================================|");
        p("|            Welcome to Hyland & Monahan Aircraft Manufacturing              |");
        p("|                                                                            |");
        p("|           Please press Enter to start the manufactoring process.           |");
        p("|============================================================================|");

        while(!choice.equals("")){
            choice = in.nextLine();
            System.out.println(choice);
            switch(choice){
                case "":
                    p("Starting the system...");
                    //initialise system
                    Factory f = new Factory();
                    f.setup();
                break;
                default:
                    p("That isn't a valid option!");
                    break;
            }
        }
        in.close();
    }
   
    //helper method that uses polymorphism to print out anything you supply to it,
    //this will be removed with a find and replace when we submit but just makes it easier 
    //printing out stuff
    public static void p(Object o){
        System.out.println(o);
    }
    
}
