import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.InputMismatchException;

public class Factory {

    private ArrayBlockingQueue<Aircraft> aircrafts;
    private final Operator operator;
    private static ExecutorService threadPool;
    private final ArrayList<Robot> robots;
    private final ArrayList<Future> runningRobots;
    private final Scanner in = new Scanner(System.in);

    public Factory() {
        if (threadPool == null) {
            threadPool = Executors.newFixedThreadPool(10);
        }

        aircrafts = new ArrayBlockingQueue<Aircraft>(1000);
        robots = new ArrayList<Robot>();
        operator = new Operator(this);
        runningRobots = new ArrayList<Future>();

    }

    public void setup() {
        for (int i = 0; i < 10; i++) {
            final Robot r = new Robot(this, i, operator);
            //robots.add(r);
            runningRobots.add(threadPool.submit(r));
        }
        // threadPool.shutdown();
    }

    public ArrayList<Future> getWorkingRobots(){
        return this.runningRobots;
    }

    public void doWork() {
      //  this.operator.moveAircraft(aircrafts.poll());
        // for (int i = 0; i < robots.size(); i++) {
        //     if (robots.get(i).getWorkingAircraft() == null) {
        //         if (!aircrafts.isEmpty()) {
        //             final Aircraft a = aircrafts.poll();
        //             robots.get(i).setWorkingAircraft(a);
        //             notifyAll();
        //         } else {
        //             Main.log("Theres no aircrafts left :(");
        //         }
        //     }
        // }
    }

    public Queue<Aircraft> getAirCrafts() {
        return this.aircrafts;
    }

    public ArrayList<Robot> getRobots() {
        return this.robots;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public synchronized void execute(final Runnable r) {
        threadPool.execute(r);
    }

    public void commands() {
        int choice = 0;

        menu();

        while (choice != 5) {
            try {
                choice = in.nextInt();
            } catch (final InputMismatchException e) {
                System.err.println("Wrong input! Please only input Integers...");
            }
            in.nextLine();
            switch (choice) {
                case 1:
                    Main.log("How many Aircrafts would you like to order?");
                    int aircraftNum;
                    while (true) {
                        try {
                            aircraftNum = in.nextInt();
                            break;
                        } catch (final InputMismatchException e) {
                            System.err.println("Wrong input! Please only input Integers...");
                            in.nextLine();
                            Main.log("How many Aircrafts would you like to order?");
                        }
                    }
                    in.nextLine();
                    Main.log("What are the name of the Aircrafts?");
                    final String aircraftsName = in.nextLine();
                    for (int i = 0; i < aircraftNum; i++) {
                        final Aircraft a = new Aircraft(aircraftsName + "-" + Integer.toString(i));
                        aircrafts.add(a);
                    }
                    setup();
                    //keeps printing out an array?
                    //while(!this.operator.checkProduction()){Main.log("");}
                    threadPool.shutdown();
                    choice = 5;
                break;
                case 5:
                    Main.log("Goodbye!");
                break;
                default:
                    Main.log("That isn't a valid option!");
                    break;
            }
        }
    }

    public void menu(){
        Main.log("|----------------------------------------------------------------------------------------|");
        Main.log("|  What would you like to do :                                                           |");
        Main.log("|     1. Select number of aircraft                                                       |");
        Main.log("|     5. Close the system                                                                |");
        Main.log("|----------------------------------------------------------------------------------------|");
    }
}