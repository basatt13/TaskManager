package controller;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.IOException;
import java.util.Scanner;

public class ForTest extends FileBackedTaskManager{

    ForTest(String file) {
        super(file);
    }

    public static void test() throws IOException {
        FileBackedTaskManager test = FileBackedTaskManager.loadFromFile(createFileForSave());
        ForTest forTest = new ForTest("autoSave.csv");
        forTest.createTask();
        forTest.createTask();
        forTest.createEpic();
        forTest.createEpic();
        forTest.createSubtask();
        forTest.createSubtask();
        forTest.getTaskByID();
        forTest.getEpicByID();
        forTest.getSubtaskByID();
        forTest.getTaskByID();
    }

    @Override
    public void createTask(){
        int numberTask = generateNumberTask();
        String name = "задача " + numberTask;
        String details = "описание задачи " + numberTask;
        Status status1 = Status.NEW;
        Task task = new Task(numberTask, name, details, status1);
        try {
            addTasks(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(numberTask);
    }

    @Override
    public void createEpic() {
        int numberEpics = generateNumberTask();
        String name = "эпик " + numberEpics;
        String details = "описание эпика " + numberEpics;
        Status status = Status.NEW;
        Epic epic = new Epic(numberEpics, name, details, status);
        try {
            addEpics(epic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(numberEpics);
    }

    @Override
    public void createSubtask()  {
        Scanner scanner = new Scanner(System.in);
        if (Tables.allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
            createEpic();
        } else {
            showListEpics();
            System.out.println("Введите номер эпика для которого создается подзадача");
            int idEpic = scanner.nextInt();
            int numberSubtask = generateNumberTask();
            String name = "подзадача " + numberSubtask;
            String details = "описание подзадачи " + numberSubtask;
            Status status = Status.NEW;
            SubTask subTask = new SubTask(numberSubtask, name, details, status);
            try {
                addSubtask(subTask);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Tables.allEpics.get(idEpic).getSubtasks().add(subTask.getID());
            subTask.setEpic(idEpic);
            Tables.forGenerateID.add(numberSubtask);
        }
    }
}
