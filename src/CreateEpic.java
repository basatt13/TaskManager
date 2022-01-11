import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

    public class CreateEpic {
    public static void main(String[] args) {
        ManagerForTasks managerForTasks = new ManagerForTasks();
        generateNumberEpic(managerForTasks.allTasks);
    }

    public static void createEpic(HashMap<Integer, Task> allTasks) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите название эпика");
        String name = scan.nextLine();
        System.out.println("Введите описание эпика ");
        String details = scan.nextLine();
        CreateTasks.showListtasks(allTasks);
        int numberEpic =  generateNumberEpic(allTasks);
        String status = "NEW";
        HashMap<Integer, Object> subTask = new HashMap<>();
        Epic epic = new Epic(numberEpic, name, details, status, subTask);
        addEpic(epic, allTasks);
    }

    public static void addEpic(Epic e, HashMap<Integer, Task> allTasks){
        allTasks.get(e.ID/100).getForSubtask().put(e.ID,e);
    }

    public static int generateNumberEpic(HashMap<Integer, Task> allTasks) {

        System.out.println("Введите номер задачи для которой создается эпик");
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                int nametask= sc.nextInt();
                return nametask * 100 + allTasks.get(nametask).getForSubtask().size() + 1;
            } catch (InputMismatchException e) {
                System.out.println("Введите корреткно число");
            }catch (NullPointerException n){
                System.out.println("Задачи, с указанным Вами номером не существует. Вот список задач");
                CreateTasks.showListtasks(allTasks);
                System.out.println("Введите корректный номер задачи");
            }
        }
    }

    public static void showListEpic(HashMap<Integer, Task> alltasks) {
        System.out.println("Список всех эпиков");
        for (Object k : alltasks.keySet()) {
            for (Object j: alltasks.get(k).getForSubtask().keySet())
                System.out.println(alltasks.get(k).getForSubtask().get(j).toString());
        }
    }
}

