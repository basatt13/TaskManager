package tasks;
import controller.Managers;
import controller.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SubTask extends Task {

    public int getEpic() {
        return epic;
    }

    protected int epic;

    public SubTask(int ID, String name, String details, Status status) {
        super(ID, name, details, status);
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

    public static void toProgressSubtask(HashMap<Integer, SubTask> allSubtask,HashMap <Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListSubtask(allSubtask);
        System.out.println("Введите ID подзадачи, которая сейчас выполняется");
        int ID = scanner.nextInt();
        for (SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                SubTask subTask = new SubTask (k.getID(),k.getName(), k.getDetails(), Status.TO_PROGRESS);
                SubTask.addSubtask(subTask,allSubtask);
                subTask.epic = k.epic;
            }
        }
        Managers.getDefault().updateStatusEpic(allEpics,allSubtask);
        Managers.getDefault().showListEpics(allEpics);
    }

    public static void doneSubtask(HashMap<Integer, SubTask> allSubtask,HashMap <Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListSubtask(allSubtask);
        System.out.println("Введите ID задачи, выполнение которой завершилось");
        int ID = scanner.nextInt();
        for (SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                SubTask subTask = new SubTask (k.getID(),k.getName(), k.getDetails(), Status.DONE);
                SubTask.addSubtask(subTask,allSubtask);
                subTask.epic = k.epic;
            }
        }
        Managers.getDefault().updateStatusEpic(allEpics,allSubtask);
        Managers.getDefault().showListEpics(allEpics);
    }

    public static void getSubtaskByID(HashMap<Integer, SubTask> allSubtaask, List<Task> historyList){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо показать");
        int ID = scanner.nextInt();
        for(SubTask k: allSubtaask.values()){
            if(k.getID()==ID){
                System.out.println(k.toString());
                if (historyList.size() < 10) {
                    historyList.add(historyList.size(), k);
                } else if (historyList.size()==10){
                    historyList.remove(0);
                    historyList.add(historyList.size(), k);
                }
            }
        }
    }

    public static void updateSubtask(HashMap<Integer, SubTask> allSubtask) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (SubTask k : allSubtask.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название подзадачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание подзадачи ");
                String details = scan.nextLine();
                SubTask subTask = new SubTask(ID, name, details, k.getStatus());
                allSubtask.put(subTask.getID(), subTask);
                subTask.setEpic(k.getEpic());
            }
        }
    }

    public void setEpic(int epic) {
        this.epic = epic;
    }
}





