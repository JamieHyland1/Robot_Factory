import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        ArrayList<Robot> robots = new ArrayList<Robot>();
        int choice  = -1;
        p("|============================================================================|");
        p("|            Welcome to Hyland & Monohan Aircraft Manufacturing              |");
        p("|                                                                            |");
        p("| Please press the number corrosponding to the task you with to perform:     |");
        p("|============================================================================|");

        while(choice != 5){
            printMenu();
            choice = in.nextInt();
            switch(choice){
                case 1:
                    p("Starting the system...");
                    //initialise system 
                break;
                case 5:
                    p("Goodbye!");
                break;
                default:
                    p("That isnt a valid option!");
                    break;
            }
        }
    }
   
    //helper method that uses polymorphism to print out anything you supply to it,
    //this will be removed with a find and replace when we submit but just makes it easier 
    //printing out stuff
    public static void p(Object o){
        System.out.println(o);
    }

    static void printMenu(){
        p("|----------------------------------------------------------------------------------------|");
        p("|1.Start the manufacturing process                                                       |");
        p("|5.Close the system                                                                      |");
        p("|----------------------------------------------------------------------------------------|");
    }
}
