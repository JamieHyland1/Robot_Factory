import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Aircraft{

    private final String id;
    private final ArrayList<Integer> parts_needed = new ArrayList<Integer>();
    private final Random rn = new Random();

    public Aircraft(final String id) {
        //default constructor
        this.id = id;
        //randomly sets aircraft's need work array from 0 - 9
        for (int i = 0; i < 5; i++) {
            //50/50 chance
            if (rn.nextBoolean()) {
                parts_needed.add((int)Math.floor(Math.random()*10));
            }
        }
        Collections.shuffle(this.parts_needed);
        Main.log(this.parts_needed.toString());
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
        return "\n Aircraft: " + getID() + " needs work on " + getPartsNeeded();
    }
}