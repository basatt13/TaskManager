import java.util.HashMap;
import java.util.Objects;

public class Task {
    int ID;
    private String name;
    private String details;
    private String status;
    private HashMap<Integer,Object> forSubtask;

    public Task(int ID, String name, String details, String status, HashMap<Integer, Object> forSubtask) {
        this.ID = ID;
        this.name = name;
        this.details = details;
        this.status = status;
        this.forSubtask = forSubtask;
    }

    @Override
    public String toString() {
        return "Задача{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return ID == task.ID && Objects.equals(name, task.name) &&
                Objects.equals(details, task.details) &&
                Objects.equals(status, task.status) &&
                Objects.equals(forSubtask, task.forSubtask);

    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, details, status, forSubtask);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<Integer, Object> getForSubtask() {
        return forSubtask;
    }

    public void setForSubtask(HashMap<Integer, Object> forSubtask) {
        this.forSubtask = forSubtask;
    }
}


