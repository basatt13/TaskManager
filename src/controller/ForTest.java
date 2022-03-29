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

        ForTest forTest = new ForTest("autoSave.csv");
        forTest.loadFromFile(createFileForSave());
        Task task = new Task(forTest.generateNumberTask(), "задача " + forTest.generateNumberTask()
                ,"описание",Status.NEW,"12.12.22, 01:00",20);
        Epic epic = new Epic(forTest.generateNumberTask(), "эпик " + forTest.generateNumberTask()
                ,"описание",Status.NEW,null,0);
        SubTask subTask = new SubTask(forTest.generateNumberTask(), "подзадача " + forTest.generateNumberTask()
                ,"описание",Status.NEW,"13.12.22, 01:00",20);
        forTest.createTask(task);
        forTest.createTask(task);
        forTest.createEpic(epic);
        forTest.createEpic(epic);
        forTest.createSubtask(subTask,3);
        forTest.createSubtask(subTask,3);
        forTest.getTaskByID(1);
        forTest.getEpicByID(2);
        forTest.getSubtaskByID(3);
        forTest.getTaskByID(1);

    }


    @Override
    public void createTask(Task task){
        try {
            addTasks(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(task.getID());
    }

    @Override
    public void createEpic(Epic epic) {
        try {
            addEpics(epic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(epic.getID());
    }

    @Override
    public void createSubtask(SubTask subTask, int idEpic)  {
        Scanner scanner = new Scanner(System.in);
        if (Tables.allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
        } else {
            try {
                addSubtask(subTask);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Tables.allEpics.get(idEpic).getSubtasks().add(subTask.getID());
            subTask.setEpic(idEpic);
            Tables.forGenerateID.add(subTask.getID());
        }
    }
}
