import java.util.ArrayList;

public class Aircraft{

    private String id;
    //private ArrayList<Integer> parts_needed = new ArrayList<Integer>();
    private int parts_needed;

    public Aircraft(String id, int parts_needed){
        //default constructor
        this.id = id;
        this.parts_needed = parts_needed;
    }
    
    public String getId() {
        return id;
    }

    public Boolean installParts(){
        if(this.parts_needed > 0) 
        {
            this.parts_needed--;
            return true;
        }
        else {
            System.out.println("no parts needed");
        }
        return false;
    }

    public int getPartsNeeded() {
        return parts_needed;
    }

    public String toString() {
        return "Aircraft: " + getId() + " needs work on " + getPartsNeeded();
    }
}