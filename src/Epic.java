import java.util.HashMap;

public class Epic extends Task {


    public Epic(int ID, String name, String details, String status, HashMap<Integer, Object> forSubtask) {
        super(ID, name, details, status, forSubtask);
    }

    @Override
    public String toString() {
        return ID  +
                " - " + getName() + ", status:" +
                getStatus();
    }
}



