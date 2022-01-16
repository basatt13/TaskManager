package tasks;
import controller.Manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Epic extends Task{

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    protected ArrayList<Integer> subtasks = new ArrayList<>();

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }
    @Override
    public String toString() {
        return "Epic{" +
                "} " + super.toString();
    }

    public Epic(int ID, String name, String details, String status) {
        super(ID, name, details, status);
    }



    public static void updateEpic(HashMap<Integer, Epic> allEpic) {
        Scanner scanner = new Scanner(System.in);
        Manager.showListEpics(allEpic);
        System.out.println("Введите ID эпика, который необходимо обновить");
        int ID = scanner.nextInt();
        for (Epic k : allEpic.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название эпика");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание эпика ");
                String details = scan.nextLine();
                Epic epic = new Epic(ID, name, details, k.getStatus());
                allEpic.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            }
        }
    }

    public static int generateNumberEpics(HashMap <Integer, Epic> allEpics) {
        return allEpics.size() + 1;
    }

    public static void getEpicByID(HashMap<Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID эпика, который необходимо показать");
        int ID = scanner.nextInt();
        for(Epic k: allEpics.values()){
            if(k.getID()==ID){
                System.out.println(k.toString());
                }
            }
        }
    }
