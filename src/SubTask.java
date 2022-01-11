import java.util.HashMap;

public class SubTask extends Epic {


    public SubTask(int ID, String name, String details, String status, HashMap<Integer, Object> forSubtask) {
        super(ID, name, details, status, forSubtask);
    }

    @Override
    public String toString() {
        return ID  +
                " - " + getName() + ", status:" +
                getStatus();
    }
}



