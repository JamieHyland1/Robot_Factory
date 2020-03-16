import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main{

    private static Queue<Aircraft> aircrafts;

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        int choice  = -1;
        aircrafts = new ArrayDeque<Aircraft>();

        p("|============================================================================|");
        p("|            Welcome to Hyland & Monahan Aircraft Manufacturing              |");
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
                    Random rn = new Random();
                    //randomly sets aircraft's parts needed from 1 - 10 parts
                    Aircraft aircraft = new Aircraft("Aircraft 1", rn.nextInt(10)+1);
                    aircrafts.add(aircraft);
                    System.out.println(aircrafts);
                break;
                case 5:
                    p("Goodbye!");
                break;
                default:
                    p("That isnt a valid option!");
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

    static void printMenu(){
        p("|----------------------------------------------------------------------------------------|");
        p("|1.Start the manufacturing process                                                       |");
        p("|5.Close the system                                                                      |");
        p("|----------------------------------------------------------------------------------------|");
    }
}
