package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Epic extends Task{
protected ArrayList<Integer> subtasks = new ArrayList<>();

    @Override
    public String toString() {
        return "Epic{" +
                "} " + super.toString();
    }

    public Epic(int ID, String name, String details, String status) {
        super(ID, name, details, status);

    }
    public static void createEpic (HashMap<Integer, Epic> allEpics) {
        System.out.println("Введите название эпика");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberEpics = generateNumberEpics(allEpics);
        String status = "NEW";
        Epic epic = new Epic(numberEpics, name, details, status);
        addEpics(epic, allEpics);

    }

    public static void addEpics(Epic epic, HashMap<Integer, Epic> allEpics){
        allEpics.put(epic.ID,epic);
    }

    public static int generateNumberEpics(HashMap <Integer, Epic> allEpics) {
        return allEpics.size() + 1;
    }

    public static void showListEpics(HashMap<Integer, Epic> allEpics) {
        System.out.println("Список всех эпиков'");
        for (Epic epic : allEpics.values()) {
            System.out.println(epic.toString());
        }
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

    public static void updateStatusEpic(HashMap<Integer, Epic> allEpics,HashMap<Integer, SubTask> allSubTusk){
        for(Epic k:allEpics.values()){
            int countStatusNew = 0;
            int countStatusDone = 0;
            int countStatusProgress=0;
            if(!k.subtasks.isEmpty()){
                for (Integer j: k.subtasks){
                    if(allSubTusk.get(j).getStatus().equals("NEW")){
                        countStatusNew+=1;
                    } else if(allSubTusk.get(j).getStatus().equals("DONE")){
                        countStatusDone += 1;
                    }else if(allSubTusk.get(j).getStatus().equals("TO_PROGRESS")){
                        countStatusProgress +=1;
                    }
                }
            }
            System.out.println(countStatusDone + countStatusNew+countStatusProgress);
            if(countStatusNew == k.subtasks.size()){
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), "NEW");
                allEpics.put(epic.getID(),epic);
                epic.subtasks = k.subtasks;
            }else if(countStatusDone==k.subtasks.size()){
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), "DONE");
                allEpics.put(epic.getID(),epic);
                epic.subtasks = k.subtasks;
            } else if(countStatusProgress >= 1){
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), "IN_PROGRESS");
                allEpics.put(epic.getID(),epic);
                epic.subtasks = k.subtasks;
            }
            }
             showListEpics(allEpics);
        }
    }
