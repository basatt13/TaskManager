package tasks;
import controller.Manager;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Task {
    int ID;
    private String name;
    private String details;
    private String status;

    public Task(int ID, String name, String details, String status) {
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

    public String getStatus() {
        return status;
    }

    public static int generateNumberTask(HashMap<Integer, Task> allTasks) {
        return allTasks.size() + 1;
    }

    public static void updateTask(HashMap<Integer, Task> allTasks) {
        Scanner scanner = new Scanner(System.in);
        Manager.showListtasks(allTasks);
        System.out.println("Введите ID задачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (Task k : allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название задачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание задачи ");
                String details = scan.nextLine();
                Task task = new Task(ID, name, details, k.getStatus());
                Manager.addTasks(task, allTasks);
            }
        }
    }
}

