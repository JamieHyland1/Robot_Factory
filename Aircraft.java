import java.util.ArrayList;

public class Aircraft{

    private String id;
    private ArrayList<Integer> parts_needed = new ArrayList<Integer>();

    public Aircraft(String id, ArrayList<Integer> parts_needed){
        //default constructor
        this.id = id;
        this.parts_needed = parts_needed;
    }
    
    public String getId() {
        return id;
    }

    public ArrayList<Integer> getPartsNeeded() {
        return parts_needed;
    }

    public String toString() {
        return "Aircraft: " + getId() + " needs work on " + getPartsNeeded();
    }
}