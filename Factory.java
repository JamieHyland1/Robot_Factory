import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;
import java.util.InputMismatchException;

public class Factory {

    private Queue<Aircraft> aircrafts;
    private ArrayList<Robot> robots;
    private Operator operator;

    private static ExecutorService threadPool;

    private Scanner in = new Scanner(System.in);
    
    public Factory() {
        if (threadPool == null) {
            threadPool = Executors.newFixedThreadPool(2);
        }
        aircrafts = new ArrayDeque<Aircraft>();
        robots = new ArrayList<Robot>();
        operator = new Operator(this);
    }

    public void setup() {
        for(int i = 0; i < 10; i++){
            robots.add(new Robot(this, Integer.toString(i)));
            (robots.get(i)).start();
        }
        commands();
        threadPool.shutdown();
    }

    public void doWork() {
        execute(() -> {
            synchronized (this) {
                operator.moveAircraft(aircrafts.poll());
                // Just to see if robot get assigned an aircraft correctly. Will be removed
                for (Robot r : robots) {
                    System.out.println(r.getID() + ": "+ r.getWorkingAircraft());
                }
                // call function from Robot to do work
                notify();
            }
        });
    }

    public ArrayList<Robot> getRobots() {
        return this.robots;
    } 

    public synchronized void execute(Runnable r) {
        threadPool.execute(r);
    }

    public void commands() {
        int choice  = 0;

        menu();

        while(choice != 5){
            try {
                choice = in.nextInt();
            }
            catch (InputMismatchException e) {
                System.err.println("Wrong input! Please only input Integers...");
            }
            in.nextLine(); 
            switch(choice){
                case 1:
                    Random rn = new Random();
                    //randomly sets aircraft's need work array from 0 - 9
                    ArrayList<Integer> workNeeded = new ArrayList<Integer>();
                    for (int i = 0; i < 10; i++) {
                        if (rn.nextBoolean()) {
                            workNeeded.add(i);
                        }
                    }
                    System.out.println("What is the name of the Aircraft?");
                    String aircraftName = in.nextLine();
                    Aircraft aircraft = new Aircraft(aircraftName, workNeeded);
                    aircrafts.add(aircraft);
                    System.out.println(aircraft);
                    doWork();
                    synchronized (this) {
                        try { wait(); } catch (InterruptedException e) {}
                    }
                    menu();
                break;
                case 2:
                    int parts;
                    System.out.println("How many parts would you like to order?");
                    while (true) {
                        try {
                            parts = in.nextInt();
                            break;
                        }
                        catch (InputMismatchException e) {
                            System.err.println("Wrong input! Please only input Integers...");
                            in.nextLine();
                            System.out.println("How many parts would you like to order?");
                        }
                    }
                    in.nextLine(); 
                    operator.orderParts(parts);
                    menu();
                break;
                case 5:
                    System.out.println("Goodbye!");
                break;
                default:
                    System.out.println("That isn't a valid option!");
                    break;
            }
        }
    }

    public void menu(){
        System.out.println("|----------------------------------------------------------------------------------------|");
        System.out.println("|  What would you like to do :                                                           |");
        System.out.println("|     1. Build Aircraft                                                                  |");
        System.out.println("|     2. Buy Parts                                                                       |");
        System.out.println("|     5. Close the system                                                                |");
        System.out.println("|----------------------------------------------------------------------------------------|");
    }
}