import java.util.*;

public class CreateTasks {

    public static void createTask(HashMap<Integer, Task> allTasks) {
        System.out.println("Введите название задачи");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberTask = generateNumberTask(allTasks);
        String status = "NEW";
        HashMap<Integer,Object> subTask = new HashMap<>();
        Task task = new Task(numberTask, name, details, status, subTask);
        addTasks(task, allTasks);
    }

    public static void addTasks(Task o, HashMap<Integer, Task> allTasks){
        allTasks.put(o.ID,o);
    }

    public static void showListtasks(HashMap<Integer, Task> allTasks) {
        System.out.println("Список всех задач");
        for (int task : allTasks.keySet()) {
            System.out.println(allTasks.get(task).toString());
            for(int epic: allTasks.get(task).getForSubtask().keySet()){
                System.out.println(allTasks.get(task).getForSubtask().get(epic).toString());
            }
        }
    }


    public static int generateNumberTask(HashMap<Integer, Task> allTasks) {
        return allTasks.size() + 1;
    }
}

