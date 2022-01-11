import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CreateSubTask {

    public static void createSubTask(HashMap<Integer, Task> allTasks) {
        Scanner scan = new Scanner(System.in);
        CreateEpic.showListEpic(allTasks);
        int numbersubTask = (int) generateNumberEpic(allTasks);
        int numEpic = numbersubTask/100000;
        int numTask = numEpic/100;
        System.out.println("Введите название подзадачи");
        String name = scan.nextLine();
        System.out.println("Введите описание подзадачи ");
        String details = scan.nextLine();
        String status = "NEW";
        HashMap <Integer,Object> subTask = new HashMap<>();
        SubTask subTask1 = new SubTask(numbersubTask, name, details, status, subTask);
        Epic epicObject = (Epic) allTasks.get(numTask).getForSubtask().get(numEpic);
        epicObject.getForSubtask().put(numbersubTask,subTask1);
    }

    public static long generateNumberEpic(HashMap<Integer, Task> allTasks) {
        System.out.println("Введите номер эпика, для которого создается подзадача");
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                int nameEpic = scanner.nextInt();
                int nameTask = nameEpic / 100;
                Epic epic = (Epic) allTasks.get(nameTask).getForSubtask().get(nameEpic);
                int numberSubtusk = nameEpic * 100000 + epic.getForSubtask().size() + 1;
                return numberSubtusk;
            }catch (InputMismatchException e) {
                System.out.println("Введите корреткно число");
            }catch (NullPointerException n){
                System.out.println("Эпика, с указанным Вами номером не существует. Вот список всех эпиков");
                CreateEpic.showListEpic(allTasks);
                System.out.println("Введите корректный номер эпика");
            }
        }
    }


    public static void showListSubTaskForEpic(HashMap<Integer, Task> allTasks) {
        Scanner sc = new Scanner(System.in);
        CreateEpic.showListEpic(allTasks);
        System.out.println("Введите номер эпика для которого необходимо показать список подзадач");
        int numEpic = sc.nextInt();
        int numtask = numEpic/100;
        if (!allTasks.isEmpty() && !allTasks.get(numtask).getForSubtask().isEmpty()){
        Epic epicObject = (Epic) allTasks.get(numtask).getForSubtask().get(numEpic);
        for (int k: epicObject.getForSubtask().keySet()){
            System.out.println(epicObject.getForSubtask().get(k).toString());
        }
    } else {
            System.out.println("Списки пусты");
        }
    }
}
