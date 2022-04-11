package controller;
import api.HttpTaskManager;
import api.HttpTaskServer;
import api.KVServer;
import api.KVTaskClient;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.IOException;

public class ForTest extends HttpTaskManager {

    KVTaskClient kvTaskClient;
    public ForTest(String file) throws IOException, InterruptedException {
        super(file);
        kvTaskClient = new KVTaskClient(file);

    }

    public static void test() throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        ForTest forTest = new ForTest("http://localhost:8079");
        HttpTaskServer httpTaskServer = new HttpTaskServer(8080, forTest);
        httpTaskServer.start();

        Task task = new Task(forTest.generateNumberTask(), "задача " + forTest.generateNumberTask()
                ,"описание",Status.NEW,"12.12.22/01:00",20);
        Epic epic = new Epic(forTest.generateNumberTask(), "эпик " + forTest.generateNumberTask()
                ,"описание",Status.NEW,null,0);
        SubTask subTask = new SubTask(forTest.generateNumberTask(), "подзадача " + forTest.generateNumberTask()
                ,"описание",Status.NEW,"13.12.22/01:00",20);
        forTest.createTask(task);
        forTest.createTask(task);
        forTest.createEpic(epic);
        forTest.createEpic(epic);
        forTest.createSubtask(subTask,1);
        forTest.createSubtask(subTask,1);
        forTest.getTaskByID(1);
        forTest.getEpicByID(2);
        forTest.getSubtaskByID(3);
        forTest.getTaskByID(1);
kvServer.getServer().stop(100);
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
        if (Tables.allEpics.isEmpty() || !Tables.allEpics.containsKey(idEpic)) {
            System.out.println("Сначала создайте эпик c id "+ idEpic);
        } else{
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
