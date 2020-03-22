import java.util.ArrayList;
import java.util.Random;

public class Aircraft{

    private String id;
    private ArrayList<Integer> parts_needed = new ArrayList<Integer>();
    private Random rn = new Random();

    public Aircraft(String id){
        //default constructor
        this.id = id;
        //randomly sets aircraft's need work array from 0 - 9
        for (int i = 0; i < 10; i++) {
            //50/50 chance
            if (rn.nextBoolean()) {
                parts_needed.add(i);
            }
        }
        // On the very low chance the foor loop adds nothing
        if (parts_needed.size() == 0) {
            parts_needed.add(0);
        }
    }
    
    public String getID() {
        return id;
    }

    public ArrayList<Integer> getPartsNeeded() {
        return parts_needed;
    }

    public String toString() {
        return "Aircraft: " + getID() + " needs work on " + getPartsNeeded();
    }
}