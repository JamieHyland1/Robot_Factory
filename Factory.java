import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.InputMismatchException;

public class Factory {

    private Queue<Aircraft> aircrafts;
    private ArrayList<Robot> robots;
    private Operator operator;

    private static ExecutorService threadPool;

    private Scanner in = new Scanner(System.in);
    
    public Factory() {
        if (threadPool == null) {
            threadPool = Executors.newFixedThreadPool(5);
        }
        aircrafts = new ArrayDeque<Aircraft>();
        robots = new ArrayList<Robot>();
        operator = new Operator(this);
    }

    public void setup() {
        for(int i = 0; i < 10; i++){
            robots.add(new Robot(this,i));
            (robots.get(i)).start();
        }
        commands();
        threadPool.shutdown();
    }

    public void doWork() {
        execute(() -> {
            synchronized (this) {
                Aircraft aircraft = aircrafts.poll();
                operator.moveAircraft(aircraft);
            }
        });
    }

    public ArrayList<Robot> getRobots() {
        return this.robots;
    } 

    public Operator getOperator() {
        return this.operator;
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
                    System.out.println("What is the name of the Aircraft?");
                    String aircraftName = in.nextLine();
                    Aircraft aircraft = new Aircraft(aircraftName);
                    aircrafts.add(aircraft);
                    doWork();
                    menu();
                break;
                case 2:
                    System.out.println("How many Aircrafts would you like to order?");
                    int aircraftNum;
                    while (true) {
                        try {
                            aircraftNum = in.nextInt();
                            break;
                        }
                        catch (InputMismatchException e) {
                            System.err.println("Wrong input! Please only input Integers...");
                            in.nextLine();
                            System.out.println("How many Aircrafts would you like to order?");
                        }
                    }
                    in.nextLine(); 
                    System.out.println("What are the name of the Aircrafts?");
                    String aircraftsName = in.nextLine();
                    for (int i = 0; i < aircraftNum; i++) {
                        Aircraft a = new Aircraft(aircraftsName + "-" + Integer.toString(i));
                        aircrafts.add(a);
                        doWork();
                    }
                    menu();
                break;
                case 3:
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
        System.out.println("|     2. Build Mulitple Aircrafts                                                        |");
        System.out.println("|     3. Buy Parts                                                                       |");
        System.out.println("|     5. Close the system                                                                |");
        System.out.println("|----------------------------------------------------------------------------------------|");
    }
}