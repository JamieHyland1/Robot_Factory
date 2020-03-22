import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

public class Main extends Thread {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        Path path = Paths.get("output.dat");

        try {
            Files.deleteIfExists(path);
        }
        catch(IOException e) {
            System.out.println(e);
        }

        String choice  = " ";

        log("|============================================================================|");
        log("|            Welcome to Hyland & Monahan Aircraft Manufacturing              |");
        log("|                                                                            |");
        log("|           Please press Enter to start the manufactoring process.           |");
        log("|============================================================================|");

        while(!choice.equals("")){
            choice = in.nextLine();
            switch(choice){
                case "":
                    log("Starting the system...");
                    //initialise system
                    Factory f = new Factory();
                    f.commands();
                break;
                default:
                    log(choice + " isn't a valid option!");
                    break;
            }
        }
        in.close();
    }

    public static void log(String message) { 
        // For outputting to console and to output file
        System.out.println(message);
        try {
            PrintWriter out = new PrintWriter(new FileWriter("output.dat", true), true);
            out.write(message+"\n");
            out.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
      }
}

