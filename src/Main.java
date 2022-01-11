import java.util.HashMap;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        ManagerForTasks managerForTasks = new ManagerForTasks();
        HashMap<Integer, Task> collection = managerForTasks.allTasks;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            Menu.comands();
            int command = scanner.nextInt();
            if (command > 0 && command <= 13) {
                if (command == 1) {
                    CreateTasks.createTask(collection);
                } else if (command == 2) {
                    CreateEpic.createEpic(collection);
                } else if (command == 3) {
                    CreateSubTask.createSubTask(collection);
                } else if (command == 4) {
                    CreateTasks.showListtasks(collection);
                } else if (command == 5) {
                    CreateEpic.showListEpic(collection);
                } else if (command == 6) {
                    CreateSubTask.showListSubTaskForEpic(collection);
                } else if (command == 7) {
                    ManagerForTasks.getSomeTaskbyID(collection);
                } else if (command == 8) {
                    ManagerForTasks.updateTasks(collection);
                } else if (command == 9) {
                    ManagerForTasks.removetaskbyID(collection);
                } else if (command == 10) {
                    ManagerForTasks.cleanalltasks(collection);
                } else if (command == 11) {
                    Status.StatusProgress(collection);
                    Status.updateStatusForEpic(collection);
                    Status.updateStatusForTask(collection);
                } else if (command == 12) {
                    Status.StatusDone(collection);
                    Status.updateStatusForEpic(collection);
                    Status.updateStatusForTask(collection);
                }
            } else if (command == 131313) {
                break;
            }
        }
    }
}

