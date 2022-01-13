package tasks;

import java.util.HashMap;
import java.util.Scanner;

public class SubTask extends Epic {
    protected int epic;

    public SubTask(int ID, String name, String details, String status) {
        super(ID, name, details, status);
    }

    public static void createSubtask(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics) {
        Scanner scanner = new Scanner(System.in);
        if (allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
        } else {
            Epic.showListEpics(allEpics);
            System.out.println("Введите номер эпика для которого создается подзадача");
            int idEpic = scanner.nextInt();
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите название подзадачи ");
            String name = sc.nextLine();
            System.out.println("Введите описание подзадачи ");
            String details = sc.nextLine();
            int numberSubtask = generateNumberSub(allSubTusk);
            String status = "NEW";
            SubTask subTask = new SubTask(numberSubtask, name, details, status);
            addSubtask(subTask, allSubTusk);
            allEpics.get(idEpic).subtasks.add(subTask.getID());
            subTask.epic = idEpic;

        }
    }

    public static void getListSubtasksByEpicID(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics) {
        Scanner sc = new Scanner(System.in);
        Epic.showListEpics(allEpics);
        System.out.println("Введите номер эпика для которого нужно получить список всех подзадач");
        int idEpic = sc.nextInt();
        for (SubTask k : allSubTusk.values()) {
            if (k.epic == idEpic) {
                System.out.println(k.toString());
            }
        }
    }


    public static void addSubtask(SubTask subTask, HashMap<Integer, SubTask> allSubTusk) {
        allSubTusk.put(subTask.getID(), subTask);
    }

    public static int generateNumberSub(HashMap<Integer, SubTask> allSubTusk) {
        return allSubTusk.size() + 1;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epic=" + epic +
                "} " + super.toString();
    }

    public static void showListSubtask(HashMap<Integer, SubTask> allSubTusk) {
        System.out.println("Список всех подзадач'");
        for (int epic : allSubTusk.keySet()) {
            System.out.println(allSubTusk.get(epic).toString());
        }
    }

    public static void toProgressSubtask(HashMap<Integer, SubTask> allSubtask,HashMap <Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        showListSubtask(allSubtask);
        System.out.println("Введите ID подзадачи, которая сейчас выполняется");
        int ID = scanner.nextInt();
        for (SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                SubTask subTask = new SubTask (k.getID(),k.getName(), k.getDetails(), "TO_PROGRESS");
                addSubtask(subTask,allSubtask);
                subTask.epic = k.epic;

            }
        }
        Epic.updateStatusEpic(allEpics,allSubtask);
        Epic.showListEpics(allEpics);
    }

    public static void doneSubtask(HashMap<Integer, SubTask> allSubtask,HashMap <Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        showListSubtask(allSubtask);
        System.out.println("Введите ID задачи, выполнение которой завершилось");
        int ID = scanner.nextInt();
        for (SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                SubTask subTask = new SubTask (k.getID(),k.getName(), k.getDetails(), "DONE");
                addSubtask(subTask,allSubtask);
                subTask.epic = k.epic;
            }
        }
        Epic.updateStatusEpic(allEpics,allSubtask);
        Epic.showListEpics(allEpics);
    }

    public static void getSubtaskByID(HashMap<Integer, SubTask> allSubtaask){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо показать");
        int ID = scanner.nextInt();
        for(SubTask k: allSubtaask.values()){
            if(k.getID()==ID){
                System.out.println(k.toString());
            }
        }
    }
        }





