import java.util.HashMap;
import java.util.Scanner;

public class Status {

    public static void StatusProgress(HashMap<Integer, Task> allTasks) {
        while (true){
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Укажите номер задачи/эпика/подзадачи которая сейчас выполняется");
                System.out.println("Задача и эпики указываются при условии, что в них не входят эпики" +
                        " и подзадачи соответственно");
                int ID = scanner.nextInt();
                if (ID > 10100000) {
                    int numEpic = ID / 100000;
                    int numTask = numEpic / 100;
                    Epic epicObject = (Epic) allTasks.get(numTask).getForSubtask().get(numEpic);
                    SubTask subTask1 = (SubTask) epicObject.getForSubtask().get(ID);
                    String name = subTask1.getName();
                    String details = subTask1.getDetails();
                    String status = "IN_PROGRESS";
                    HashMap<Integer, Object> subTask = new HashMap<>();
                    SubTask subTask2 = new SubTask(ID, name, details, status, subTask);
                    epicObject.getForSubtask().put(ID, subTask2);
                    break;
                }else if(ID<=100){
                    Task task = new Task(allTasks.get(ID).ID,allTasks.get(ID).getName(),allTasks.get(ID).getDetails(),
                            "IN_PROGRESS",allTasks.get(ID).getForSubtask());
                    allTasks.put(ID,task);
                } else if(ID<=100000){
                    int numTask = ID/100000;
                    Epic epic = (Epic) allTasks.get(numTask).getForSubtask().get(ID);
                    Epic epic1 = new Epic(epic.ID, epic.getName(), epic.getDetails(), "IN_PROGRESS",epic.getForSubtask());
                    allTasks.get(numTask).getForSubtask().put(ID,epic1);
                }else {
                    System.out.println("Номер подзадачи не найден");
                }
            }catch (Exception e){
                System.out.println("Введите корректный номер задачи");
            }
        }
    }
    public static void updateStatusForEpic(HashMap<Integer, Task> allTasks){
        System.out.println("Обновляем статусы подзадач");
        for (int k: allTasks.keySet()){
            for (int j: allTasks.get(k).getForSubtask().keySet()) {
                int chekDone = 0;
                int chekNew = 0;
                int chekProgress = 0;
                Epic epic = (Epic) allTasks.get(k).getForSubtask().get(j);
                for (int l : epic.getForSubtask().keySet()) {
                    SubTask subTask = (SubTask) epic.getForSubtask().get(l);
                    if (epic.getForSubtask().isEmpty()){
                        System.out.println("В эпике отсутствуют подзадачи");
                    } else if (subTask.getStatus().equals("NEW")) {
                        chekNew += 1;
                    } else if(subTask.getStatus().equals("DONE")) {
                        chekDone +=1;
                    } else if(subTask.getStatus().equals("IN_PROGRESS")){
                        chekProgress += 1;
                    }
                }
                if(chekNew ==epic.getForSubtask().size()){
                    Epic epic3 = new Epic(epic.ID, epic.getName(), epic.getDetails(),"NEW",
                            epic.getForSubtask());
                    CreateEpic.addEpic(epic3,allTasks);

                } else if(chekDone == epic.getForSubtask().size()){
                    Epic epic3 = new Epic(epic.ID, epic.getName(), epic.getDetails(),"DONE",
                            epic.getForSubtask());
                    CreateEpic.addEpic(epic3,allTasks);
                }else if(chekProgress >= 1) {
                    Epic epic3 = new Epic(epic.ID, epic.getName(), epic.getDetails(), "IN_PROGRESS",
                            epic.getForSubtask());
                    CreateEpic.addEpic(epic3, allTasks);
                }
                System.out.println(" Обновление завершено");
                CreateTasks.showListtasks(allTasks);
                    }
                }
            }

    public static void StatusDone(HashMap<Integer, Task> allTasks) {
        while (true){
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Укажите номер задачи/эпика/подзадачи, которая завершена");
                System.out.println("Задача и эпики указываются при условии, что в них не входят эпики" +
                        " и подзадачи соответственно");
                int ID = scanner.nextInt();
                if (ID > 10100000) {
                    int numEpic = ID / 100000;
                    int numTask = numEpic / 100;
                    Epic epicObject = (Epic) allTasks.get(numTask).getForSubtask().get(numEpic);
                    SubTask subTask1 = (SubTask) epicObject.getForSubtask().get(ID);
                    String name = subTask1.getName();
                    String details = subTask1.getDetails();
                    String status = "DONE";
                    HashMap<Integer, Object> subTask = new HashMap<>();
                    SubTask subTask2 = new SubTask(ID, name, details, status, subTask);
                    epicObject.getForSubtask().put(ID, subTask2);
                    break;
                }else if(ID<=100){
                    Task task = new Task(allTasks.get(ID).ID,allTasks.get(ID).getName(),allTasks.get(ID).getDetails(),
                            "DONE",allTasks.get(ID).getForSubtask());
                    allTasks.put(ID,task);
                } else if(ID<=100000){
                    int numTask = ID/100000;
                    Epic epic = (Epic) allTasks.get(numTask).getForSubtask().get(ID);
                    Epic epic1 = new Epic(epic.ID, epic.getName(), epic.getDetails(), "DONE",epic.getForSubtask());
                    allTasks.get(numTask).getForSubtask().put(ID,epic1);
                }else {
                    System.out.println("Номер подзадачи не найден");
                }
            }catch (Exception e){
                System.out.println("Введите корректный номер задачи");
            }
        }
    }


    public static void updateStatusForTask(HashMap<Integer, Task> allTasks){
        System.out.println("Обновляем статусы задач");
        for (int k: allTasks.keySet()){
            int checkDoneEpic = 0;
            int checkNewEpic = 0;
            int checkProgressEpic = 0;
            for (int j: allTasks.get(k).getForSubtask().keySet()) {
                Epic epic = (Epic) allTasks.get(k).getForSubtask().get(j);
                if (allTasks.get(k).getForSubtask().isEmpty()) {
                    System.out.println();
                } else if (epic.getStatus().equals("DONE")) {
                    checkDoneEpic += 1;
                } else if (epic.getStatus().equals("IN_PROGRESS")) {
                    checkProgressEpic += 1;
                } else if (epic.getStatus().equals("NEW")) {
                    checkNewEpic += 1;
                }
            }
                if(checkNewEpic ==allTasks.get(k).getForSubtask().size()){
                    Task task = new Task(allTasks.get(k).ID, allTasks.get(k).getName(), allTasks.get(k).getDetails(),
                            "NEW",
                            allTasks.get(k).getForSubtask());
                    CreateTasks.addTasks(task,allTasks);
                } else if(checkDoneEpic == allTasks.get(k).getForSubtask().size()){
                    Task task = new Task(allTasks.get(k).ID, allTasks.get(k).getName(), allTasks.get(k).getDetails(),
                            "DONE",
                            allTasks.get(k).getForSubtask());
                    CreateTasks.addTasks(task,allTasks);
                }else if(checkProgressEpic >= 1) {
                    Task task = new Task(allTasks.get(k).ID, allTasks.get(k).getName(), allTasks.get(k).getDetails(),
                            "IN_PROGRESS",
                            allTasks.get(k).getForSubtask());
                    CreateTasks.addTasks(task,allTasks);
                }
                System.out.println(" Обновление завершено");
                CreateTasks.showListtasks(allTasks);
            }
        }
    }




