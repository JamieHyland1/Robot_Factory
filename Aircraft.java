public class Aircraft{

    private String id;
    private int parts_needed;

    public Aircraft(String id, int parts_needed){
        //default constructor
        this.id = id;
        this.parts_needed = parts_needed;
    }
    
    public String getId() {
        return id;
    }

    public int getPartsNeeded() {
        return parts_needed;
    }

    public String toString() {
        return "Aircraft: " + getId() + " needs " + getPartsNeeded() + " parts.";
    }
}