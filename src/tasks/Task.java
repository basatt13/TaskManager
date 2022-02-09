package tasks;
import controller.Status;
import java.util.HashMap;
import java.util.Objects;


public class Task {
    int ID;
    private String name;
    private String details;
    private Status status;

    public Task(int ID, String name, String details, Status status) {
        this.ID = ID;
        this.name = name;
        this.details = details;
        this.status = status;
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
        return getID() == task.getID() && Objects.equals(getName(), task.getName()) &&
                Objects.equals(getDetails(), task.getDetails()) &&
                Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        Objects.hash(getID(), getName(), getDetails());
        int result = Objects.hash(getID(),getName(),getDetails());
        result = result*31;
        return result;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public Status getStatus() {
        return status;
    }

}


